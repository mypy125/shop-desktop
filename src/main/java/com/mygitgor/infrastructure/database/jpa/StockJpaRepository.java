package com.mygitgor.infrastructure.database.jpa;

import com.mygitgor.infrastructure.database.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockJpaRepository extends JpaRepository<StockEntity, UUID> {
}
