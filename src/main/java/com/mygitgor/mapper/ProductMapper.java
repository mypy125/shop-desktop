package com.mygitgor.mapper;

import com.mygitgor.domain.model.Product;
import com.mygitgor.infrastructure.database.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper extends ConfigMapper<Product,ProductEntity>{
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
}
