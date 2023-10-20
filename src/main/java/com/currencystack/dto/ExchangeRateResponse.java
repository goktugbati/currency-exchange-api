package com.currencystack.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

@Data
public class ExchangeRateResponse {
    private String base;
    private Map<String, BigDecimal> rates;
    private LocalDate date;
    private boolean success;
    private Instant timestamp;
}
