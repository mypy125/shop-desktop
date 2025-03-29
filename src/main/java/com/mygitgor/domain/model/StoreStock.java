package com.mygitgor.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreStock {
    private UUID id;
    private Store store;
    private Stock stock;
    private int quantity;
}

