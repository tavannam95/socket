package com.a2m.gen.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.edu.AemCouSbjChapter;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.QuizEntity;
import com.a2m.gen.entities.edu.QuizItemAnswerEntity;
import com.a2m.gen.entities.edu.QuizItemAnswerResultEntity;
import com.a2m.gen.entities.edu.QuizItemEntity;
import com.a2m.gen.entities.edu.QuizStudentMapEntity;
import com.a2m.gen.models.course.QuizItemAnswerModel;
import com.a2m.gen.models.course.QuizItemModel;
import com.a2m.gen.models.course.QuizModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Repository
public class QuizDao {
	@Autowired
	private CommonService commonService;

	@Autowired
	private EntityManager entityManager;

	public List<QuizEntity> getQuizByChapter(QuizModel args) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuizEntity> cq = cb.createQuery(QuizEntity.class);
		Root<QuizEntity> quiz = cq.from(QuizEntity.class);

		cq.orderBy(cb.desc(quiz.get("quizId")));
		cq.select(quiz);

//        where start
		List<Predicate> predicates = new ArrayList<>();
		if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
			predicates.add(cb.like(cb.lower(quiz.get("quizNm")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(lecture.get("lectureNm"), args.getSearchText()));
		}

		if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
			predicates.add(cb.equal(quiz.get("status"), args.getSearchStatus()));
		}

		predicates.add(cb.equal(quiz.get("deleteFlag"), false));
		predicates.add(cb.equal(quiz.get("chapter"), args.getChapterId()));
		cq.where(predicates.toArray(new Predicate[0]));
//        where end
		
		if( args.getGetAll() != null && args.getGetAll()  ) {
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

	public Long countQuizByChapter(QuizModel args) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<QuizEntity> quiz = criteriaQuery.from(QuizEntity.class);
		criteriaQuery.select(cb.count(quiz));

//      where start
		List<Predicate> predicates = new ArrayList<>();
		if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
			predicates.add(cb.like(cb.lower(quiz.get("quizNm")), "%" + args.getSearchText().toLowerCase() + "%"));
//          predicates.add(cb.equal(lecture.get("lectureNm"), args.getSearchText()));
		}

		if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
			predicates.add(cb.equal(quiz.get("status"), args.getSearchStatus()));
		}

		predicates.add(cb.equal(quiz.get("deleteFlag"), false));
		predicates.add(cb.equal(quiz.get("chapter"), args.getChapterId()));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result;
	}

	public QuizEntity saveQuiz(QuizModel model, QuizEntity entity) {
		entity.setQuizId(model.getKey());

		AemCouSbjChapter chapter = entityManager.find(AemCouSbjChapter.class, model.getChapterId());
		entity.setChapter(chapter);
		AemCourseSubject subject = entityManager.find(AemCourseSubject.class, model.getSubjectId());
		entity.setSubject(subject);

		entity.setQuizNm(model.getQuizNm());
		entity.setQuizContent(model.getQuizContent());
		entity.setQuizType(model.getQuizType());
		entity.setOrdNo(model.getOrdNo());
		entity.setQuizTime(model.getQuizTime());
		entity.setStatus(model.getStatus());

		DatabaseUtil.setCommonFields(entity, model); // change loginUserId when use
		if (model.getKey() != null) {
			entityManager.merge(entity);
		} else {
			entityManager.persist(entity);
		}

		return entity;
	}

	public QuizItemEntity saveQuizItem(QuizItemModel model, QuizItemEntity entity) {
		entity.setQuizItemId(model.getKey());

		QuizEntity quiz = entityManager.find(QuizEntity.class, model.getQuizId());
		entity.setQuiz(quiz);

		entity.setQuizItemContent(model.getQuizItemContent());
		entity.setAnswerA(model.getAnswerA());
		entity.setAnswerB(model.getAnswerB());
		entity.setAnswerC(model.getAnswerC());
		entity.setAnswerD(model.getAnswerD());
		entity.setAnswerCorrect(model.getAnswerCorrectString());

		entity.setQuizItemType(model.getQuizItemType());
		entity.setStats(model.getStatus());

		DatabaseUtil.setCommonFields(entity, model); // change loginUserId when use
		if (model.getKey() != null) {
			entityManager.merge(entity);
		} else {
			entityManager.persist(entity);
		}

		return entity;
	}

	public QuizItemEntity deleteQuizItem(QuizItemModel model) {
		QuizItemEntity entity = entityManager.find(QuizItemEntity.class, model.getKey());		

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<QuizItemEntity> criteriaDelete = criteriaBuilder
				.createCriteriaDelete(QuizItemEntity.class);
		Root<QuizItemEntity> root = criteriaDelete.from(QuizItemEntity.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get("quizItemId"), model.getKey()));
		int rowsDeleted = entityManager.createQuery(criteriaDelete).executeUpdate();
		
		return entity;
	}

	public QuizEntity deleteQuiz(QuizModel model, QuizEntity entity) {

		DatabaseUtil.setCommonFields(entity, model);
		entity.setQuizId(model.getKey());

		AemCouSbjChapter chapter = entityManager.find(AemCouSbjChapter.class, model.getChapterId());
		entity.setChapter(chapter);
		AemCourseSubject subject = entityManager.find(AemCourseSubject.class, model.getSubjectId());
		entity.setSubject(subject);

		entity.setQuizNm(model.getQuizNm());
		entity.setQuizContent(model.getQuizContent());
		entity.setQuizType(model.getQuizType());
		entity.setOrdNo(model.getOrdNo());
		entity.setStatus(model.getStatus());

		entity.setDeleteFlag(true);
		entityManager.merge(entity);
		return entity;
	}

	public QuizEntity getQuizById(QuizModel model) {
		return this.entityManager.find(QuizEntity.class, model.getKey());
	}

	public QuizEntity getQuizById(Long key) {
		return this.entityManager.find(QuizEntity.class, key);
	}

	public QuizItemEntity getQuizItemById(Long key) {
		return this.entityManager.find(QuizItemEntity.class, key);
	}

	public AemStudentEntity getEamStudentEntity(Long key) {
		return this.entityManager.find(AemStudentEntity.class, key);
	}

	public void deleteQuizItemByChapterId(QuizModel quizModel) {
		List<QuizItemModel> listQuizitem = quizModel.getListQuizItem();
		for (int i = 0; i < listQuizitem.size(); i++) {
			if (listQuizitem.get(i).getKey() != null) {
				QuizItemEntity quizItemEntity = entityManager.find(QuizItemEntity.class, listQuizitem.get(i).getKey());
				entityManager.remove(quizItemEntity);
			}

		}
	}

	public QuizStudentMapEntity submit(QuizStudentMapEntity quizStudentMapEntity) {
		return this.entityManager.merge(quizStudentMapEntity);
	}

	public void deleteQuizItemAnswer(Long quizItemId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<QuizItemAnswerEntity> criteriaDelete = criteriaBuilder
				.createCriteriaDelete(QuizItemAnswerEntity.class);
		Root<QuizItemAnswerEntity> root = criteriaDelete.from(QuizItemAnswerEntity.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get("quizItemEntity"), quizItemId));
		int rowsDeleted = entityManager.createQuery(criteriaDelete).executeUpdate();
		// System.out.println("entities deleted: " + rowsDeleted);
	}

	public void insertQuizItemAnswer(QuizItemAnswerModel answerModel, QuizItemAnswerEntity entity) {
		entity.setAnswerContent(answerModel.getAnswerContent());
		entity.setIsAnswerCorrect(answerModel.getIsAnswerCorrect());

		this.entityManager.merge(entity);

	}

	public void test() {
		QuizItemAnswerResultEntity entity = this.entityManager.find(QuizItemAnswerResultEntity.class,
				CastUtil.castToLong(1));
	}

	public QuizItemAnswerEntity getAnswerById(Long key) {
		return this.entityManager.find(QuizItemAnswerEntity.class, key);
	}

	public Object getListHisQuizStuSubtmited(HashMap<String, Object> parameter) {
		List result = new ArrayList<>();
		
		Query query  = this.entityManager.createNativeQuery(
		        "SELECT " 
		        + "map.INS_DT "
		        + ",map.TOTAL_CORRECT_ANSWER "
		        + ",map.TOTAL_WRONG_ANSWER "
		        + ",map.TOTAL_NOT_CHOOSE "
		        + ",map.QUIZ_STUDENT_ID "
		        
		        
		        + "FROM EAM_QUIZ_STUDENT_MAP map "		
				+ "WHERE map.QUIZ_ID = :quizId "
				+ " AND map.STUDENT_INFO_ID = :stuId "				
				+ "ORDER BY map.INS_DT DESC " 
			  + "LIMIT " + parameter.get("rows") + " "
	  		  + "OFFSET " + (Integer.valueOf((String) parameter.get("rows")) * Integer.valueOf((String) parameter.get("page")))
		     
				).setParameter("quizId", parameter.get("quizId"))
				.setParameter("stuId", parameter.get("studentInfoId"));
		
		List list = new ArrayList<>();
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		for (Object object : list) {
			Object[] objects = (Object[]) object;
			Date insDate = (Date) objects[0];
			BigInteger totalCorrect = (BigInteger) objects[1];
			BigInteger totalWrong = (BigInteger) objects[2];
			BigInteger totalNotChoose = (BigInteger) objects[3];
			BigInteger quizStudentId = (BigInteger) objects[4];
			
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("insDate", insDate);
			map.put("totalCorrect", totalCorrect.longValue());
			map.put("totalWrong", totalWrong.longValue());		
			map.put("totalNotChoose", totalNotChoose.longValue());	
			map.put("quizStudentId", quizStudentId.longValue());	
			result.add(map);
		}
		
		
		return result;
	}

	public Long countHisQuizStuSubtmited(HashMap<String, Object> parameter) {
		String searchByClass = "";
		
		if( CastUtil.castToString(parameter.get("classIds")).length() >0 ) {
			searchByClass = "AND CLASS_ID IN (" +CastUtil.castToString(parameter.get("classIds"))+ ") ";
		}
		
		Query query  = this.entityManager.createNativeQuery(
		        "SELECT COUNT(*) "		//0	
		        + "FROM EAM_QUIZ_STUDENT_MAP map "
				+ "WHERE map.QUIZ_ID = :quizId "
				+ " AND map.STUDENT_INFO_ID = :stuId "				
				+ "ORDER BY map.INS_DT DESC " 
		     
				).setParameter("quizId", parameter.get("quizId"))
				.setParameter("stuId", parameter.get("studentInfoId"));
		
		Object singleResult = new ArrayList<>();
		try {
			singleResult = query.getSingleResult();
			return ((BigInteger) singleResult) .longValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return Long.valueOf(0);
	}
	
	public Object getListStuSubtmited(HashMap<String, Object> parameter) {
		List result = new ArrayList<>();
		String searchByClass = "";
		
		if( CastUtil.castToString(parameter.get("classIds")).length() >0 ) {
			searchByClass = "AND CLASS_ID IN (" +CastUtil.castToString(parameter.get("classIds"))+ ") ";
		}
		
		Query query  = this.entityManager.createNativeQuery(
		        "SELECT stu.STUDENT_INFO_ID " //0
		        + ",stu.FULL_NAME " //1
		        + ",map.QUIZ_ID " //2
		        + ",map.INS_DT " //3
		        + ",temp.CLASS_IDS " //4
		        + ",map.TOTAL_CORRECT_ANSWER " //5
		        + ",map.TOTAL_WRONG_ANSWER " //6
		        + ",map.TOTAL_NOT_CHOOSE " //7
		        
		        
		        + "FROM EAM_QUIZ_STUDENT_MAP map "			
		        
				+ "RIGHT JOIN "
					+ "(SELECT st.STUDENT_INFO_ID,  MAX(st.INS_DT) AS INS_DT, GROUP_CONCAT(distinct cl.CLASS_ID) CLASS_IDS "
					+ "FROM EAM_QUIZ_STUDENT_MAP st "
					
					+ "LEFT JOIN EAM_CLASS_STUDENT_MAP cl USING(STUDENT_INFO_ID) "
					+ "WHERE :quizId = st.QUIZ_ID "
					+ searchByClass
					
					+ "GROUP BY st.STUDENT_INFO_ID) as temp "				
					+ "ON (temp.STUDENT_INFO_ID = map.STUDENT_INFO_ID AND temp.INS_DT = map.INS_DT) "
					
				+ "LEFT JOIN "
					+ "EAM_STUDENT_INFO stu "
					+ "ON temp.STUDENT_INFO_ID = stu.STUDENT_INFO_ID "
				
				+ "WHERE UPPER(stu.FULL_NAME) like CONCAT('%',UPPER(:fullName),'%') "				
				+ "ORDER BY map.INS_DT DESC " 
			  + "LIMIT " + parameter.get("rows") + " "
	  		  + "OFFSET " + (Integer.valueOf((String) parameter.get("rows")) * Integer.valueOf((String) parameter.get("page")))
		     
				).setParameter("quizId", parameter.get("quizId"))
				.setParameter("fullName", parameter.get("fullName"));
		
		List list = new ArrayList<>();
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		for (Object object : list) {
			Object[] objects = (Object[]) object;
			BigInteger studentId = (BigInteger) objects[0];
			String fullName = (String) objects[1];
			BigInteger quizId = (BigInteger) objects[2];
			Date insDate = (Date) objects[3];
			BigInteger totalCorrect = (BigInteger) objects[5];
			BigInteger totalWrong = (BigInteger) objects[6];
			BigInteger totalNotChoose = (BigInteger) objects[7];
			
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("studentId", studentId);
			map.put("fullName", fullName);
			map.put("quizId", quizId);
			map.put("insDate", insDate);
			map.put("totalCorrect", totalCorrect.longValue());
			map.put("totalWrong", totalWrong.longValue());		
			map.put("totalNotChoose", totalNotChoose.longValue());		
			result.add(map);
		}
		
		
		return result;
	}
	
	public Long countStuSubtmited(HashMap<String, Object> parameter) {
		String searchByClass = "";
		
		if( CastUtil.castToString(parameter.get("classIds")).length() >0 ) {
			searchByClass = "AND CLASS_ID IN (" +CastUtil.castToString(parameter.get("classIds"))+ ") ";
		}
		
		Query query  = this.entityManager.createNativeQuery(
		        "SELECT COUNT(*) "		//0	
		        + "FROM EAM_QUIZ_STUDENT_MAP map "		
		        
				+ "RIGHT JOIN "
					+ "(SELECT st.STUDENT_INFO_ID,  MAX(st.INS_DT) AS INS_DT, GROUP_CONCAT(distinct cl.CLASS_ID) CLASS_IDS "
					+ "FROM EAM_QUIZ_STUDENT_MAP st "
					
					+ "LEFT JOIN EAM_CLASS_STUDENT_MAP cl USING(STUDENT_INFO_ID) "
					+ "WHERE :quizId = st.QUIZ_ID "
					+ searchByClass
					
					+ "GROUP BY st.STUDENT_INFO_ID) as temp "				
					+ "ON (temp.STUDENT_INFO_ID = map.STUDENT_INFO_ID AND temp.INS_DT = map.INS_DT) "
					
				+ "LEFT JOIN "
					+ "EAM_STUDENT_INFO stu "
					+ "ON temp.STUDENT_INFO_ID = stu.STUDENT_INFO_ID "
				
				+ "WHERE UPPER(stu.FULL_NAME) like CONCAT('%',UPPER(:fullName),'%') "				
				+ "ORDER BY map.INS_DT DESC" 
		     
				).setParameter("quizId", parameter.get("quizId"))
				.setParameter("fullName", parameter.get("fullName"));
		
		Object singleResult = new ArrayList<>();
		try {
			singleResult = query.getSingleResult();
			return ((BigInteger) singleResult) .longValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return Long.valueOf(0);
	}
	
	public HashMap<String, Object> getStatisResult(HashMap<String, Object> parameter) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String searchByClass = "";
		
		if( CastUtil.castToString(parameter.get("classIds")).length() >0 ) {
			searchByClass = "AND CLASS_ID IN (" +CastUtil.castToString(parameter.get("classIds"))+ ") ";
		}
		
		Query query  = this.entityManager.createNativeQuery(
		        "SELECT "
		        + "SUM("
		        	+ " if(map.TOTAL_CORRECT_ANSWER/"
		        				+ "(map.TOTAL_CORRECT_ANSWER+map.TOTAL_WRONG_ANSWER+map.TOTAL_NOT_CHOOSE) "
		        			+ "BETWEEN 0.7 AND 1, 1, 0) "
		        + ") as professional, "		//0
		        			
		        + "SUM("
	        	+ " if(map.TOTAL_CORRECT_ANSWER/"
	        				+ "(map.TOTAL_CORRECT_ANSWER+map.TOTAL_WRONG_ANSWER+map.TOTAL_NOT_CHOOSE) "
	        			+ "BETWEEN 0.5 AND 0.669, 1, 0) "
	        	+ ") as average, "		//1
	        			
	        	+ "SUM("
	        	+ " if(map.TOTAL_CORRECT_ANSWER/"
	        				+ "(map.TOTAL_CORRECT_ANSWER+map.TOTAL_WRONG_ANSWER+map.TOTAL_NOT_CHOOSE) "
	        			+ "BETWEEN 0 AND 0.499, 1, 0) "
	        	+ ") as noob "		//2	
	        			
	        
		        + "FROM EAM_QUIZ_STUDENT_MAP map "		
		        
				+ "RIGHT JOIN "
					+ "(SELECT st.STUDENT_INFO_ID,  MAX(st.INS_DT) AS INS_DT, GROUP_CONCAT(distinct cl.CLASS_ID) CLASS_IDS "
					+ "FROM EAM_QUIZ_STUDENT_MAP st "
					
					+ "LEFT JOIN EAM_CLASS_STUDENT_MAP cl USING(STUDENT_INFO_ID) "
					+ "WHERE :quizId = st.QUIZ_ID "
					+ searchByClass
					
					+ "GROUP BY st.STUDENT_INFO_ID) as temp "				
					+ "ON (temp.STUDENT_INFO_ID = map.STUDENT_INFO_ID AND temp.INS_DT = map.INS_DT) "
					
				+ "LEFT JOIN "
					+ "EAM_STUDENT_INFO stu "
					+ "ON temp.STUDENT_INFO_ID = stu.STUDENT_INFO_ID "
				
				+ "WHERE UPPER(stu.FULL_NAME) like CONCAT('%',UPPER(:fullName),'%') "				
				+ "ORDER BY map.INS_DT DESC" 
		     
				).setParameter("quizId", parameter.get("quizId"))
				.setParameter("fullName", parameter.get("fullName"));
		
		List list = new ArrayList<>();
		try {
			list = query.getResultList();
			for (Object object : list) {
				Object[] objects = (Object[]) object;
				BigDecimal professional = (BigDecimal) objects[0];
				BigDecimal average = (BigDecimal) objects[1];
				BigDecimal noob = (BigDecimal) objects[2];
				
				if(professional!=null && average!=null && noob!=null) {
					result.put("professional", professional.longValue());
					result.put("average", average.longValue());
					result.put("noob", noob);
				}else {
					result.put("nonQuiz", true);
				}
				
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	//SELECT r.*, coalesce(sum(r.RESULT = 'correct'), 0) as correct, coalesce(sum(r.RESULT != ''), 0) as total  FROM EAM_QUIZ_ITEM_RESULT r GROUP BY r.QUIZ_ITEM_ID
	
	public Object getListQuestionStatistical(HashMap<String, Object> parameter) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		Query query  = this.entityManager.createNativeQuery(
		        "SELECT "
		        + "coalesce(sum(r.RESULT = 'correct'), 0) as correct " //0
		        + ",coalesce(sum(r.RESULT != ''), 0) as total " //1
		        + ",e.QUIZ_ITEM_CONTENT as content " //2
		        
		        + "FROM EAM_QUIZ_ITEM_RESULT r "
		        + "LEFT JOIN EAM_QUIZ_ITEM e ON r.QUIZ_ITEM_ID = e.QUIZ_ITEM_ID "
				
				+ "WHERE e.QUIZ_ID = :quizId "
				
				+ "GROUP BY r.QUIZ_ITEM_ID "
				+ "ORDER BY r.QUIZ_ITEM_ID "
	
//paging				+ "LIMIT " + parameter.get("rows") + " "
//		  		+ "OFFSET " + (Integer.valueOf((String) parameter.get("rows")) * Integer.valueOf((String) parameter.get("page")))			
		     
				).setParameter("quizId", parameter.get("quizId"));
		
		List list = new ArrayList<>();
		try {
			list = query.getResultList();
			for (Object object : list) {
				Object[] objects = (Object[]) object;
				BigDecimal correct = (BigDecimal) objects[0];
				BigDecimal total = (BigDecimal) objects[1];
				String content = (String) objects[2];
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("correct", correct.longValue());
				map.put("total", total.longValue());
				map.put("content", content);
				
				result.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Long countQuestionStatistical(HashMap<String, Object> parameter) {		
		Query query  = this.entityManager.createNativeQuery(
		        "SELECT "
		        + "COUNT(*) as count " //0
		        
		        + "FROM EAM_QUIZ_ITEM r "
				
				+ "WHERE r.QUIZ_ID = :quizId "		
		     
				).setParameter("quizId", parameter.get("quizId"));
		
		Object singleResult = new ArrayList<>();
		try {
			singleResult = query.getSingleResult();
			return ((BigInteger) singleResult) .longValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return Long.valueOf(0);
	}
	
	public Object getScaleCorrectByQuizItem(Long id) {
		Query query  = this.entityManager.createNativeQuery(
		        "SELECT "
		        + "coalesce(sum(r.RESULT = 'correct'), 0) as correct "
		        + ",COUNT(*) AS total "
		        
		        + "FROM EAM_QUIZ_ITEM_RESULT r "
				
				+ "WHERE r.QUIZ_ITEM_ID = :quizItemId "				
		     
				).setParameter("quizItemId", id);
		
		Object obj = new Object();
		try {
			obj = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Object[] aObj = (Object[]) obj;
		Map result = new HashMap<>();
		result.put("correct", aObj[0]);
		result.put("total", aObj[1]);
		
		return result;
	}
}


