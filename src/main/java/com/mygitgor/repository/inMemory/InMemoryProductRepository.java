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
        products.put(product.getCode(), product);
    }

    @Override
    public void update(Product product) {
        products.put(product.getCode(), product);
    }

    @Override
    public void delete(String code) {
        products.remove(code);
    }

    @Override
    public Product findById(String code) {
        return products.get(code);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}