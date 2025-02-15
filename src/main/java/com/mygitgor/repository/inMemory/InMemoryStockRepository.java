package com.mygitgor.repository.inMemory;

import com.mygitgor.model.Stock;
import com.mygitgor.repository.StockRepository;

import java.util.*;

public class InMemoryStockRepository implements StockRepository {
    private final Map<String, Stock> stocks = new HashMap<>();

    @Override
    public void addStock(String productCode, int quantity) {
        Stock stock = stocks.get(productCode);
        if (stock == null) {
            stock = new Stock(productCode, quantity);
        } else {
            stock.setQuantity(stock.getQuantity() + quantity);
        }
        stocks.put(productCode, stock);
    }

    @Override
    public void updateStock(Stock stock) {
        stocks.put(stock.getProductCode(), stock);
    }

    @Override
    public void removeStock(String productCode) {
        stocks.remove(productCode);
    }

    @Override
    public Stock findStockByProductCode(String productCode) {
        return stocks.get(productCode);
    }

    @Override
    public List<Stock> findAllStocks() {
        return new ArrayList<>(stocks.values());
    }
}
