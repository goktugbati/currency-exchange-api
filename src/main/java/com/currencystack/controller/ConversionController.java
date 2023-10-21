package com.currencystack.controller;


import com.currencystack.dto.ConversionListRequestDTO;
import com.currencystack.dto.ConversionListResponseDTO;
import com.currencystack.dto.ConversionResponseDTO;
import com.currencystack.dto.ConversionRequestDTO;
import com.currencystack.service.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")
    public ResponseEntity<ConversionListResponseDTO> listConversions(ConversionListRequestDTO requestDTO) {
        Page<ConversionDTO> conversions = conversionService.listConversions(Optional.ofNullable(transactionId), Optional.ofNullable(transactionDate), pageable);

        ConversionListResponseDTO response = new ConversionListResponseDTO();
        response.setConversions(conversions.getContent());
        // set other pagination details...

        return ResponseEntity.ok(response);
    }
}
