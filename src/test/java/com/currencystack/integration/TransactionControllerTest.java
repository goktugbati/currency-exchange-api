package com.currencystack.integration;

import com.currencystack.dto.TransactionRequestDTO;
import com.currencystack.dto.TransactionResponseDTO;
import com.currencystack.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testConvertEndpoint() throws Exception {
        String sourceCurrencyCode = "USD";
        String targetCurrencyCode = "EUR";
        double amount = 100.00;

        TransactionResponseDTO mockResponse = new TransactionResponseDTO();
        mockResponse.setTargetAmount(BigDecimal.valueOf(amount));

        // Mock the service's behavior
        when(transactionService.convert(new TransactionRequestDTO(sourceCurrencyCode, targetCurrencyCode, amount)))
                .thenReturn(mockResponse);


        mockMvc.perform(post("/api/conversions/convert")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new TransactionRequestDTO(sourceCurrencyCode, targetCurrencyCode, amount))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.targetAmount", is(amount)));
    }
}
