package com.a2m.gen.services.sys;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.a2m.gen.entities.TargetMgt;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.repository.sys.sys0103.Sys0103Respository;

public interface Sys0103Service {
	public TsstUser save(TsstUser tsstUser) throws Exception;

	public TsstUser findByUserUid(String userUid);


	void updateUser(TsstUser tsstUser) throws Exception;
	
	public void delete(String userUid) throws Exception;
	
//	public int modifyStatus(Map param);
	
	public Page<TsstUser> searchByfullNameAndStatus(Map<String, Object> tsstUser) ;
	
	public List<TsstUser> findByUserAssist();

	public List<TsstUser> findByTsstUserList(List<TsstUserModel> model);
	


}