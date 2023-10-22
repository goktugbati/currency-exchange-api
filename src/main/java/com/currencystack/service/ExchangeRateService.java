package com.currencystack.service;

import com.currencystack.dto.CurrencyConversion;
import com.currencystack.dto.RateDTO;
import com.currencystack.entity.enums.Currency;
import com.currencystack.exception.InvalidCurrencyCodeException;
import com.currencystack.provider.ExchangeRateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateService.class);

    private final ExchangeRateProvider exchangeRateProvider;

    public ExchangeRateService(ExchangeRateProvider exchangeRateProvider) {
        this.exchangeRateProvider = exchangeRateProvider;
    }

    public RateDTO getExchangeRate(String sourceCurrencyCode, String targetCurrencyCode) {
        LOGGER.info("Fetching exchange rate for {} to {}", sourceCurrencyCode, targetCurrencyCode);
        Currency sourceCurrency = (sourceCurrencyCode != null && !sourceCurrencyCode.isEmpty()) ? getCurrency(sourceCurrencyCode) : Currency.EUR;
        Currency targetCurrency = getCurrency(targetCurrencyCode);

        CurrencyConversion currencyConversion = this.exchangeRateProvider.getCurrencyConversionRate(sourceCurrency, targetCurrency);

        return RateDTO.builder()
                .baseCurrencyCode(sourceCurrency.getCode())
                .targetCurrencyCode(targetCurrency.getCode())
                .rate(currencyConversion.getConversionRate())
                .timestamp(currencyConversion.getTimestamp())
                .build();
    }

    private Currency getCurrency(String currencyCode) {
        LOGGER.debug("Getting currency code: {}", currencyCode);
        return Currency.convert(currencyCode.toUpperCase()).orElseThrow(() -> new InvalidCurrencyCodeException(currencyCode));
    }
}

