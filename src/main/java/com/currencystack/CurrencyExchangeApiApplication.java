package com.currencystack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.currencystack.client")
public class CurrencyExchangeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApiApplication.class, args);
	}

}
