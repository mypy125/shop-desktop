package com.mygitgor.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private UUID id;
    private Product product;
    private int quantity;
}


