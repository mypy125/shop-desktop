package com.mygitgor.infrastructure.database.impl;

import com.mygitgor.domain.model.Product;
import com.mygitgor.domain.repository.ProductRepository;
import com.mygitgor.infrastructure.database.ProductEntity;
import com.mygitgor.infrastructure.database.jpa.ProductJpaRepository;
import com.mygitgor.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository jpaRepository;
    private final ProductMapper productMapper;

    @Override
    public Optional<Product> findByCode(String code) {
        return Optional.of(jpaRepository.findByCode(code)
                .map(productMapper::toDto).get());
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = productMapper.toEntity(product);
        jpaRepository.save(entity);
        return product;
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Product product) {
        ProductEntity entity = productMapper.toEntity(product);
        jpaRepository.delete(entity);
    }
}
