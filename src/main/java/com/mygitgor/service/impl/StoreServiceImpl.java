package com.mygitgor.service.impl;

import com.mygitgor.model.Store;
import com.mygitgor.repository.StoreRepository;
import com.mygitgor.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Transactional
    @Override
    public void addStore(Store store) {
        storeRepository.save(store);
    }

    @Transactional(readOnly = true)
    @Override
    public Store findByName(String name) {
        return storeRepository.findByName(name);
    }

    @Override
    public Store findById(Long id) throws Exception {
        return storeRepository.findById(id)
                .orElseThrow(()->new Exception("store not found by id"+id));
    }

    @Transactional
    @Override
    public void updateStore(Store store) {
        if (storeRepository.existsById(store.getId())) {
            storeRepository.save(store);
        } else {
            throw new IllegalArgumentException("Store not found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteStore(String name) {
        Store store = storeRepository.findByName(name);
        if (store != null) {
            storeRepository.delete(store);
        } else {
            throw new IllegalArgumentException("Store not found");
        }
    }

    @Override
    public void deleteStore(Long id) throws Exception {
        Store store = findById(id);
        storeRepository.delete(store);
    }

}
