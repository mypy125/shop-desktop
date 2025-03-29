package com.mygitgor.mapper;

import com.mygitgor.domain.model.Stock;
import com.mygitgor.infrastructure.database.StockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StockMapper extends ConfigMapper<Stock,StockEntity>{
    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

}
