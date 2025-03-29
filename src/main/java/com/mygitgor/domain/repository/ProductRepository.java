package com.mygitgor.domain.repository;

import com.mygitgor.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Optional<Product> findByCode(String code);
    Product save(Product product);
    boolean existsById(UUID id);
    List<Product> findAll();
    void delete(Product product);
}
