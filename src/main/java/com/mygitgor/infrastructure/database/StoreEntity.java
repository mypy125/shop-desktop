package com.mygitgor.infrastructure.database;

import com.mygitgor.domain.model.StoreStock;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StoreStock> storeStocks;
}
