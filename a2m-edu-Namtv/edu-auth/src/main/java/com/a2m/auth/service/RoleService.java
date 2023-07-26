package com.a2m.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.auth.dao.RoleDao;
import com.a2m.auth.model.Role;

@Service
public class RoleService {
	@Autowired
	private RoleDao tsstRoleDao;
	
	public List<Role> getRoleByUserUid(String userUid) {
		return tsstRoleDao.getRoleByUserUid(userUid);
	}
}
