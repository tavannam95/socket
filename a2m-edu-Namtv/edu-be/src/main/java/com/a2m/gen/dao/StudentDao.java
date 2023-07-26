package com.a2m.gen.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.ClassEntity;
import com.a2m.gen.entities.edu.EamStudentInfo;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class StudentDao {
	@Autowired 
	 private CommonService commonService;
	
	 @Autowired
	 private TsstUserDao userDao;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemStudentEntity> getList(ParamBaseModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemStudentEntity> cq = cb.createQuery(AemStudentEntity.class);
        Root<AemStudentEntity> studentEntity = cq.from(AemStudentEntity.class);  
        cq.orderBy(cb.desc(studentEntity.get("studentInfoId")));  
        cq.select(studentEntity);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
           predicates.add(cb.like(cb.lower(studentEntity.get("fullName")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(room.get("roomNm"), args.getSearchText()));
        }
        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
	        predicates.add(cb.equal(studentEntity.get("status"), args.getSearchStatus()));
        }
        predicates.add(cb.equal(studentEntity.get("deleteFlag"), false));
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        if(args.getAll!=null) {
            return entityManager.createQuery(cq)
                    .getResultList();
        }
        
        return entityManager.createQuery(cq)
//        		=== start paging
        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();
    }
    
    public List<AemStudentEntity> getStudentBy(ParamBaseModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemStudentEntity> cq = cb.createQuery(AemStudentEntity.class);
        Root<AemStudentEntity> studentEntity = cq.from(AemStudentEntity.class);  
        cq.orderBy(cb.desc(studentEntity.get("studentInfoId")));  
        cq.select(studentEntity);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
           predicates.add(cb.like(cb.lower(studentEntity.get("fullName")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(room.get("roomNm"), args.getSearchText()));
        }
//        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
//	        predicates.add(cb.equal(studentEntity.get("status"), args.getSearchStatus()));
//        }
        
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
//        		=== start paging
        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();
    }
    
    public Long count(ParamBaseModel search){ 	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemStudentEntity> root = criteriaQuery.from(AemStudentEntity.class);
        criteriaQuery.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
            predicates.add(cb.like(cb.lower( root.get("fullName")), "%" + search.getSearchText().toLowerCase() + "%"));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
    	return result;
    }
    
    
    public AemStudentEntity getStudent(StudentModel studentModel) {
    	return this.entityManager.find(AemStudentEntity.class, studentModel.getKey());
    }
//    
    public EamStudentInfo getStudentinfo(StudentModel studentModel) {
    	return this.entityManager.find(EamStudentInfo.class, studentModel.getKey());
    }
//    
//    public AemClass saveClass(ClassModel model, AemClass db) {
//    	db.setClassNm(model.getClassName());
//    	db.setClassType(model.getClassType());
//    	db.setStatus(model.getClassStatus());
//    	
//		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
//		entityManager.persist(db);
//		return db;
//    }
//
    @Transactional
    public boolean deleteClassStudentMap(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    	CriteriaDelete<AemClassStudentMap> cd = cb.createCriteriaDelete(AemClassStudentMap.class);
    	Root<AemClassStudentMap> sample = cd.from(AemClassStudentMap.class);
    	
    	cd.where(cb.equal(sample.get("tableId"), id));
    	this.entityManager.createQuery(cd).executeUpdate();
		return true;
    }
//    
    public List<AemStudentEntity> getListStudentInprogress(){
    	List<AemStudentEntity> result = new ArrayList<>();
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    	String string = 
    			"  SELECT DISTINCT "
    			+" student.STUDENT_INFO_ID,student.STUDENT_CODE,student.ADDRESS,student.CELL_PHONE"
    			+" ,student.DOB,student.EMAIL,student.EMAIL_VERIFY_KEY,student.FULL_NAME"
    			+" ,student.GENDER,student.IMG_PATH,student.ORGANIZATION,student.POSITION"
    			+" ,student.2FA_ENABLE,student.2FA_KEY,student.IDEA"
    			+" ,student.`COMMENT`,student.TYPICAL_FLAG,student.DELETE_FLAG"
    			+ " FROM EAM_STUDENT_INFO student" 
    			+" LEFT JOIN EAM_CLASS_STUDENT_MAP classStudent ON student.STUDENT_INFO_ID = classStudent.STUDENT_INFO_ID" 
    			+" LEFT JOIN EAM_CLASS class ON classStudent.CLASS_ID = class.CLASS_ID"
    			+" WHERE class.START_DT <= " 
    			+":timeStamp"
//    			+ " AND class.END_DT >= "
//    			+":timeStamp"
    			+" AND class.ISFINISH <> 1"
    			;
    	Query query  = this.entityManager.createNativeQuery(string,AemStudentEntity.class).setParameter("timeStamp", timeStamp);;
    	List<AemStudentEntity> resultList = query.getResultList();
    	
//    	if (resultList.size() > 0) {
//			for (Object object : resultList) {
//				Object[] objects = (Object[]) object;
//				Long studentInfoId = ((BigInteger) objects[0]).longValue();
//				String studentCode = objects[1]!=null ? objects[1].toString() : null;
//				String address = objects[2]!=null ? objects[2].toString() : null;
//				String cellPhone = objects[3] != null ? objects[3].toString() : null;
//				((java.util.Date) objects[4]).getTime();
//				Date dob = objects[4]!=null ?  (Date) objects[4] : null;
//				String email = objects[5] != null ? objects[5].toString() : null;
//				String emailVerifyKey = objects[6] != null ? objects[6].toString() : null;
//				String fullName = objects[7] != null ? objects[7].toString() : null;
//				Boolean gender = objects[8]!=null ?  (Boolean) objects[8] : null;
//				String imgPath = objects[9] != null ? objects[9].toString() : null;
//				String organization = objects[10] != null ? objects[10].toString() : null;
//				String position = objects[11] != null ? objects[11].toString() : null;
//				Boolean twoFaKey = objects[12]!=null ?  (Boolean) objects[12] : null;
//				String idea = objects[13] != null ? objects[13].toString() : null;
//				String comment = objects[14] != null ? objects[14].toString() : null;
//				Boolean typicalFlag = objects[15]!=null ?  (Boolean) objects[15] : null;
//				Boolean deleteFlag = objects[16]!=null ?  (Boolean) objects[16] : null;
//				
//				AemStudentEntity entity = new AemStudentEntity();
//				entity.setStudentInfoId(studentInfoId);
//				entity.setStudentCode(studentCode);
//				entity.setAddress(address);
//				entity.setCellPhone(cellPhone);
//				entity.setDob(dob);
//				entity.setEmail(emailVerifyKey);
//				entity.setEmailVerifyKey(emailVerifyKey);
//				entity.setFullName(fullName);
//				entity.setGender(gender);
//				entity.setImgPath(imgPath);
//				entity.setOrganization(organization);
//				entity.setPosition(position);
//				
//				entity.setIdea(idea);
////				entity.setCo(comment);
//				entity.setTypicalFlag(typicalFlag);
//				entity.setDeleteFlag(deleteFlag);
//				
//				result.add(entity);
//			}
//    	}
    	
    	return resultList ;
    }
 
    public Long getCountStudentInprogress(){
    	Long result = new Long(0);
    	
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    	String string = 
    			" SELECT COUNT( DISTINCT  student.STUDENT_INFO_ID) "
    					+ " FROM EAM_STUDENT_INFO student" 
    					+" LEFT JOIN EAM_CLASS_STUDENT_MAP classStudent ON student.STUDENT_INFO_ID = classStudent.STUDENT_INFO_ID" 
    					+" LEFT JOIN EAM_CLASS class ON classStudent.CLASS_ID = class.CLASS_ID"
    					+" WHERE class.START_DT <= " 
    					+":timeStamp"
//    					+ " AND class.END_DT >= "
//    					+":timeStamp"
    					+" AND class.ISFINISH <> 1"
    					;
    	Query query  = this.entityManager.createNativeQuery(string).setParameter("timeStamp", timeStamp);;
    	 Object singleResult = query.getSingleResult();
    	 result =  ((BigInteger) singleResult).longValue();
    	return result ;
    }
    
    public List<AemStudentEntity> getListStudentByUserUid(String userUid)throws Exception{
    	List<AemStudentEntity> result = new ArrayList<>();
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    	
    	TsstUser tsstUser = userDao.getTsstUserByUserUid(userUid);
    	String userType = tsstUser.getUserType();
    	
    	String queryString =  "";
    	if(userType.equals("TEA")) {
    		queryString = 
    				"  SELECT DISTINCT "
    						+" student.STUDENT_INFO_ID,student.STUDENT_CODE,student.ADDRESS,student.CELL_PHONE"
    						+" ,student.DOB,student.EMAIL,student.EMAIL_VERIFY_KEY,student.FULL_NAME"
    						+" ,student.GENDER,student.IMG_PATH,student.ORGANIZATION,student.POSITION"
    						+" ,student.2FA_ENABLE,student.2FA_KEY,student.IDEA"
    						+" ,student.`COMMENT`,student.TYPICAL_FLAG,student.DELETE_FLAG"
    						+ "  FROM EAM_STUDENT_INFO student " 
    						+" LEFT JOIN EAM_CLASS_STUDENT_MAP classStudent ON student.STUDENT_INFO_ID = classStudent.STUDENT_INFO_ID" 
    						+" LEFT JOIN EAM_CLASS class  ON classStudent.CLASS_ID = class.CLASS_ID"
    						+" LEFT JOIN EAM_CLASS_TEACHER_MAP classTeacher ON  class.CLASS_ID = classTeacher.CLASS_ID"
    						+" LEFT JOIN EAM_TEACHER_INFO teacher ON classTeacher.TEACHER_INFO_ID = teacher.TEACHER_INFO_ID"
    						+" LEFT JOIN TSST_USER USER ON teacher.TEACHER_INFO_ID = USER.USER_INFO_ID"
    						+" WHERE class.START_DT <= " 
    						+":timeStamp"
    						+ " AND class.END_DT >= "
    						+":timeStamp"
    						+" AND class.`STATUS` = 1"
    						+" AND USER.USER_UID = "
    						+":userUid"
    						;
    	}else if(userType.equals("EMP")) {
    		queryString = 
    				"  SELECT DISTINCT "
    						+" student.STUDENT_INFO_ID,student.STUDENT_CODE,student.ADDRESS,student.CELL_PHONE"
    						+" ,student.DOB,student.EMAIL,student.EMAIL_VERIFY_KEY,student.FULL_NAME"
    						+" ,student.GENDER,student.IMG_PATH,student.ORGANIZATION,student.POSITION"
    						+" ,student.2FA_ENABLE,student.2FA_KEY,student.IDEA"
    						+" ,student.COMMENT,student.TYPICAL_FLAG,student.DELETE_FLAG"
    						+ "  FROM EAM_STUDENT_INFO student " 
    						+" LEFT JOIN EAM_CLASS_STUDENT_MAP classStudent ON student.STUDENT_INFO_ID = classStudent.STUDENT_INFO_ID" 
    						+" LEFT JOIN EAM_CLASS class  ON classStudent.CLASS_ID = class.CLASS_ID"
    						+" LEFT JOIN EAM_COURSE course ON class.COURSE_ID = course.COURSE_ID"
    						+" LEFT JOIN EAM_COURSE_SUBJECT_MAP courseSubject ON course.COURSE_ID = courseSubject.COURSE_ID"
    						+" LEFT JOIN EAM_COURSE_SUBJECT subject ON courseSubject.SUBJECT_ID = subject.SUBJECT_ID"
    						+" LEFT JOIN TSST_USER USER ON subject.USER_UID = USER.USER_UID"
    						+" WHERE class.START_DT <= " 
    						+":timeStamp"
    						+ " AND class.END_DT >= "
    						+":timeStamp"
    						+" AND class.STATUS = 1"
    						+" AND USER.USER_UID = "
    						+":userUid"
    						;
    	}
    	Query query  = this.entityManager.createNativeQuery(queryString,AemStudentEntity.class)
    			.setParameter("userUid", userUid)
    			.setParameter("timeStamp", timeStamp);
    	List<AemStudentEntity> resultList = query.getResultList();
    	
//    	if (resultList.size() > 0) {
//			for (Object object : resultList) {
//				Object[] objects = (Object[]) object;
//				Long studentInfoId = ((BigInteger) objects[0]).longValue();
//				String studentCode = objects[1]!=null ? objects[1].toString() : null;
//				String address = objects[2]!=null ? objects[2].toString() : null;
//				String cellPhone = objects[3] != null ? objects[3].toString() : null;
//				((java.util.Date) objects[4]).getTime();
//				Date dob = objects[4]!=null ?  (Date) objects[4] : null;
//				String email = objects[5] != null ? objects[5].toString() : null;
//				String emailVerifyKey = objects[6] != null ? objects[6].toString() : null;
//				String fullName = objects[7] != null ? objects[7].toString() : null;
//				Boolean gender = objects[8]!=null ?  (Boolean) objects[8] : null;
//				String imgPath = objects[9] != null ? objects[9].toString() : null;
//				String organization = objects[10] != null ? objects[10].toString() : null;
//				String position = objects[11] != null ? objects[11].toString() : null;
//				Boolean twoFaKey = objects[12]!=null ?  (Boolean) objects[12] : null;
//				String idea = objects[13] != null ? objects[13].toString() : null;
//				String comment = objects[14] != null ? objects[14].toString() : null;
//				Boolean typicalFlag = objects[15]!=null ?  (Boolean) objects[15] : null;
//				Boolean deleteFlag = objects[16]!=null ?  (Boolean) objects[16] : null;
//				
//				AemStudentEntity entity = new AemStudentEntity();
//				entity.setStudentInfoId(studentInfoId);
//				entity.setStudentCode(studentCode);
//				entity.setAddress(address);
//				entity.setCellPhone(cellPhone);
//				entity.setDob(dob);
//				entity.setEmail(emailVerifyKey);
//				entity.setEmailVerifyKey(emailVerifyKey);
//				entity.setFullName(fullName);
//				entity.setGender(gender);
//				entity.setImgPath(imgPath);
//				entity.setOrganization(organization);
//				entity.setPosition(position);
//				
//				entity.setIdea(idea);
////				entity.setCo(comment);
//				entity.setTypicalFlag(typicalFlag);
//				entity.setDeleteFlag(deleteFlag);
//				
//				result.add(entity);
//			}
//    	}
    	
    	return resultList ;
    }
    
    public Long getCountStudentByUserUid(String userUid){
    	Long result = new Long(0);
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    	
    	TsstUser tsstUser = userDao.getTsstUserByUserUid(userUid);
    	String userType = tsstUser.getUserType();
    	
    	String queryString =  "";
    	if(userType.equals("TEA")) {
    		queryString = 
    				" SELECT COUNT( DISTINCT  student.STUDENT_INFO_ID) "
    						+ "  FROM EAM_STUDENT_INFO student " 
    						+" LEFT JOIN EAM_CLASS_STUDENT_MAP classStudent ON student.STUDENT_INFO_ID = classStudent.STUDENT_INFO_ID" 
    						+" LEFT JOIN EAM_CLASS class  ON classStudent.CLASS_ID = class.CLASS_ID"
    						+" LEFT JOIN EAM_CLASS_TEACHER_MAP classTeacher ON  class.CLASS_ID = classTeacher.CLASS_ID"
    						+" LEFT JOIN EAM_TEACHER_INFO teacher ON classTeacher.TEACHER_INFO_ID = teacher.TEACHER_INFO_ID"
    						+" LEFT JOIN TSST_USER USER ON teacher.TEACHER_INFO_ID = USER.USER_INFO_ID"
    						+" WHERE class.START_DT <= " 
    						+":timeStamp"
    						+ " AND class.END_DT >= "
    						+":timeStamp"
    						+" AND class.`STATUS` = 1"
    						+" AND USER.USER_UID = "
    						+":userUid"
    						;
    	}else if(userType.equals("EMP")) {
    		queryString = 
    	    		queryString = 
    				" SELECT COUNT( DISTINCT  student.STUDENT_INFO_ID) "
    						+ "  FROM EAM_STUDENT_INFO student " 
    						+" LEFT JOIN EAM_CLASS_STUDENT_MAP classStudent ON student.STUDENT_INFO_ID = classStudent.STUDENT_INFO_ID" 
    						+" LEFT JOIN EAM_CLASS class  ON classStudent.CLASS_ID = class.CLASS_ID"
    						+" LEFT JOIN EAM_COURSE course ON class.COURSE_ID = course.COURSE_ID"
    						+" LEFT JOIN EAM_COURSE_SUBJECT_MAP courseSubject ON course.COURSE_ID = courseSubject.COURSE_ID"
    						+" LEFT JOIN EAM_COURSE_SUBJECT subject ON courseSubject.SUBJECT_ID = subject.SUBJECT_ID"
    						+" LEFT JOIN TSST_USER USER ON subject.USER_UID = USER.USER_UID"
    						+" WHERE class.START_DT <= " 
    						+":timeStamp"
    						+ " AND class.END_DT >= "
    						+":timeStamp"
    						+" AND class.`STATUS` = 1"
    						+" AND USER.USER_UID = "
    						+":userUid"
    						;
    	}
    	
    	Query query  = this.entityManager.createNativeQuery(queryString)    			
    			.setParameter("userUid", userUid)
    			.setParameter("timeStamp", timeStamp);;
    	 Object singleResult = query.getSingleResult();
    	 result =  ((BigInteger) singleResult).longValue();
    	return result ;
    }
    
    
}
