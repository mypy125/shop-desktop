package com.mygitgor.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product{
    private UUID id;
    private String code;
    private double price;
    private Date productionDate;
    private Date expirationDate;
}
