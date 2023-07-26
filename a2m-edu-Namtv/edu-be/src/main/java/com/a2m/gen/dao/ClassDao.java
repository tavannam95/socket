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

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Repository
public class ClassDao {
	@Autowired
	private CommonService commonService;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private TsstUserDao userDao;

	@Autowired
	private ClassStudentMapDao classStudentMapDao;

	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
	private EntityManager entityManager;

	public List<AemClass> get(ClassModel args) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AemClass> cq = cb.createQuery(AemClass.class);
		Root<AemClass> classEntity = cq.from(AemClass.class);
		cq.orderBy(cb.desc(classEntity.get("classId")));
		cq.select(classEntity);

//        where start
		List<Predicate> predicates = new ArrayList<>();
		if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
			predicates
					.add(cb.like(cb.lower(classEntity.get("classNm")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(room.get("roomNm"), args.getSearchText()));
		}
		if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
			predicates.add(cb.equal(classEntity.get("status"), args.getSearchStatus()));
		}
		if (args.getRows() == null) {
			if (args.getCourseId() != null && !"".equals(args.getCourseId())) {
				predicates.add(cb.equal(classEntity.get("course"), args.getCourseId()));
			}
		}
		predicates.add(cb.equal(classEntity.get("deleteFlag"), false));
		cq.where(predicates.toArray(new Predicate[0]));
//        where end

		if (args.getRows() == null || args.getAll != null) {
			return entityManager.createQuery(cq).getResultList();
		}
		return entityManager.createQuery(cq)
//        		=== start paging
				.setMaxResults(CastUtil.castToInteger(args.getRows()))
				.setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
				.getResultList();
	}
	
	
	public List<AemClass> getListClassInProgress(ClassModel args) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AemClass> cq = cb.createQuery(AemClass.class);
		Root<AemClass> classEntity = cq.from(AemClass.class);
		cq.orderBy(cb.desc(classEntity.get("classId")));
		cq.select(classEntity);

//        where start
		List<Predicate> predicates = new ArrayList<>();
		if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
			predicates
					.add(cb.like(cb.lower(classEntity.get("classNm")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(room.get("roomNm"), args.getSearchText()));
		}
		if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
			predicates.add(cb.equal(classEntity.get("status"), args.getSearchStatus()));
		}
		if (args.getRows() == null) {
			if (args.getCourseId() != null && !"".equals(args.getCourseId())) {
				predicates.add(cb.equal(classEntity.get("course"), args.getCourseId()));
			}
		}
		predicates.add(cb.equal(classEntity.get("isFinish"), false));
		predicates.add(cb.lessThanOrEqualTo(classEntity.get("startDate"), new Date() ));
//		predicates.add(cb.greaterThanOrEqualTo(classEntity.get("endDate"), new Date() ));
		
		predicates.add(cb.equal(classEntity.get("deleteFlag"), false));
		cq.where(predicates.toArray(new Predicate[0]));
//        where end

		if (args.getRows() == null || args.getAll != null) {
			return entityManager.createQuery(cq).getResultList();
		}
		return entityManager.createQuery(cq)
//        		=== start paging
				.setMaxResults(CastUtil.castToInteger(args.getRows()))
				.setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
				.getResultList();
	}
	
	public Long countInprogress(ParamBaseModel search) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<AemClass> root = criteriaQuery.from(AemClass.class);
		criteriaQuery.select(cb.count(root));

		List<Predicate> predicates = new ArrayList<>();
		if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
			predicates.add(cb.like(cb.lower(root.get("classNm")), "%" + search.getSearchText().toLowerCase() + "%"));
		}
		if (search.getSearchStatus() != null && !"".equals(search.getSearchStatus())) {
			predicates.add(cb.equal(root.get("status"), search.getSearchStatus()));
		}
		predicates.add(cb.equal(root.get("isFinish"), false));
		predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), new Date() ));
//		predicates.add(cb.greaterThanOrEqualTo(root.get("endDate"), new Date() ));
		
		predicates.add(cb.equal(root.get("deleteFlag"), false));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result;
	}

	public Long count(ParamBaseModel search) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<AemClass> root = criteriaQuery.from(AemClass.class);
		criteriaQuery.select(cb.count(root));

		List<Predicate> predicates = new ArrayList<>();
		if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
			predicates.add(cb.like(cb.lower(root.get("classNm")), "%" + search.getSearchText().toLowerCase() + "%"));
		}
		if (search.getSearchStatus() != null && !"".equals(search.getSearchStatus())) {
			predicates.add(cb.equal(root.get("status"), search.getSearchStatus()));
		}
		predicates.add(cb.equal(root.get("deleteFlag"), false));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result;
	}

	public AemClass getClassEntity(ClassModel classModel) {
		return this.entityManager.find(AemClass.class, classModel.getKey());
	}

	public AemClass saveClass(ClassModel model, AemClass db) {

		AemCourse course = this.entityManager.find(AemCourse.class, model.getCourseId());

		db.setCourse(course);
		db.setClassCode(model.getClassCode());
		db.setClassNm(model.getClassName());
		db.setClassType(model.getClassType());
		db.setStatus(model.getClassStatus());
		db.setIsFinish(model.getIsFinish());
		db.setIsStart(model.getIsStart());
		db.setStartDate(model.getStartDate());
		db.setEndDate(model.getEndDate());
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
	}
	
	public boolean deleteClass(Long id) {
//		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
//		CriteriaDelete<AemClass> cd = cb.createCriteriaDelete(AemClass.class);
//		Root<AemClass> sample = cd.from(AemClass.class);
//
//		cd.where(cb.equal(sample.get("classId"), id));
//		this.entityManager.createQuery(cd).executeUpdate();
		ClassModel classModel = new ClassModel();
		classModel.setKey(id);
		AemClass classEntity = getClassEntity(classModel);
		classEntity.setDeleteFlag(true);
		entityManager.persist(classEntity);
		
		return true;
	}

	public List<AemClass> getListClassByUserUid(String userUid) throws Exception {

		TsstUser tsstUser = userDao.getTsstUserByUserUid(userUid);
		String userType = tsstUser.getUserType();
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

		List<AemClass> listClass = new ArrayList<>();

		String queryString = "";

		if (userType.equals("STU")) {
			queryString = "SELECT DISTINCT "
					+ " class.CLASS_ID,class.CLASS_CODE,class.CLASS_NM,class.CLASS_TYPE,class.COURSE_ID,"
					+ " class.START_DT,class.END_DT,class.`STATUS`,class.INS_UID,class.INS_DT,class.UPD_UID,class.UPD_DT,class.DELETE_FLAG,class.ISFINISH, class.ISSTART"
					+ "  FROM EAM_CLASS AS class" + " LEFT JOIN EAM_CLASS_STUDENT_MAP as classStudent"
					+ " ON class.CLASS_ID = classStudent.CLASS_ID" + " LEFT JOIN EAM_STUDENT_INFO AS student"
					+ " ON classStudent.STUDENT_INFO_ID = student.STUDENT_INFO_ID" + " LEFT JOIN TSST_USER AS USER"
					+ " ON student.STUDENT_INFO_ID = USER.USER_INFO_ID" + " WHERE  USER.USER_UID ="
					+":userUid "
					+ " AND class.STATUS = true"
					+ " AND class.ISFINISH = false"
//					+" AND class.START_DT <= " 
//	    			+":timeStamp"
	    			+ " AND class.END_DT >= "
	    			+":timeStamp"
					;
		} else if (userType.equals("TEA")) {
			queryString = "SELECT DISTINCT "
					+ " class.CLASS_ID,class.CLASS_CODE,class.CLASS_NM,class.CLASS_TYPE,class.COURSE_ID,"
					+ " class.START_DT,class.END_DT,class.`STATUS`,class.INS_UID,class.INS_DT,class.UPD_UID,class.UPD_DT,class.DELETE_FLAG,class.ISFINISH, class.ISSTART"
					+ "  FROM EAM_CLASS AS class" + " LEFT JOIN EAM_CLASS_TEACHER_MAP AS classTeacher"
					+ " ON class.CLASS_ID = classTeacher.CLASS_ID" + " LEFT JOIN EAM_TEACHER_INFO AS teacher"
					+ " ON classTeacher.TEACHER_INFO_ID = teacher.TEACHER_INFO_ID" + " LEFT JOIN TSST_USER AS USER"
					+ " ON teacher.TEACHER_INFO_ID = USER.USER_INFO_ID" + " WHERE  USER.USER_UID =" 
					+":userUid "
					+ "AND class.STATUS = true "
					+ " AND class.ISFINISH = false"
//					+" AND class.START_DT <= " 
//	    			+":timeStamp"
	    			+ " AND class.END_DT >= "
	    			+":timeStamp";
		} else if (userType.equals("EMP")) {
			queryString = "SELECT DISTINCT "
					+ " class.CLASS_ID,class.CLASS_CODE,class.CLASS_NM,class.CLASS_TYPE,class.COURSE_ID,"
					+ " class.START_DT,class.END_DT,class.`STATUS`,class.INS_UID,class.INS_DT,class.UPD_UID,class.UPD_DT,class.DELETE_FLAG,class.ISFINISH, class.ISSTART"
					+ "  FROM EAM_CLASS AS class" + " LEFT JOIN EAM_COURSE course"
					+ " ON class.COURSE_ID = course.COURSE_ID" + " LEFT JOIN EAM_COURSE_SUBJECT_MAP courseSubject"
					+ " ON course.COURSE_ID = courseSubject.COURSE_ID" + " LEFT JOIN EAM_COURSE_SUBJECT subject"
					+ " ON courseSubject.SUBJECT_ID = subject.SUBJECT_ID" + " LEFT JOIN TSST_USER USER"
					+ " ON subject.USER_UID = USER.USER_UID" + " WHERE  USER.USER_UID =" 
					+":userUid "
					+ "AND class.STATUS = true"
					+ " AND class.ISFINISH = false"
//					+" AND class.START_DT <= " 
//	    			+":timeStamp"
	    			+ " AND class.END_DT >= "
	    			+":timeStamp";
		}

		Query query = this.entityManager.createNativeQuery(queryString,AemClass.class).setParameter("userUid", userUid).setParameter("timeStamp", timeStamp);
		List<AemClass> resultList = query.getResultList();
//		if (resultList.size() > 0) {
//			for (Object object : resultList) {
//				Object[] objects = (Object[]) object;
//				Long classId = ((BigInteger) objects[0]).longValue();
//				String classCode = objects[1].toString();
//				String classNm = objects[2].toString();
//				String classType = objects[3] != null ? objects[3].toString() : null;
//				Long courseId = ((BigInteger) objects[4]).longValue();
//				Date startDate = (Date) objects[5];
//				Date endDate = (Date) objects[6];
//				Boolean status = (Boolean) objects[7];
//				String ins_uid = objects[8] != null ? objects[8].toString() : null;
//				Date ins_dt = objects[9] != null ? (Date) objects[9] : null;
//				String upd_uid = objects[10] != null ? objects[10].toString() : null;
//				Date upd_dt = objects[11] != null ? (Date) objects[11] : null;
//				Boolean deleteFlag = (Boolean) objects[12];
//
//				AemClass aemClass = new AemClass();
//				aemClass.setClassId(classId);
//				aemClass.setClassCode(classCode);
//				aemClass.setClassNm(classNm);
//				aemClass.setClassType(classType);
//
//				Edu0102RequestModel courseModel = new Edu0102RequestModel();
//				courseModel.setKey(courseId);
//				AemCourse course = courseDao.getCourse(courseModel);
//				aemClass.setCourse(course);
//
//				aemClass.setStartDate(startDate);
//				aemClass.setEndDate(endDate);
//				aemClass.setStatus(status);
//				aemClass.setInsUid(queryString);
//				aemClass.setInsDt(ins_dt);
//				aemClass.setUpdUid(queryString);
//				aemClass.setUpdDt(upd_dt);
//				aemClass.setDeleteFlag(deleteFlag);
//
//				List<AemClassStudentMap> classStudentMaps = classStudentMapDao.getClassStudentsByClassId(classId);
//				aemClass.setListOfClassStudentMap(classStudentMaps);
//
//				listClass.add(aemClass);
//				System.out.println(course);
//			}
//		}

		return resultList;
	}

}
