package com.currencystack.service;

import com.currencystack.dto.TransactionResponseDTO;
import com.currencystack.dto.TransactionRequestDTO;
import com.currencystack.dto.mapper.TransactionMapper;
import com.currencystack.entity.Transaction;
import com.currencystack.exception.TransactionNotFoundException;
import com.currencystack.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
public class TransactionService {
    private final ExchangeRateService exchangeRateService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(ExchangeRateService exchangeRateService, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.exchangeRateService = exchangeRateService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponseDTO convert(TransactionRequestDTO requestDTO) {
        BigDecimal exchangeRate = exchangeRateService.getExchangeRate(requestDTO.getSourceCurrencyCode(), requestDTO.getTargetCurrencyCode()).getRate();
        Transaction transaction = transactionMapper.toEntity(requestDTO);
        BigDecimal convertedAmount = transaction.getSourceAmount().multiply(exchangeRate);
        transaction.setTargetAmount(convertedAmount);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toDTO(transaction);
    }

    public Page<TransactionResponseDTO> getConversionsByDate(LocalDate date, Pageable pageable) {
        Instant startOfDay = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endOfDay = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        Page<Transaction> transactions =  transactionRepository.findTransactionByCreationTimestampBetweenOrderByCreationTimestampDesc(startOfDay, endOfDay, pageable);


        return null;
    }

    public TransactionResponseDTO getTransactionById(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
        return transactionMapper.toDTO(transaction);
    }
}

