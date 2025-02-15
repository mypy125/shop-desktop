package com.mygitgor.service;

import com.mygitgor.model.Store;

import java.util.Date;
import java.util.List;

public interface StoreService {
    void addStore(Store store);
    void updateStore(Store store);
    void removeStore(String storeName);
    Store findStoreByName(String storeName);
    List<Store> findAllStores();
    void addProductToStore(String storeName,String productCode,double price,Date productionDate,Date expirationDate, int quantity);
    void sellProduct(String storeName, String productName, int quantity);
    void returnProductToStock(String storeName, String productName, int quantity);
    void checkAndReturnExpiredProducts();
}
