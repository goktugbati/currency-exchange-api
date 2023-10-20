package com.currencystack.dto.mapper;

import com.currencystack.dto.ConversionResponseDTO;
import com.currencystack.dto.ConversionRequestDTO;
import com.currencystack.entity.Conversion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversionMapper {

    ConversionResponseDTO toDTO(Conversion conversion);

    @Mapping(source = "sourceCurrencyCode", target = "sourceCurrencyCode")
    @Mapping(source = "targetCurrencyCode", target = "targetCurrencyCode")
    @Mapping(source = "amount", target = "sourceAmount")
    Conversion toEntity(ConversionRequestDTO conversionRequestDTO);
}
