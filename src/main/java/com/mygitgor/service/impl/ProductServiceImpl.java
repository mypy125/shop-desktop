package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    @Override
    public void deleteProduct(String code) {
        productRepository.delete(code);
    }

    @Override
    public Product findById(String code) {
        return productRepository.findById(code);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
