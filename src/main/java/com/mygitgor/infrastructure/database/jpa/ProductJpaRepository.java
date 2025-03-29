package com.mygitgor.infrastructure.database.jpa;

import com.mygitgor.infrastructure.database.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
}
