package com.mygitgor.mapper;

public interface ConfigMapper <T, E>{
     E toEntity(T t);
     T toDto(E e);
}
