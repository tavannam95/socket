package com.a2m.gen.mappers;

import org.mapstruct.Mapper;

import com.a2m.gen.dto.TargetDto;
import com.a2m.gen.entities.TargetMgt;

@Mapper(componentModel = "spring", uses = {})
public interface TargetMapper extends EntityMapper<TargetDto, TargetMgt>{

}
