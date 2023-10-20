package com.currencystack.service;

import com.currencystack.dto.ConversionResponseDTO;
import com.currencystack.dto.ConversionRequestDTO;
import com.currencystack.dto.mapper.ConversionMapper;
import com.currencystack.entity.Conversion;
import com.currencystack.repository.ConversionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
}

