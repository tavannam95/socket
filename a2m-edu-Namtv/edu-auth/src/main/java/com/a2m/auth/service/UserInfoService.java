package com.a2m.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.auth.dao.UserInfoDao;
import com.a2m.auth.model.request.TsstUserRequest;

@Service
public class UserInfoService {
	@Autowired
	private UserInfoDao empMgtDao;

	public TsstUserRequest save(TsstUserRequest user) {
		if (empMgtDao.save(user) > 0) {
			return user;
		}
		return null;
	}
}
