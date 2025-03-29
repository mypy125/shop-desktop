package com.mygitgor.infrastructure.database.jpa;

import com.mygitgor.infrastructure.database.StoreStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreStockJpaRepository extends JpaRepository<StoreStockEntity, UUID> {
    List<StoreStockEntity> findByStoreId(UUID storeId);
    List<StoreStockEntity> findByStockId(UUID stockId);
    Optional<StoreStockEntity> findByStoreIdAndStockId(UUID storeId, UUID stockId);
}
