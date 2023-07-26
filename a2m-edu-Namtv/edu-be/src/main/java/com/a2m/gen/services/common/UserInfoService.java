package com.a2m.gen.services.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.entities.TsstRole;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.repository.TsstUserRepository;

@Service
public class UserInfoService {
	
	@Autowired
	private TsstUserRepository userRepo;
	
	public Map<String, Object> getUserInfo(String userUid) {
//		Optional<TsstUser> tsstUser = userRepo.findById(userUid);
		TsstUser user = userRepo.findByUserUid(userUid);
		String userType = user.getUserType();
		
		List<TsstRole> roles = user.getRoles();
		for(int i = 0 ; i < roles.size(); i++) {
			TsstRole role = roles.get(i);
			role.setUsers(null);
			
		}
		
		Map<String, Object> userInfo = null;
		
		if( userType.equals("EMP") ) {
			userInfo =  userRepo.getUserEMPInfo(userUid);
		}else if( userType.equals("TEA") ) {
			userInfo =  userRepo.getUserTEAInfo(userUid);
		}else {
			userInfo =  userRepo.getUserSTUInfo(userUid);
		}
		
		Map<String, Object> userResponse = new HashMap<>();
		userResponse.putAll(userInfo);
		userResponse.put("roles", roles);
		return userResponse;
	}
	
	public Map<String, Object> getUserInfo2(String userUid) {
//		Optional<TsstUser> tsstUser = userRepo.findById(userUid);
		TsstUser user = userRepo.findByUserUid(userUid);
		String userType = user.getUserType();
		
		Map<String, Object> userInfo = null;
		
		if( userType.equals("EMP") ) {
			userInfo =  userRepo.getUserEMPInfo(userUid);
		}else if( userType.equals("TEA") ) {
			userInfo =  userRepo.getUserTEAInfo(userUid);
		}else {
			userInfo =  userRepo.getUserSTUInfo(userUid);
		}
		
		Map<String, Object> userResponse = new HashMap<>();
		userResponse.putAll(userInfo);
		return userResponse;
	}
}
