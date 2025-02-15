package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.model.Store;
import com.mygitgor.repository.StoreRepository;
import com.mygitgor.service.StoreService;

import java.util.Date;
import java.util.List;

public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public void addStore(Store store) {
        storeRepository.addStore(store);
    }

    @Override
    public void updateStore(Store store) {
        storeRepository.updateStore(store);
    }

    @Override
    public void removeStore(String storeName) {
        storeRepository.removeStore(storeName);
    }

    @Override
    public Store findStoreByName(String storeName) {
        return storeRepository.findStoreByName(storeName);
    }

    @Override
    public List<Store> findAllStores() {
        return storeRepository.findAllStores();
    }

    @Override
    public void addProductToStore(String storeName, String productCode, double price, Date productionDate, Date expirationDate, int quantity) {
        Store store = storeRepository.findStoreByName(storeName);
        if (store != null) {
            Stock stock = findStockByProductCode(store, productCode);
            if (stock == null) {
                Product product = new Product();
                product.setCode(productCode);
                product.setPrice(price);
                product.setProductionDate(productionDate);
                product.setExpirationDate(expirationDate);

                stock = new Stock();
                stock.setProductCode(productCode);
                stock.setProduct(product);
                stock.setQuantity(quantity);

                store.getStocks().add(stock);
            } else {
                stock.setQuantity(stock.getQuantity() + quantity);
            }
            storeRepository.updateStore(store);
        } else {
            throw new IllegalArgumentException("Store not found");
        }
    }

    @Override
    public void sellProduct(String storeName, String productCode, int quantity) {
        Store store = storeRepository.findStoreByName(storeName);
        if (store != null) {
            Stock stock = findStockByProductCode(store, productCode);
            if (stock != null && stock.getQuantity() >= quantity) {
                stock.setQuantity(stock.getQuantity() - quantity);
                storeRepository.updateStore(store);
            } else {
                throw new IllegalArgumentException("Not enough product in stock or product not found");
            }
        } else {
            throw new IllegalArgumentException("Store not found");
        }
    }

    @Override
    public void returnProductToStock(String storeName, String productCode, int quantity) {
        Store store = storeRepository.findStoreByName(storeName);
        if (store != null) {
            Stock stock = findStockByProductCode(store, productCode);
            if (stock != null) {
                stock.setQuantity(stock.getQuantity() + quantity);
                storeRepository.updateStore(store);
            } else {
                throw new IllegalArgumentException("Product not found");
            }
        } else {
            throw new IllegalArgumentException("Store not found");
        }
    }

    @Override
    public void checkAndReturnExpiredProducts() {
        List<Store> stores = storeRepository.findAllStores();
        Date currentDate = new Date();
        for (Store store : stores) {
            List<Stock> stocks = store.getStocks();
            for (Stock stock : stocks) {
                if (stock.getProduct().getExpirationDate().before(currentDate)) {
                    stock.setQuantity(0);
                }
            }
            storeRepository.updateStore(store);
        }
    }

    private Stock findStockByProductCode(Store store, String productCode) {
        for (Stock stock : store.getStocks()) {
            if (stock.getProductCode().equals(productCode)) {
                return stock;
            }
        }
        return null;
    }
}
