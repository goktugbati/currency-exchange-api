package com.currencystack.dto.mapper;

import com.currencystack.dto.TransactionRequestDTO;
import com.currencystack.dto.TransactionResponseDTO;
import com.currencystack.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponseDTO toDTO(Transaction transaction);

    @Mapping(source = "sourceCurrencyCode", target = "sourceCurrencyCode")
    @Mapping(source = "targetCurrencyCode", target = "targetCurrencyCode")
    @Mapping(source = "amount", target = "sourceAmount")
    Transaction toEntity(TransactionRequestDTO transactionRequestDTO);
}
