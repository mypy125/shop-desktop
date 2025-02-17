package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.model.Store;
import com.mygitgor.model.StoreStock;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;
import com.mygitgor.repository.StoreRepository;
import com.mygitgor.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    @Override
    public void addStore(Store store) {
        storeRepository.save(store);
    }

    @Override
    public void updateStore(Store store) {
        storeRepository.save(store);
    }

    @Override
    public void removeStore(String storeName) {
        Store store = storeRepository.findByName(storeName);
        if (store != null) {
            storeRepository.delete(store);
        } else {
            throw new IllegalArgumentException("Store not found: " + storeName);
        }
    }

    @Override
    public Store findStoreByName(String storeName) {
        return storeRepository.findByName(storeName);
    }

    @Override
    public List<Store> findAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public void addProductToStore(String storeName, String productCode, double price, Date productionDate, Date expirationDate, int quantity) {
        Store store = storeRepository.findByName(storeName);
        if (store == null) {
            throw new IllegalArgumentException("Store not found: " + storeName);
        }

        Product product = productRepository.findByCode(productCode);
        if (product == null) {
            product = new Product(productCode, price, productionDate, expirationDate);
            productRepository.save(product);
        }

        Stock stock = stockRepository.findByProductCode(productCode);
        if (stock == null) {
            stock = new Stock();
            stock.setProductCode(productCode);
            stock.setProduct(product);
            stock.setQuantity(quantity);
            stockRepository.save(stock);
        }

        StoreStock storeStock = new StoreStock();
        storeStock.setStore(store);
        storeStock.setStock(stock);
        storeStock.setQuantity(quantity);

        store.getStoreStocks().add(storeStock);
        storeRepository.save(store);

    }

    @Override
    public void sellProduct(String storeName, String productCode, int quantity) {
        Store store = storeRepository.findByNameWithStocks(storeName);
        if (store == null) {
            throw new IllegalArgumentException("Store not found: " + storeName);
        }

        StoreStock storeStock = findStoreStockByProductCode(store, productCode);
        if (storeStock == null || storeStock.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough product in stock or product not found.");
        }

        storeStock.setQuantity(storeStock.getQuantity() - quantity);
        storeRepository.save(store);
    }


    @Override
    public void returnProductToStock(String storeName, String productCode, int quantity) {
        Store store = storeRepository.findByNameWithStocks(storeName);
        if (store == null) {
            throw new IllegalArgumentException("Store not found: " + storeName);
        }

        StoreStock storeStock = findStoreStockByProductCode(store, productCode);
        if (storeStock == null) {
            throw new IllegalArgumentException("Product not found in store.");
        }

        storeStock.setQuantity(storeStock.getQuantity() + quantity);
        storeRepository.save(store);
    }


    @Override
    public void checkAndReturnExpiredProducts() {
        List<Store> stores = storeRepository.findAll();
        Date currentDate = new Date();

        for (Store store : stores) {
            for (StoreStock storeStock : store.getStoreStocks()) {
                Product product = storeStock.getStock().getProduct();

                if (product.getExpirationDate().before(currentDate)) {
                    storeStock.setQuantity(0);
                }
            }
            storeRepository.save(store);
        }
    }

    private StoreStock findStoreStockByProductCode(Store store, String productCode) {
        return store.getStoreStocks().stream()
                .filter(storeStock -> storeStock.getStock().getProductCode().equals(productCode))
                .findFirst()
                .orElse(null);
    }

}
