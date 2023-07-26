package com.a2m.gen.services.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.TsstUserInfo;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.repository.sys.sys0103.Sys01032Respository;
import com.a2m.gen.repository.sys.sys0103.Sys0103Respository;
import com.a2m.gen.services.common.ComSeqService;
import com.a2m.gen.services.common.CommonService;

@Service
public class Sys0103ServiceImp implements Sys0103Service {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private Sys0103Respository sys0103Repository;
	
	@Autowired
	private Sys01032Respository sys01032Repository;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired 
	 private CommonService commonService;
	 
	@Autowired
	private ComSeqService comSeqService;
		

	@Transactional
	public TsstUser save(TsstUser tsstUser) throws Exception {
		Date dt = new Date();
		if (tsstUser.getUserUid() == null) {
			TsstUserInfo tsstUserInfo = tsstUser.getTsstUserInfo();
			tsstUserInfo.setTsstUser(tsstUser);
			tsstUser.setUserUid(comSeqService.getSeq("SEQ_USER_UID"));
			
			tsstUser.getTsstUserInfo().setTwoFAEnable(false);

		    String password = tsstUser.getPassword();
		    String encode = passwordEncoder.encode(password);
	    	tsstUser.setPassword(encode);    	
	    	tsstUser.setCreatedBy(commonService.getUserUid());
			tsstUser.setCreatedDate(new Date());
			tsstUser.setStatus(true);
			//tsstUser = sys0103Repository.save(tsstUser);
			
			entityManager.persist(tsstUserInfo);
			Long tsstUserInfoId = tsstUserInfo.getUserInfoId();
			sys0103Repository.saveUser(tsstUser, tsstUserInfo);
		} else {
//			TsstUserInfo tsstUserInfo = tsstUser.getTsstUserInfo();
//			tsstUserInfo.setTsstUser(tsstUser);	
//			tsstUser = sys0103Repository.save(tsstUser);
			
			TsstUserInfo tsstUserInfo = tsstUser.getTsstUserInfo();			
			Long tsstUserInfoId = tsstUserInfo.getUserInfoId();			
			sys01032Repository.modify(
					tsstUserInfoId, 
					tsstUserInfo.getFullName(),
					tsstUserInfo.getDob(),
					
					tsstUserInfo.getEmail(),
					tsstUserInfo.getCellPhone(),
					
					tsstUserInfo.getAddress(),
					tsstUserInfo.getGender(),
					
					tsstUserInfo.getImgPath(),
					tsstUserInfo.getEmailVerifyKey(),
					
					tsstUserInfo.getTwoFAEnable(),
					tsstUserInfo.getTwoFAKey(),
					
					tsstUserInfo.getOrganization(),
					tsstUserInfo.getPosition()
			);
			sys0103Repository.modify(
					tsstUser.getUserUid(), 
					tsstUser.getUpdatedBy(),
					
					tsstUser.getUpdatedDate(),					
					tsstUser.getPassword(),
					
					tsstUser.isStatus(),					
					tsstUser.getUserId(),
					
					tsstUser.getUserType(),
					
					tsstUserInfoId
			);
		} 
		return tsstUser;

	}

	@Transactional
	public TsstUser findByUserUid(String userUid) {
		TsstUser tsstUser = sys0103Repository.findByUserUid(userUid);
		return tsstUser;
	}

//	public List<TsstUser> getListUser(String userUid) {
//		return sys0103Repository.findByUserUId(userUid);
//	}

	public void updateUser(TsstUser tsstUser) throws Exception {
		save(tsstUser);

	}


	public void delete(String userUid) throws Exception{
		Optional<TsstUser> user = sys0103Repository.findById(userUid);
		sys0103Repository.delete(user.get());
	}
	
//	public int modifyStatus(Map param) {
//		String id = param.get("USER_UID").toString().trim();
//		int stt = Integer.parseInt(param.get("STATUS").toString().trim());
//		int res = 0;
//		try {
//			sys0103Repository.modifyStatus(id, stt);
//			res = 1;
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return res;
//	}

	public Page<TsstUser> searchByfullNameAndStatus(Map<String, Object> tsstUser) {
		int page = Integer.parseInt(tsstUser.get("page").toString());
		int rows = Integer.parseInt(tsstUser.get("rows").toString());
		String fullName = (tsstUser.get("fullName") != null) ? tsstUser.get("fullName").toString() : null;
		String status = (tsstUser.get("status") != null) ? tsstUser.get("status").toString() : null;

		Pageable pageable = PageRequest.of(page, rows); 
//		Page<TsstUser> aaa = sys0103Repository.findByfullNameAndStatus(fullName, status, pageable);
		return sys0103Repository.findByfullNameAndStatus(fullName, status, pageable);
	}
	
	public List<TsstUser> findByUserAssist (){
	return	sys0103Repository.findByUserAssist();
	}

	public List<TsstUser> findByTsstUserList (List<TsstUserModel> listModel){
		List<TsstUser> listTsstUser = new ArrayList<>();
		String userInfoId = "";
		String userType = "";
		for (TsstUserModel model: listModel) {
			userInfoId = model.getUserInfoId().toString();
			userType = model.getUserType();
			TsstUser tsstUser = sys0103Repository.findByTsstUserList(userInfoId, userType);
			listTsstUser.add(tsstUser);
		}
	return listTsstUser;
	}

}
