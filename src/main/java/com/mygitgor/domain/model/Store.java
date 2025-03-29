package com.mygitgor.domain.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private UUID id;
    private String name;
    private List<StoreStock> storeStocks;
}
