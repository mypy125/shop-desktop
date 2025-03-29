package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Product;
import com.mygitgor.domain.model.Stock;

public interface StockRepository {
    Stock findByProduct(Product product);
}
