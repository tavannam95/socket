package com.a2m.auth.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.a2m.auth.dao.RoleDao;
import com.a2m.auth.dao.UserDao;
import com.a2m.auth.model.Role;
import com.a2m.auth.model.response.UserResponse;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserDao tsstUserDao;
	@Autowired
	private RoleDao tsstRoleDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserResponse user = tsstUserDao.getTsstUserByUserId(username);
		if (user == null) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}else {
			List<Role> roles = tsstRoleDao.getRoleByUserUid(user.getUserUid());
			user.setRoles(roles);
		}
		return new UserDetailsImpl(user);
	}

}
