package com.currencystack.exception;

public class InvalidCurrencyCodeException extends IllegalArgumentException {
    private static final String MESSAGE = "Invalid currency code: %s";

    public InvalidCurrencyCodeException(String currencyCode) {
        super(String.format(MESSAGE, currencyCode));
    }
}
