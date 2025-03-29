package com.mygitgor.infrastructure.database;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String code;

    private double price;

    @Temporal(TemporalType.DATE)
    private Date productionDate;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;
}
