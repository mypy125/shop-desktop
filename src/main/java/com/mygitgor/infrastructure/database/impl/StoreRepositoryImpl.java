package com.mygitgor.infrastructure.database.impl;

import com.mygitgor.domain.model.Store;
import com.mygitgor.domain.repository.StoreRepository;
import com.mygitgor.infrastructure.database.jpa.StoreJpaRepository;
import com.mygitgor.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {
    private final StoreJpaRepository jpaRepository;
    private final StoreMapper storeMapper;

    @Override
    public Store findByName(String name) {
        return jpaRepository.findByName(name)
                .map(storeMapper::toDto)
                .orElse(null);
    }

    @Override
    public void save(Store store) {
        jpaRepository.save(storeMapper.toEntity(store));
    }

    @Override
    public Store findById(UUID id) {
        return jpaRepository.findById(id)
                .map(storeMapper::toDto)
                .orElse(null);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Store> findAll() {
        return jpaRepository.findAll()
                .stream().map(storeMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Store store) {
        jpaRepository.delete(storeMapper.toEntity(store));
    }
}
