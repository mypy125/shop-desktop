package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.model.Store;
import com.mygitgor.model.StoreStock;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;
import com.mygitgor.repository.StoreRepository;
import com.mygitgor.repository.StoreStockRepository;
import com.mygitgor.service.ProductService;
import com.mygitgor.service.StockService;
import com.mygitgor.service.StoreService;
import com.mygitgor.service.StoreStockService;
import lombok.AllArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final StoreStockRepository storeStockRepository;
    private final StockRepository stockRepository;

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

    @Transactional
    @Override
    public void returnProductToStock(String storeName, String productCode, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Store store = storeRepository.findByName(storeName);
        if (store == null) {
            throw new IllegalArgumentException("Store not found with name: " + storeName);
        }

        Product product = productRepository.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Product not found with code: " + productCode);
        }

        Stock stockByProduct = stockRepository.findByProduct(product);
        if (stockByProduct == null) {
            stockByProduct = new Stock();
            stockByProduct.setProduct(product);
            stockByProduct.setQuantity(0);
            stockRepository.save(stockByProduct);
        }

        StoreStock storeStock = storeStockRepository.findByStoreAndStock(store, stockByProduct);

        if (storeStock == null) {
            storeStock = new StoreStock();
            storeStock.setStore(store);
            storeStock.setStock(stockByProduct);
            storeStock.setQuantity(0);
        }

        stockByProduct.setQuantity(stockByProduct.getQuantity() + quantity);
        stockRepository.save(stockByProduct);

        storeStock.setQuantity(storeStock.getQuantity() - quantity);
        storeStockRepository.save(storeStock);
    }


}
