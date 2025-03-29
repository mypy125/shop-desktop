package com.mygitgor.infrastructure.database.impl;

import com.mygitgor.domain.model.Product;
import com.mygitgor.domain.model.Stock;
import com.mygitgor.domain.repository.StockRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepositoryImpl implements StockRepository {
    @Override
    public Stock findByProduct(Product product) {
        return null;
    }
}
