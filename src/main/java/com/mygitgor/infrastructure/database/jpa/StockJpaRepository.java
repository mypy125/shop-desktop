package com.mygitgor.infrastructure.database.jpa;

import com.mygitgor.infrastructure.database.ProductEntity;
import com.mygitgor.infrastructure.database.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, UUID> {
    Optional<StockEntity> findByProduct(ProductEntity product);
}
