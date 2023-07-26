package com.a2m.gen.services.edu;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.models.TsstUserModel;

public interface Edu0105Service {
	public TsstUser save(TsstUser tsstUser) throws Exception;

	public TsstUser findByUserUid(String userUid);


	void updateUser(TsstUser tsstUser) throws Exception;
	
	public void delete(String userUid) throws Exception;
	
//	public int modifyStatus(Map param);
	
	public Page<TsstUser> searchByfullNameAndStatus(Map<String, Object> tsstUser) ;

	public Boolean teacherForDel(List<TsstUserModel> listTsstModel) throws Exception;
	
}
