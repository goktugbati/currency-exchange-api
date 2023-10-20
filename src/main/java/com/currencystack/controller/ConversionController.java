package com.currencystack.controller;


import com.currencystack.dto.ConversionResponseDTO;
import com.currencystack.dto.ConversionRequestDTO;
import com.currencystack.service.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/conversions")
public class ConversionController {

    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping("/convert")
    public ResponseEntity<ConversionResponseDTO> convert(@Valid @RequestBody ConversionRequestDTO request) {
        ConversionResponseDTO conversionResponseDTO = conversionService.convert(request);
        return ResponseEntity.ok(conversionResponseDTO);
    }
}