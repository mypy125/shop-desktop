package com.mygitgor.model;

import java.util.List;
import java.util.Objects;

public class Store {
    private String name;
    private List<Stock> stocks;

    public Store() {
    }

    public Store(String name, List<Stock> stocks) {
        this.name = name;
        this.stocks = stocks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(name, store.name) && Objects.equals(stocks, store.stocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, stocks);
    }
}
