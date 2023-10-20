package com.currencystack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponseDTO {
    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;
    private String transactionId;
}
