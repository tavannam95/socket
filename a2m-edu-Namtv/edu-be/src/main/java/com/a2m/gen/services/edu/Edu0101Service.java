package com.a2m.gen.services.edu;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.models.edu.StudentModel;

public interface Edu0101Service {
	public TsstUser save(TsstUser tsstUser) throws Exception;

    public List<TsstUser> saveForExcel(List<TsstUser> listTsstUser) throws Exception;

	public TsstUser findByUserUid(String userUid);

	public TsstUser getTsstUserByUserInfoId(Long userId);

	void updateUser(TsstUser tsstUser) throws Exception;
	
	public void delete(String userUid) throws Exception;

	public Boolean studentForDel(List<TsstUserModel> listTsstModel) throws Exception;
	
//	public int modifyStatus(Map param);
	
	public Page<TsstUser> searchByfullNameAndStatus(Map<String, Object> tsstUser) ;
	
	List<AemStudentEntity> getList(ParamSearchModel model);
	
	public List<StudentModel> getListStudentInprogress ();
	
	public Long getCountStudentInprogress ();
	
	public List<StudentModel> getListStudentByUserUid (String userUid) throws Exception;
	
	public Long getCountStudentByUserUid (String userUid);

    public List<StudentModel> importExcelMulti (MultipartFile file);

    public String exportExcel (List<TsstUser> tsstUser) throws Exception;

    public String exportList (StudentModel search) throws Exception;
}
