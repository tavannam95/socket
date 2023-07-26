package com.a2m.auth.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.a2m.auth.model.request.TsstUserRequest;
import com.a2m.auth.model.response.UserResponse;

@Mapper
public interface UserDao {
	int saveTsstUser(TsstUserRequest tsstUser) throws SQLException;
	UserResponse getTsstUserByUserUid(String userUid);
	UserResponse getTsstUserByUserId(String userId);
	int existsByEmail(String email);
	int existsByUserId(String userId);
	int saveUserRole(Map<String, Object> arg) throws SQLException;
	UserResponse getTsstUserByEmail(String email);
}
