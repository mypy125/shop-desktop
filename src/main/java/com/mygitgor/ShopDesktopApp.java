package com.mygitgor;

import com.mygitgor.GUI.Dashboard;
import com.mygitgor.config.AppConfig;
import com.mygitgor.service.ProductService;
import com.mygitgor.service.StockService;
import com.mygitgor.service.StoreService;
import com.mygitgor.service.StoreStockService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class ShopDesktopApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            ProductService productService = context.getBean(ProductService.class);
            StockService stockService = context.getBean(StockService.class);
            StoreService storeService = context.getBean(StoreService.class);
            StoreStockService storeStockService = context.getBean(StoreStockService.class);

            Dashboard dashboard = new Dashboard(productService, stockService, storeService,storeStockService);
            dashboard.setVisible(true);
        });
    }
}