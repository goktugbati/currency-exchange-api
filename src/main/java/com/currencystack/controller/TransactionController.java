package com.currencystack.controller;


import com.currencystack.dto.TransactionRequestDTO;
import com.currencystack.dto.TransactionResponseDTO;
import com.currencystack.service.TransactionService;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/")
    public ResponseEntity<TransactionResponseDTO> convert(@Valid @RequestBody TransactionRequestDTO request) {
        TransactionResponseDTO transactionResponseDTO = transactionService.convert(request);
        return ResponseEntity.ok(transactionResponseDTO);
    }

//    @GetMapping("/")
//    public ResponseEntity<TransactionResponseDTO> listTransactions(@DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate transactionDate,
//                                                                   Pageable pageable) {
//        TransactionResponseDTO transactionResponseDTO = transactionService.getConversionsByDate(transactionDate, pageable);
//        return ResponseEntity.ok(transactionResponseDTO);
//    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> getByTransactionId(@PathVariable Long transactionId) {
        TransactionResponseDTO conversion = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(conversion);
    }
}
