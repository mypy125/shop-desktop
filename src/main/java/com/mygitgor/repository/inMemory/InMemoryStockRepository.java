package com.mygitgor.repository.inMemory;

import com.mygitgor.model.Stock;
import com.mygitgor.repository.StockRepository;

import java.util.*;

public class InMemoryStockRepository implements StockRepository {
    private final Map<String, Stock> stockMap = new HashMap<>();

    @Override
    public void addStock(Stock stock) {
        stockMap.put(stock.getProduct().getCode(), stock);
    }

    @Override
    public void updateStock(Stock stock) {
        if (stockMap.containsKey(stock.getProduct().getCode())) {
            stockMap.put(stock.getProduct().getCode(), stock);
        }
    }

    @Override
    public void removeStock(String productCode) {
        stockMap.remove(productCode);
    }

    @Override
    public Stock findStockByProductCode(String productCode) {
        return stockMap.get(productCode);
    }

    @Override
    public List<Stock> findAllStocks() {
        return new ArrayList<>(stockMap.values());
    }
}
