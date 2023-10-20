package com.currencystack.controller;

import com.currencystack.dto.RateDTO;
import com.currencystack.service.ExchangeRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exchange-rate")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public ResponseEntity<RateDTO> getExchangeRate(@RequestParam String target,
                                                   @RequestParam(required = false) String base) {
        RateDTO rate = exchangeRateService.getExchangeRate(base, target);
        return ResponseEntity.ok(rate);
    }
}
