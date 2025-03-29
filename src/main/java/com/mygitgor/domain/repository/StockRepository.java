package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Product;
import com.mygitgor.domain.model.Stock;
import org.springframework.stereotype.Repository;

public interface StockRepository {
    Stock findByProduct(Product product);
}
