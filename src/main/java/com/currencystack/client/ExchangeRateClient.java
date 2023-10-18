package com.currencystack.currencyexchangeapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "ratesapi", url = "${ratesapi.endpoint}")
public interface ExchangeRateClient {

    @GetMapping("/latest")
    Map<String, Object> getLatestRates(@RequestParam("base") String baseCurrency);
}
