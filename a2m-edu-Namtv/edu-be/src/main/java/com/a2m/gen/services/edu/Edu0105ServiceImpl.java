package com.a2m.gen.services.edu;

import java.sql.SQLException;
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

import com.a2m.gen.dao.TeacherDao;
import com.a2m.gen.dao.TsstUserDao;
import com.a2m.gen.dto.TsstUserRoleDto;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemClassTeacherMap;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.AemTeacherEntity;
import com.a2m.gen.entities.edu.EamTeacherInfo;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.models.edu.TeacherModel;
import com.a2m.gen.repository.edu.Edu01052Respository;
import com.a2m.gen.repository.edu.Edu0105Respository;
import com.a2m.gen.repository.sys.sys0102.Sys0102Repository;
import com.a2m.gen.services.common.ComSeqService;
import com.a2m.gen.services.common.CommonService;

@Service
public class Edu0105ServiceImpl implements Edu0105Service {
	
	@PersistenceContext
    private EntityManager entityManager;

	@Autowired
	private Edu0105Respository edu0105Respository;
	
	@Autowired
	private Edu01052Respository edu01052Respository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ComSeqService comSeqService;
	
	@Autowired
	private Sys0102Repository sys0102Repo;

    @Autowired
    private TeacherDao teacherDao;
	
	@Autowired
	private TsstUserDao tsstUserDao;
        

	@Transactional
	public TsstUser save(TsstUser tsstUser) throws Exception {
		Date dt = new Date();
		if (tsstUser.getUserUid() == null) {
			EamTeacherInfo eamTeacherInfo = tsstUser.getEamTeacherInfo();
			eamTeacherInfo.setTsstUser(tsstUser);
			tsstUser.setUserUid(comSeqService.getSeq("SEQ_USER_UID"));
			
			tsstUser.getEamTeacherInfo().setTwoFAEnable(false);

		    String password = tsstUser.getPassword();
		    String encode = passwordEncoder.encode(password);
	    	tsstUser.setPassword(encode);    	
	    	tsstUser.setCreatedBy(commonService.getUserUid());
			tsstUser.setCreatedDate(new Date());
			tsstUser.setStatus(true);
			String userId = tsstUser.getUserId();
			TsstUserModel model = new TsstUserModel();
			model.setUserId(userId);
			 List<TsstUser> tsstUserList = tsstUserDao.getTsstUserList(model);
			 if(tsstUserList.size()==1) {
				 return null;
			 }
			entityManager.persist(eamTeacherInfo);
			Long eamTeacherInfoId = eamTeacherInfo.getTeacherInfoId();
			edu0105Respository.saveTeacher(tsstUser, eamTeacherInfo);
			
			insertRoleForTeacher(tsstUser.getUserUid());
		} else {
			EamTeacherInfo eamTeacherInfo = tsstUser.getEamTeacherInfo();			
			Long eamTeacherInfoId = eamTeacherInfo.getTeacherInfoId();			
			edu01052Respository.modify(
					eamTeacherInfoId, 
					eamTeacherInfo.getFullName(),
					eamTeacherInfo.getDob(),
					
					eamTeacherInfo.getEmail(),
					eamTeacherInfo.getCellPhone(),
					
					eamTeacherInfo.getAddress(),
					eamTeacherInfo.getGender(),
					
					eamTeacherInfo.getImgPath(),
					eamTeacherInfo.getEmailVerifyKey(),
					
					eamTeacherInfo.getTwoFAEnable(),
					eamTeacherInfo.getTwoFAKey(),
					
					eamTeacherInfo.getOrganization(),
					eamTeacherInfo.getPosition(),
	                eamTeacherInfo.getDeleteFlag()
			);
			edu0105Respository.modify(
					tsstUser.getUserUid(), 
					tsstUser.getUpdatedBy(),
					
					tsstUser.getUpdatedDate(),					
					tsstUser.getPassword(),
					
					tsstUser.isStatus(),					
					tsstUser.getUserId(),
					
					tsstUser.getUserType(),
					
					eamTeacherInfoId
			);
		} 
		return tsstUser;

	}

	public TsstUser findByUserUid(String userUid) {
		TsstUser tsstUser = edu0105Respository.findByUserUid(userUid);
		return tsstUser;
	}

//	public List<TsstUser> getListUser(String userUid) {
//		return edu0105Respository.findByUserUId(userUid);
//	}

	public void updateUser(TsstUser tsstUser) throws Exception {
		save(tsstUser);

	}

	public Boolean teacherForDel(List<TsstUserModel> listTsstModel) throws Exception {
		for(TsstUserModel teacher : listTsstModel) {
			String id = teacher.getUserUid().toString();
			delete(id);
		}
		return true;
	}

	public void delete(String userUid) throws Exception{
		Optional<TsstUser> user = edu0105Respository.findById(userUid);
//		deleteRoleForTeacher(user.get().getUserUid());
//		edu0105Respository.delete(user.get());
		TsstUser tsstUser = user.get();
		EamTeacherInfo eamTeacherInfo = tsstUser.getEamTeacherInfo();         
        Long eamTeacherInfoId = eamTeacherInfo.getTeacherInfoId();      
        eamTeacherInfo.setDeleteFlag(true);
        edu01052Respository.modify(
                eamTeacherInfoId, 
                eamTeacherInfo.getFullName(),
                eamTeacherInfo.getDob(),
                
                eamTeacherInfo.getEmail(),
                eamTeacherInfo.getCellPhone(),
                
                eamTeacherInfo.getAddress(),
                eamTeacherInfo.getGender(),
                
                eamTeacherInfo.getImgPath(),
                eamTeacherInfo.getEmailVerifyKey(),
                
                eamTeacherInfo.getTwoFAEnable(),
                eamTeacherInfo.getTwoFAKey(),
                
                eamTeacherInfo.getOrganization(),
                eamTeacherInfo.getPosition(),
                eamTeacherInfo.getDeleteFlag()
        );
        TeacherModel teacherModel = new TeacherModel();
        teacherModel.setKey(eamTeacherInfoId);
        AemTeacherEntity teacher = teacherDao.getTeacher(teacherModel);
        List<AemClassTeacherMap> teacherMap = teacher.getListOfClassTeacherMap();
        for (AemClassTeacherMap element : teacherMap) {
            teacherDao.deleteTeacher(element.getTableId());
       }
	}
	
//	public int modifyStatus(Map param) {
//		String id = param.get("USER_UID").toString().trim();
//		int stt = Integer.parseInt(param.get("STATUS").toString().trim());
//		int res = 0;
//		try {
//			edu0105Respository.modifyStatus(id, stt);
//			res = 1;
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return res;
//	}

	public Page<TsstUser> searchByfullNameAndStatus(Map<String, Object> tsstUser) {
		System.out.println(tsstUser.get("page"));
		int page = (tsstUser.get("page")!= null) ? Integer.parseInt(tsstUser.get("page").toString()):0;
		int rows = (tsstUser.get("rows")!= null) ? Integer.parseInt(tsstUser.get("rows").toString()):0;
		String fullName = (tsstUser.get("fullName") != null) ? tsstUser.get("fullName").toString() : null;
		String status = (tsstUser.get("status") != null) ? tsstUser.get("status").toString() : null;

		Pageable pageable = PageRequest.of(page, rows); 
		Page<TsstUser> aaa = edu0105Respository.findByfullNameAndStatus(fullName, status, pageable);
		return edu0105Respository.findByfullNameAndStatus(fullName, status, pageable);
	}
	
	public void insertRoleForTeacher(String teacherUserUid) throws SQLException {
		TsstUserRoleDto userRole = new TsstUserRoleDto();
		userRole.setRoleId("R013");
		userRole.setUserUid(teacherUserUid);
		
		insertTsstUserRole(userRole);
	}
	
	public void deleteRoleForTeacher(String teacherUserUid) throws SQLException {
		TsstUserRoleDto userRole = new TsstUserRoleDto();
		userRole.setRoleId("R013");
		userRole.setUserUid(teacherUserUid);
		
		deleteTsstUserRole(userRole);
	}
	
	public int insertTsstUserRole(TsstUserRoleDto userRole) throws SQLException{
		return sys0102Repo.insertTsstUserRole(userRole);
	}
	
	public int deleteTsstUserRole(TsstUserRoleDto userRole) throws SQLException{
		return sys0102Repo.deleteTsstUserRole(userRole);
	}

}
