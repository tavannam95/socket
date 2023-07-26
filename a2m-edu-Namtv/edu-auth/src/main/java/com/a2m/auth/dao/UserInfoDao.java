package com.a2m.auth.dao;

import org.apache.ibatis.annotations.Mapper;

import com.a2m.auth.model.request.TsstUserRequest;

@Mapper
public interface UserInfoDao {
	int save (TsstUserRequest request);
}
