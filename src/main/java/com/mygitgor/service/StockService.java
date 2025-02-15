package com.mygitgor.service;

import com.mygitgor.model.Stock;
import com.mygitgor.repository.StockRepository;

public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void addStock(Stock stock) {
        stockRepository.addStock(stock);
    }
}
