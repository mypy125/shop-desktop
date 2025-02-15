package com.mygitgor.service;

import com.mygitgor.model.Product;
import com.mygitgor.repository.ProductRepository;

import java.util.List;

public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public void deleteProduct(String code) {
        productRepository.delete(code);
    }

    public Product findById(String code) {
        return productRepository.findById(code);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
