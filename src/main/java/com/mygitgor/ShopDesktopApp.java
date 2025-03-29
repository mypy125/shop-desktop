package com.mygitgor;

import com.mygitgor.GUI.Dashboard;
import com.mygitgor.config.AppConfig;
import com.mygitgor.application.service.ProductService;
import com.mygitgor.application.service.StockService;
import com.mygitgor.application.service.StoreService;
import com.mygitgor.application.service.StoreStockService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.*;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mygitgor.infrastructure.database.jpa")
@EntityScan(basePackages = "com.mygitgor.infrastructure.database")
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