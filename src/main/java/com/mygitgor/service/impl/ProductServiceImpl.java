package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(String code) {
        Product product = productRepository.findByCode(code);
        if (product != null) {
            productRepository.delete(product);
        } else {
            throw new IllegalArgumentException("Product not found with code: " + code);
        }
    }

    @Override
    public Product findByCode(String code) {
        return productRepository.findByCode(code);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
