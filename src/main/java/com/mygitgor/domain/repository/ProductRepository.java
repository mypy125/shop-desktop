package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Product;

public interface ProductRepository {
    Product findByCode(String code);
}
