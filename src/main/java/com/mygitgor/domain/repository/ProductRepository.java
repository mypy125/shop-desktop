package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Product;
import org.springframework.stereotype.Repository;

public interface ProductRepository {
    Product findByCode(String code);
}
