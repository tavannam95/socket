package com.a2m.gen.mappers;

import org.mapstruct.Mapper;

import com.a2m.gen.dto.TsstRoleDto;
import com.a2m.gen.entities.TsstRole;

@Mapper(componentModel = "spring", uses = {})
public interface TsstRoleMapper extends EntityMapper<TsstRoleDto, TsstRole>{
	
}
