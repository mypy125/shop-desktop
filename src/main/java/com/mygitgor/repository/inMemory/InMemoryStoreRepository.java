package com.mygitgor.repository.inMemory;

import com.mygitgor.model.Store;
import com.mygitgor.repository.StoreRepository;

import java.util.*;

public class InMemoryStoreRepository implements StoreRepository {
    private final Map<String, Store> storeMap = new HashMap<>();

    @Override
    public void addStore(Store store) {
        storeMap.put(store.getName(), store);
    }

    @Override
    public void updateStore(Store store) {
        if (storeMap.containsKey(store.getName())) {
            storeMap.put(store.getName(), store);
        }
    }

    @Override
    public void removeStore(String storeName) {
        storeMap.remove(storeName);
    }

    @Override
    public Store findStoreByName(String storeName) {
        return storeMap.get(storeName);
    }

    @Override
    public List<Store> findAllStores() {
        return new ArrayList<>(storeMap.values());
    }
}
