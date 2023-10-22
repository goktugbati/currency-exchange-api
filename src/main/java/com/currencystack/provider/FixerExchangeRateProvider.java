package com.currencystack.provider;

import com.currencystack.client.ExchangeRateClient;
import com.currencystack.dto.CurrencyConversion;
import com.currencystack.dto.ExchangeRateResponse;
import com.currencystack.entity.enums.Currency;
import com.currencystack.exception.FetchRatesException;
import com.currencystack.exception.InvalidCurrencyCodeException;
import com.currencystack.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class FixerExchangeRateProvider implements ExchangeRateProvider {
    private static final Currency BASE_CURRENCY = Currency.EUR;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateService.class);

    private final ExchangeRateClient exchangeRateClient;

    @Value("${fixer.api.key}")
    private String apiKey;

    public FixerExchangeRateProvider(ExchangeRateClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    @Override
    public CurrencyConversion getCurrencyConversionRate(Currency sourceCurrency, Currency targetCurrency) {
        ExchangeRateResponse response = fetchRates(sourceCurrency.getCode(), targetCurrency.getCode());
        BigDecimal rate = calculateRate(sourceCurrency, targetCurrency, response);

        return CurrencyConversion
                .builder()
                .conversionRate(rate)
                .timestamp(response.getTimestamp())
                .build();
    }

    private BigDecimal calculateRate(Currency baseCurrency, Currency targetCurrency, ExchangeRateResponse response) {
        if (baseCurrency.equals(BASE_CURRENCY)) {
            LOGGER.info("Direct exchange rate for EUR to {}", targetCurrency);
            return checkRateValidity(response, targetCurrency.getCode()).setScale(2, RoundingMode.HALF_UP);
        } else {
            BigDecimal eurToBase = checkRateValidity(response, baseCurrency.getCode());
            BigDecimal eurToTarget = checkRateValidity(response, targetCurrency.getCode());
            BigDecimal rate = eurToTarget.divide(eurToBase, 2, RoundingMode.HALF_UP);
            LOGGER.info("Calculated exchange rate for {} to {}: {}", baseCurrency, targetCurrency, rate);
            return rate;
        }
    }

    private ExchangeRateResponse fetchRates(String baseCurrencyCode, String targetCurrencyCode) {
        LOGGER.debug("Fetching exchange rates with base : {} and target : {}", baseCurrencyCode, targetCurrencyCode);
        ExchangeRateResponse response = exchangeRateClient.getExchangeRate(String.join(",", baseCurrencyCode, targetCurrencyCode), apiKey);
        if (!response.isSuccess()) {
            LOGGER.error("Failed to fetch rates or response is invalid");
            throw new FetchRatesException();
        }
        return response;
    }

    private BigDecimal checkRateValidity(ExchangeRateResponse response, String currencyCode) {
        LOGGER.debug("Checking validity of rate for currency: {}", currencyCode);
        BigDecimal rate = response.getRates().get(currencyCode);
        if (rate == null) {
            LOGGER.warn("Invalid or unsupported currency code received: {}", currencyCode);
            throw new InvalidCurrencyCodeException("Invalid or unsupported currency code: " + currencyCode);
        }
        return rate;
    }
}
