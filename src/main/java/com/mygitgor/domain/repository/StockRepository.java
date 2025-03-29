package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Product;
import com.mygitgor.domain.model.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepository {
    Optional<Stock> findByProduct(Product product);
    Stock save(Stock stock);
    void delete(Stock stock);
    List<Stock> findAll();
}
