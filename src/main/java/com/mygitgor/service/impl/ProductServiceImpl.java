package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Product findByCode(String code) {
        return productRepository.findByCode(code);
    }

    @Transactional
    @Override
    public void updateProduct(Product product) {
        if (productRepository.existsById(product.getId())) {
            productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteProduct(String code) {
        Product product = productRepository.findByCode(code);
        if (product != null) {
            productRepository.delete(product);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }
}
