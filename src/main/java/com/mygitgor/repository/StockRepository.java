package com.mygitgor.repository;

import com.mygitgor.model.Product;
import com.mygitgor.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    Stock findByProduct(Product product);
}
