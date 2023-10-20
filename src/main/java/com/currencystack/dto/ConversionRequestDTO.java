package com.currencystack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class ConversionRequestDTO {
    @NotBlank(message = "Source currency code is required")
    private String sourceCurrencyCode;

    @NotBlank(message = "Target currency code is required")
    private String targetCurrencyCode;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private Double amount;
}
