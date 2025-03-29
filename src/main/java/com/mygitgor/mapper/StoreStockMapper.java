package com.mygitgor.mapper;

import com.mygitgor.domain.model.StoreStock;
import com.mygitgor.infrastructure.database.StoreStockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface StoreStockMapper extends ConfigMapper<StoreStock,StoreStockEntity> {
    StoreStockMapper INSTANCE = Mappers.getMapper(StoreStockMapper.class);
}
