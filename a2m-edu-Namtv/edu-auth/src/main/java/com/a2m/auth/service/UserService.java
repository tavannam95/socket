package com.a2m.auth.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.auth.dao.UserDao;
import com.a2m.auth.model.request.TsstUserRequest;
import com.a2m.auth.model.response.UserResponse;

@Service
public class UserService {
	@Autowired
	private ComSeqService comSeqService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDao tsstUserDao;
	@Autowired
	private TwoFactorAuthService twoFactorAuthService;
	@Autowired
	private UserInfoService empMgtService;
	
	@Transactional(rollbackFor = Exception.class)
	public Object saveTsstUser(TsstUserRequest tsstUser) throws SQLException {
		tsstUser.setEmailVerifyKey(twoFactorAuthService.generateSecretKey());
		tsstUser.setUserUid(comSeqService.getSeq("SEQ_USER_UID"));
		tsstUser.setTwoFactorAuthEnable(false);
		tsstUser.setPassword(passwordEncoder.encode(tsstUser.getPassword()));
		empMgtService.save(tsstUser);
		tsstUserDao.saveTsstUser(tsstUser);
		Map<String, Object> userRole = new HashMap<>();
		userRole.put("userUid", tsstUser.getUserUid());
		userRole.put("roles", tsstUser.getRoles());
		tsstUserDao.saveUserRole(userRole);
		
		return tsstUser;
	}
	
	public UserResponse getTsstUserByUserUid(String userUid) {
		return tsstUserDao.getTsstUserByUserUid(userUid);
	}
	
	public UserResponse getTsstUserByUserId(String userId) {
		return tsstUserDao.getTsstUserByUserId(userId);
	}
	
	public UserResponse getTsstUserByEmail(String email) {
		return tsstUserDao.getTsstUserByEmail(email);
	}
	
	public boolean existsByEmail(String email) {
		if (tsstUserDao.existsByEmail(email)> 0) {
			return true;
		}
		return false;
	}
	
	public boolean existsByUserId(String userId) {
		if (tsstUserDao.existsByUserId(userId)> 0) {
			return true;
		}
		return false;
	}
}
