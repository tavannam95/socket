package com.a2m.gen.mappers;

import org.mapstruct.Mapper;

import com.a2m.gen.dto.TsstUserDto;
import com.a2m.gen.entities.TsstUser;

@Mapper(componentModel = "spring", uses = {})
public interface TsstUserMapper extends EntityMapper<TsstUserDto,TsstUser>{

}
