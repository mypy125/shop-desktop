package com.mygitgor.service;


import com.mygitgor.model.Store;
import com.mygitgor.model.StoreStock;

public interface StoreStockService {
    StoreStock addStockToStore(Store store, String productCode, int quantity);
    StoreStock findStoreStockByProductCode(Store store, String productCode);
    void updateStockQuantity(StoreStock storeStock, int quantity);
}

