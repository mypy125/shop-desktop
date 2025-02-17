package com.mygitgor.service.impl;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import com.mygitgor.model.Store;
import com.mygitgor.model.StoreStock;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;
import com.mygitgor.repository.StoreRepository;
import com.mygitgor.repository.StoreStockRepository;
import com.mygitgor.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final StoreStockRepository storeStockRepository;
    private final StoreRepository storeRepository;

    @Override
    public void addStock(String productCode, int quantity) {
        Product product = productRepository.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Product with code " + productCode + " not found.");
        }

        Stock stock = stockRepository.findByProductCode(productCode);
        if (stock == null) {
            stock = new Stock();
            stock.setProductCode(productCode);
            stock.setProduct(product);
            stock.setQuantity(quantity);
        } else {
            stock.setQuantity(stock.getQuantity() + quantity);
        }
        stockRepository.save(stock);
    }

    @Override
    public void updateStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public void removeStock(String productCode) {
        stockRepository.deleteByProductCode(productCode);
    }

    @Override
    public Stock findStockByProductCode(String productCode) {
        return stockRepository.findByProductCode(productCode);
    }

    @Override
    public List<Stock> findAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public void moveStockToStore(String storeName, String productCode, int quantity) {
        Store store = storeRepository.findByName(storeName);
        Stock stock = stockRepository.findByProductCode(productCode);

        if (stock.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        StoreStock storeStock = storeStockRepository.findByStoreAndStock(store, stock)
                .orElse(new StoreStock(store, stock, 0));

        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);

        storeStock.setQuantity(storeStock.getQuantity() + quantity);
        storeStockRepository.save(storeStock);
    }



}
