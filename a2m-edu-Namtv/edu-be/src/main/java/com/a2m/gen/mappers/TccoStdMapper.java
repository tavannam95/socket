package com.a2m.gen.mappers;

import org.mapstruct.Mapper;

import com.a2m.gen.dto.TccoStdDto;
import com.a2m.gen.entities.TccoStd;

@Mapper(componentModel = "spring", uses = {})
public interface TccoStdMapper extends EntityMapper<TccoStdDto, TccoStd>{

}
