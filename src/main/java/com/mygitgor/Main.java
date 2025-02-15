package com.mygitgor;

import com.mygitgor.GUI.Dashboard;
import com.mygitgor.repository.ProductRepository;
import com.mygitgor.repository.StockRepository;
import com.mygitgor.repository.StoreRepository;
import com.mygitgor.repository.inMemory.InMemoryProductRepository;
import com.mygitgor.repository.inMemory.InMemoryStockRepository;
import com.mygitgor.repository.inMemory.InMemoryStoreRepository;
import com.mygitgor.service.ProductService;
import com.mygitgor.service.StockService;
import com.mygitgor.service.StoreService;
import com.mygitgor.service.impl.ProductServiceImpl;
import com.mygitgor.service.impl.StockServiceImpl;
import com.mygitgor.service.impl.StoreServiceImpl;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductRepository productRepository = new InMemoryProductRepository();
            StockRepository stockRepository = new InMemoryStockRepository();
            ProductService productService = new ProductServiceImpl(productRepository);
            StockService stockService = new StockServiceImpl(
                    stockRepository, productRepository
            );
            StoreService storeService = new StoreServiceImpl(new InMemoryStoreRepository());

            Dashboard dashboard = new Dashboard(productService, stockService, storeService);
            dashboard.setVisible(true);
        });
    }
}