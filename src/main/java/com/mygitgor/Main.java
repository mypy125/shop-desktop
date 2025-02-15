package com.mygitgor;

import com.mygitgor.GUI.Dashboard;
import com.mygitgor.repository.inMemory.InMemoryProductRepository;
import com.mygitgor.repository.inMemory.InMemoryStockRepository;
import com.mygitgor.repository.inMemory.InMemoryStoreRepository;
import com.mygitgor.service.ProductService;
import com.mygitgor.service.StockService;
import com.mygitgor.service.StoreService;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductService productService = new ProductService(new InMemoryProductRepository());
            StockService stockService = new StockService(new InMemoryStockRepository());
            StoreService storeService = new StoreService(new InMemoryStoreRepository());

            Dashboard dashboard = new Dashboard(productService, stockService, storeService);
            dashboard.setVisible(true);
        });
    }
}