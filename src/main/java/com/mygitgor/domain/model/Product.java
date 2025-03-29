package com.mygitgor.domain.model;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product{
    private UUID id;
    private String code;
    private double price;
    private Date productionDate;
    private Date expirationDate;
}
