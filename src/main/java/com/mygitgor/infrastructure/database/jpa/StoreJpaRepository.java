package com.mygitgor.infrastructure.database.jpa;

import com.mygitgor.infrastructure.database.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreJpaRepository extends JpaRepository<StoreEntity, UUID> {
    Optional<StoreEntity> findByName(String name);
}
