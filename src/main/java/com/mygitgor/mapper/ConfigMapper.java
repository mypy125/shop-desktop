package com.mygitgor.mapper;

import org.springframework.stereotype.Component;

@Component
public interface ConfigMapper <T, E>{
     E toEntity(T t);
     T toDto(E e);
}
