package com.mygitgor.infrastructure.database.jpa;

import com.mygitgor.infrastructure.database.StoreStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreStockJpaRepository extends JpaRepository<StoreStockEntity, UUID> {
}
