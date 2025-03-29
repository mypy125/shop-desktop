package com.mygitgor.infrastructure.database.impl;

import com.mygitgor.domain.model.Stock;
import com.mygitgor.domain.model.Store;
import com.mygitgor.domain.model.StoreStock;
import com.mygitgor.domain.repository.StoreStockRepository;
import com.mygitgor.infrastructure.database.jpa.StoreStockJpaRepository;
import com.mygitgor.mapper.StoreStockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreStockRepositoryImpl implements StoreStockRepository {
    private final StoreStockJpaRepository jpaRepository;
    private final StoreStockMapper storeStockMapper;

    @Override
    public List<StoreStock> findByStore(Store store) {
        return jpaRepository.findByStoreId(store.getId()).stream()
                .map(storeStockMapper::toDto)
                .toList();
    }

    @Override
    public List<StoreStock> findByStock(Stock stock) {
        return jpaRepository.findByStockId(stock.getId()).stream()
                .map(storeStockMapper::toDto)
                .toList();
    }

    @Override
    public StoreStock findByStoreAndStock(Store store, Stock stock) {
        return jpaRepository.findByStoreIdAndStockId(store.getId(), stock.getId())
                .map(storeStockMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<StoreStock> findAll() {
        return jpaRepository.findAll()
                .stream().map(storeStockMapper::toDto).toList();
    }

    @Override
    public void save(StoreStock storeStock) {
        jpaRepository.save(storeStockMapper.toEntity(storeStock));
    }
}
