package com.currencystack.repository;

import com.currencystack.entity.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {
}
