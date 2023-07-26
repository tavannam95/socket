package com.a2m.mail.server.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
	
	List<Map<Object, Object>> getAllUser() throws Exception;

	int updateUser(Map<Object, Object> map) throws Exception;

	int insertUser(Map<Object, Object> map) throws Exception;
}
