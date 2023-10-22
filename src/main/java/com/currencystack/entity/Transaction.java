package com.currencystack.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    private Long transactionId;
    private BigDecimal sourceAmount;
    private String sourceCurrencyCode;
    private BigDecimal targetAmount;
    private String targetCurrencyCode;
    private Instant creationTimestamp;
}