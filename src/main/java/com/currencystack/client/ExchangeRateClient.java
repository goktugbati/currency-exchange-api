package com.currencystack.client;

import com.currencystack.dto.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "fixer", url = "${fixer.endpoint}")
public interface ExchangeRateClient {

    @GetMapping("/latest")
    ExchangeRateResponse getExchangeRate(@RequestParam("symbols") String targetCurrencies,
                                         @RequestParam("access_key") String accessKey);

    @GetMapping("/latest")
    ExchangeRateResponse getAllExchangeRates(@RequestParam("access_key") String accessKey);
}
