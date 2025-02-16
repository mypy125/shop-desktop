package com.mygitgor.repository;

import com.mygitgor.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {
    Stock findByProductCode(String productCode);
    List<Stock> findByProductId(Long productId);
    void deleteByProductCode(String productCode);
}
