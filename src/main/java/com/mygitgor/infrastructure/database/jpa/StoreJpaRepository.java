package com.mygitgor.infrastructure.database.jpa;

import com.mygitgor.infrastructure.database.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreJpaRepository extends JpaRepository<StoreEntity, UUID> {
    Optional<StoreEntity> findByName(String name);
}
