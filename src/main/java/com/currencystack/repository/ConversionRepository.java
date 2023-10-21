package com.currencystack.repository;

import com.currencystack.entity.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {
    Page<Conversion> findByTransactionId(Long transactionId, Pageable pageable);
    Page<Conversion> findByTransactionDate(Instant transactionDate, Pageable pageable);
}
