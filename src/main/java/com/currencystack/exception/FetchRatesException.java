package com.currencystack.exception;

public class FetchRatesException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Failed to fetch rates from the external service";

    public FetchRatesException() {
        super(DEFAULT_MESSAGE);
    }

    public FetchRatesException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}

