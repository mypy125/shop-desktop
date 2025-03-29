package com.mygitgor.infrastructure.database.impl;

import com.mygitgor.domain.model.Product;
import com.mygitgor.domain.model.Stock;
import com.mygitgor.domain.repository.StockRepository;
import com.mygitgor.infrastructure.database.ProductEntity;
import com.mygitgor.infrastructure.database.StockEntity;
import com.mygitgor.infrastructure.database.jpa.StockJpaRepository;
import com.mygitgor.mapper.ProductMapper;
import com.mygitgor.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {
    private final StockJpaRepository jpaRepository;
    private final StockMapper stockMapper;
    private final ProductMapper productMapper;

    @Override
    public Optional<Stock> findByProduct(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        ProductEntity productEntity = productMapper.toEntity(product);
        return jpaRepository.findByProduct(productEntity)
                .map(stockMapper::toDto);
    }

    @Override
    public Stock save(Stock stock) {
        Objects.requireNonNull(stock, "Stock cannot be null");
        StockEntity entity = stockMapper.toEntity(stock);
        StockEntity savedEntity = jpaRepository.save(entity);
        return stockMapper.toDto(savedEntity);
    }

    @Override
    public void delete(Stock stock) {
        Objects.requireNonNull(stock, "Stock cannot be null");
        StockEntity entity = stockMapper.toEntity(stock);
        jpaRepository.delete(entity);
    }

    @Override
    public List<Stock> findAll() {
        return jpaRepository.findAll().stream()
                .map(stockMapper::toDto)
                .collect(Collectors.toList());
    }
}
