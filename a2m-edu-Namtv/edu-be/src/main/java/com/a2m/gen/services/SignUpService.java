package com.a2m.gen.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.TsstUserInfo;
import com.a2m.gen.repository.TsstUserInfoRepository;
import com.a2m.gen.repository.signUp.SignUpRepository;
import com.a2m.gen.services.common.ComSeqService;

@Service
public class SignUpService {
	@Autowired 
	SignUpRepository signUpRespository;
	@Autowired
    PasswordEncoder passwordEncoder;
	@Autowired
	ComSeqService comSeqService;
	@Autowired
	private TsstUserInfoRepository tsstUserInfoRepo;
	
	@Transactional(rollbackFor = Exception.class)
	public int insertUser(TsstUser param) throws SQLException {
		try {
			TsstUserInfo tsstUserInfo = param.getTsstUserInfo();
			Boolean temp = false;
			tsstUserInfo.setTwoFAEnable(temp);
			tsstUserInfo =  tsstUserInfoRepo.save(tsstUserInfo);
			String userUid = comSeqService.getSeq("SEQ_USER_UID");
			String password = passwordEncoder.encode(param.getPassword());
//			Map<String, Object> map = signUpRespository.insertTsstUserInfo(fullName, dob, email, cellPhone, address, gender, emailKey, twoFaKey,organization);
//			String userInfoId = signUpRespository.getLastInsertedId();
			signUpRespository.insertTsstUser(userUid, param.getUserId(), tsstUserInfo.getUserInfoId(), password);
			signUpRespository.setRoleUser(CommonContants.USER_ROLE, userUid);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		
	}
	public List<Map<Object, Object>> getListPosition(){
		List<Map<Object, Object>> listPos = null;
		try {
			listPos = signUpRespository.getListPosition();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listPos;
	}
}
