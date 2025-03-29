package com.mygitgor.infrastructure.database.impl;

import com.mygitgor.domain.model.Product;
import com.mygitgor.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Product findByCode(String code) {
        return null;
    }
}
