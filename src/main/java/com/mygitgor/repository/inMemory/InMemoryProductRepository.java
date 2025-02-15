package com.mygitgor.repository.inMemory;

import com.mygitgor.model.Product;
import com.mygitgor.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
    private Map<String, Product> products = new HashMap<>();

    @Override
    public void save(Product product) {
        if (product == null || product.getCode() == null) {
            throw new IllegalArgumentException("Product or product code cannot be null");
        }
        products.put(product.getCode(), product);
    }

    @Override
    public void update(Product product) {
        if (product == null || product.getCode() == null) {
            throw new IllegalArgumentException("Product or product code cannot be null");
        }
        if (!products.containsKey(product.getCode())) {
            throw new IllegalArgumentException("Product with code " + product.getCode() + " not found");
        }
        products.put(product.getCode(), product);
    }

    @Override
    public void delete(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Product code cannot be null");
        }
        products.remove(code);
    }

    @Override
    public Product findById(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Product code cannot be null");
        }
        return products.get(code);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}