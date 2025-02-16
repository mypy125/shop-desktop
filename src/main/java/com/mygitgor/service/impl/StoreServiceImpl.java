package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.model.Store;
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
            store.getStocks().add(stock);
        } else {
            stock.setQuantity(stock.getQuantity() + quantity);
        }

        storeRepository.save(store);
    }

    @Override
    public void sellProduct(String storeName, String productCode, int quantity) {
        Store store = storeRepository.findByName(storeName);
        if (store == null) {
            throw new IllegalArgumentException("Store not found: " + storeName);
        }

        Stock stock = findStockByProductCode(store, productCode);
        if (stock == null || stock.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough product in stock or product not found.");
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        storeRepository.save(store);
    }

    @Override
    public void returnProductToStock(String storeName, String productCode, int quantity) {
        Store store = storeRepository.findByName(storeName);
        if (store == null) {
            throw new IllegalArgumentException("Store not found: " + storeName);
        }

        Stock stock = findStockByProductCode(store, productCode);
        if (stock == null) {
            throw new IllegalArgumentException("Product not found in store.");
        }

        stock.setQuantity(stock.getQuantity() + quantity);
        storeRepository.save(store);
    }

    @Override
    public void checkAndReturnExpiredProducts() {
        List<Store> stores = storeRepository.findAll();
        Date currentDate = new Date();
        for (Store store : stores) {
            for (Stock stock : store.getStocks()) {
                if (stock.getProduct().getExpirationDate().before(currentDate)) {
                    stock.setQuantity(0);
                }
            }
            storeRepository.save(store);
        }
    }

    private Stock findStockByProductCode(Store store, String productCode) {
        return store.getStocks().stream()
                .filter(stock -> stock.getProductCode().equals(productCode))
                .findFirst()
                .orElse(null);
    }
}
