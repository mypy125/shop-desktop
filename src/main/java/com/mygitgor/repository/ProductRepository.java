package com.mygitgor.repository;

import com.mygitgor.model.Product;

import java.util.List;

public interface ProductRepository {
    void save(Product product);
    void update(Product product);
    void delete(String code);
    Product findById(String code);
    List<Product> findAll();
}
