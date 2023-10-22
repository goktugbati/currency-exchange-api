package com.currencystack.provider;

import com.currencystack.dto.CurrencyConversion;
import com.currencystack.entity.enums.Currency;

public interface ExchangeRateProvider {
    CurrencyConversion getCurrencyConversionRate(Currency sourceCurrency, Currency targetCurrency);
}
