package com.currencystack.service;

import com.currencystack.dto.ConversionResponseDTO;
import com.currencystack.dto.ConversionRequestDTO;
import com.currencystack.dto.mapper.ConversionMapper;
import com.currencystack.entity.Conversion;
import com.currencystack.repository.ConversionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConversionService {
    private final ExchangeRateService exchangeRateService;
    private final ConversionRepository conversionRepository;
    private final ConversionMapper conversionMapper;

    public ConversionService(ExchangeRateService exchangeRateService, ConversionRepository conversionRepository, ConversionMapper conversionMapper) {
        this.exchangeRateService = exchangeRateService;
        this.conversionRepository = conversionRepository;
        this.conversionMapper = conversionMapper;
    }

    public ConversionResponseDTO convert(ConversionRequestDTO requestDTO) {
        BigDecimal exchangeRate = exchangeRateService.getExchangeRate(requestDTO.getSourceCurrencyCode(), requestDTO.getTargetCurrencyCode()).getRate();
        Conversion conversion = conversionMapper.toEntity(requestDTO);
        BigDecimal convertedAmount = conversion.getSourceAmount().multiply(exchangeRate);
        conversion.setTargetAmount(convertedAmount);
        conversion = conversionRepository.save(conversion);
        return conversionMapper.toDTO(conversion);
    }

    public Page<ConversionResponseDTO> listConversions(Optional<Long> transactionId, Optional<LocalDateTime> transactionDate, Pageable pageable) {
        Page<Conversion> conversions;
        if (transactionId.isPresent()) {
            conversions = conversionRepository.findByTransactionId(transactionId.get(), pageable);
        } else if (transactionDate.isPresent()) {
            conversions = conversionRepository.findByTransactionDate(Instant.from(transactionDate.get()), pageable);
        } else {
            throw new IllegalArgumentException("Either transactionId or transactionDate must be provided.");
        }
        return conversions.map(conversionMapper::toDTO);
    }
}

