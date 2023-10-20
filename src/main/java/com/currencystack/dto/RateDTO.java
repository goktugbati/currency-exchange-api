package com.currencystack.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class RateDTO {
    String baseCurrencyCode;
    String targetCurrencyCode;
    Instant timestamp;
    BigDecimal rate;
}
