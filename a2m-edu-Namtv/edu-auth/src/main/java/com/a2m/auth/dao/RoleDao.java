package com.a2m.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.a2m.auth.model.Role;

@Mapper
public interface RoleDao {
	List<Role> getRoleByUserUid(String userUid);
}
