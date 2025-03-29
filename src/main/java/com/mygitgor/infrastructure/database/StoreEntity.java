package com.mygitgor.infrastructure.database;

import com.mygitgor.domain.model.StoreStock;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StoreStock> storeStocks;
}
