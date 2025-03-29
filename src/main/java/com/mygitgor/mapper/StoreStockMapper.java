package com.mygitgor.mapper;

import com.mygitgor.domain.model.StoreStock;
import com.mygitgor.infrastructure.database.StoreStockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreStockMapper extends ConfigMapper<StoreStock,StoreStockEntity> {
    StoreStockMapper INSTANCE = Mappers.getMapper(StoreStockMapper.class);
}
