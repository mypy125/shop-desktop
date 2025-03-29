package com.mygitgor.mapper;

import com.mygitgor.domain.model.Store;
import com.mygitgor.infrastructure.database.StoreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreMapper extends ConfigMapper<Store,StoreEntity>{
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);
}
