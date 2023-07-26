package com.a2m.gen.dao;

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
import com.a2m.gen.entities.edu.AemCouSbjChtrLecture;
import com.a2m.gen.entities.edu.AemCscLectureFile;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.community.PostModel;
import com.a2m.gen.models.course.LectureFileModel;
import com.a2m.gen.models.course.LectureModel;
import com.a2m.gen.repository.community.PostDao.PostEnum;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class LectureDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemCouSbjChtrLecture> getLectures(ParamSearchModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCouSbjChtrLecture> cq = cb.createQuery(AemCouSbjChtrLecture.class);
        Root<AemCouSbjChtrLecture> lecture = cq.from(AemCouSbjChtrLecture.class);
        cq.orderBy(cb.desc(lecture.get("lectureId")));
        cq.select(lecture);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
            predicates.add(cb.like(cb.lower( lecture.get("lectureNm")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(lecture.get("lectureNm"), args.getSearchText()));
        }
        if (args.getTagName() != null && !"".equals(args.getTagName())) {
            predicates.add(cb.like(cb.lower( lecture.get("tags")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        
        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
            predicates.add(cb.equal(lecture.get("status"), args.getSearchStatus()));
        }
        
        if(args.getKey() != null && !"".equals(args.getKey())) {        	
        	predicates.add(cb.equal(lecture.get("aemCouSubChapter"), args.key));
        }
        predicates.add(cb.equal(lecture.get("deleteFlag"), false));
        cq.where(predicates.toArray(new Predicate[0]));
        
//        where end
        
        if(args.getAll!=null) {
        	return entityManager.createQuery(cq).getResultList();
        }
        return entityManager.createQuery(cq)
//        		=== start paging
        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();
    }
    
    public List<AemCscLectureFile> getLectureFiles(Long lectureId) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCscLectureFile> cq = cb.createQuery(AemCscLectureFile.class);
        Root<AemCscLectureFile> aemCscLectureFile = cq.from(AemCscLectureFile.class);
        
        //Join<AemCscLectureFile,TccoFile> leftJoin = aemCscLectureFile.join("tccoFile", JoinType.LEFT);
//        leftJoin.on(
//        			cb.and(
//        		            cb.equal(leftJoin.get("atchFleSeq"), cb.parameter(Integer.class, "atchFleSeq")),
//        		            cb.equal(leftJoin.get("atchFleSeq"), leftJoin.get("atchFleSeq"))
//        		        )   
//                );
        
        cq.orderBy(cb.desc(aemCscLectureFile.get("lectureId")));
        cq.select(aemCscLectureFile);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        
        if (lectureId != null) {
            predicates.add(cb.equal(aemCscLectureFile.get("lectureId"), lectureId));
        }
        
        predicates.add(cb.equal(aemCscLectureFile.get("deleteFlag"), false));
        
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
//        		=== start paging
//        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
//                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();
    }
    
    public Long countAllLecture(ParamSearchModel args){ 	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemCouSbjChtrLecture> root = criteriaQuery.from(AemCouSbjChtrLecture.class);
        criteriaQuery.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
            predicates.add(cb.like(cb.lower(root.get("lectureNm")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        if (args.getTagName() != null && !"".equals(args.getTagName())) {
            predicates.add(cb.like(cb.lower( root.get("tags")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        if(args.getKey() != null && !"".equals(args.getKey())) {   
        	predicates.add(cb.equal(root.get("aemCouSubChapter"), args.key));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
    	return result;
    }
    
    public AemCouSbjChtrLecture getLecture(LectureModel lectureModel) {
    	return this.entityManager.find(AemCouSbjChtrLecture.class, lectureModel.getKey());
    }
    
    public AemCouSbjChtrLecture saveLecture(LectureModel model, AemCouSbjChtrLecture db) {
    	db.setLectureId(model.getKey());
    	db.setLectureNm(model.getLectureNm());
    	db.setTotalLesson(null);
    	db.setTypeDocument(model.getTypeDocument());
    	db.setTags(model.getTags());
    	db.setLectureContent(model.getLectureContent());
    	db.setLectureGoal(model.getLectureGoal());
//    	db.setStartDt(model.getStartDt());
//    	db.setEndDt(model.getEndDt());
    	db.setStatus(model.getStatus());
    	db.setOdrNo(model.getOrdNo());
    	db.setLectureType(null);
    	if(model.getLinkVideo() != null) {
    		db.setLinkVideo(model.getLinkVideo());
    	}else {
    		db.setLinkVideo(null);
    	}
    	AemCouSbjChapter chapter = entityManager.find(AemCouSbjChapter.class, model.getChapterId());
    	db.setAemCouSubChapter(chapter);
    	
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }
    
    public AemCscLectureFile insertLectureFile(LectureFileModel model, AemCscLectureFile db, Long lectureId) {
    	db.setFileId(null);
    	db.setLectureId(lectureId);
    	db.setFileId(model.getTccoFileModel().getAtchFleSeq());
    	db.setFileType(model.getFileType());
    	
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }
    
    public AemCscLectureFile dropLectureFile(LectureFileModel model, AemCscLectureFile db, Long lectureId) {
    	AemCscLectureFile file = entityManager.find(AemCscLectureFile.class, model.getKey());
    	
    	entityManager.remove(file);
		return db;
    }

    public AemCouSbjChtrLecture deleteLecture(LectureModel model, AemCouSbjChtrLecture db) {
//    	db.setDeleteFlag(true);
//    	
//    	AemCouSbjChapter chapter = entityManager.find(AemCouSbjChapter.class, model.getChapterId());
//    	
//    	db.setAemCouSubChapter(chapter);
//    	
//		entityManager.persist(db);
//		entityManager.remove(db);
    	
    	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // create delete
        CriteriaDelete<AemCouSbjChtrLecture> delete = cb.createCriteriaDelete(AemCouSbjChtrLecture.class);

        // set the root class
        Root e = delete.from(AemCouSbjChtrLecture.class);

        // set where clause
//        delete.where(cb.lessThanOrEqualTo(root.get("amount"), amount));

        // perform update
        this.entityManager.createQuery(delete).executeUpdate();
		return db;
    }
    
    
    public List<LectureModel> searchByTag(ParamSearchModel searchModel) {
		List<LectureModel> result = new ArrayList<LectureModel>();
		String searchText = searchModel.getSearchText() != null?searchModel.getSearchText() : "";
		String limit = "";
		limit = searchModel.getRows()!=null ?"LIMIT " + searchModel.getRows() + " " : "" ;
		 String ofset = "";
		ofset = searchModel.getRows()!=null ?"OFFSET " + searchModel.getRows() * searchModel.getPage() : "" ;
		String fullTextSearch = "AND "
				+ "("
				+ "MATCH(lecture.LECTURES_NM, lecture.LECTURES_GOAL) AGAINST('"+searchText+"') "
				+ "OR LOWER(lecture.TAGS) LIKE LOWER('%"+searchText+"%') "
				+ ")";
		if(searchText.equals("")) {
			fullTextSearch = " ";
		}
		
		Query q = 
		entityManager.createNativeQuery(
				  "SELECT lecture.LECTURES_ID "
				  + ", lecture.CHAPTER_ID "
				  + ", lecture.LECTURES_NM "
				  + ", lecture.TAGS "
				  + ", lecture.LECTURES_CONTENT "
				  + ", lecture.LECTURES_GOAL "
				  + ", lecture.LECTURES_TYPE "
				  + ", lecture.ORD_NO "
				  + ", lecture.STATUS "
				  + ", lecture.INS_UID "
				  + ", lecture.INS_DT "
				  + ", lecture.UPD_UID "
				  + ", lecture.UPD_DT "
				  + ", lecture.DELETE_FLAG "
				  + ", IF(user.USER_TYPE = 'STU', stuInfo.FULL_NAME, IF(user.USER_TYPE = 'TEA', teaInfo.FULL_NAME, userInfo.FULL_NAME)) "
				  + ", IF(user.USER_TYPE = 'STU', stuInfo.IMG_PATH, IF(user.USER_TYPE = 'TEA', teaInfo.IMG_PATH, userInfo.IMG_PATH)) "

			  + "FROM EAM_COURSE_SUBJECT_CHAPTER_LECTURES lecture "
			  + "LEFT JOIN TSST_USER user ON lecture.INS_UID = user.USER_UID "
			  + "LEFT JOIN TSST_USER_INFO userInfo ON user.USER_INFO_ID = userInfo.USER_INFO_ID "
			  + "LEFT JOIN EAM_STUDENT_INFO stuInfo ON user.USER_INFO_ID = stuInfo.STUDENT_INFO_ID "
			  + "LEFT JOIN EAM_TEACHER_INFO teaInfo ON user.USER_INFO_ID = teaInfo.TEACHER_INFO_ID "
			  + "WHERE 1 = 1 "
			  + fullTextSearch
			  + "ORDER BY lecture.INS_DT DESC "
//			  + "LIMIT " + searchModel.getRows() + " "
//	  		  + "OFFSET " + searchModel.getRows() * searchModel.getPage()
			  +limit
			  +ofset
				
				);
		List<Object[]> lectures = q.getResultList();
	
		for (Object[] objects : lectures) {
			//Object[] objects = (Object[]) object;
			
			BigInteger lecId = (BigInteger) objects[LectureEnum.LECTURES_ID.getValue()];
			BigInteger chapId = (BigInteger) objects[LectureEnum.CHAPTER_ID.getValue()];
			String lectureName = (String) objects[LectureEnum.LECTURES_NM.getValue()];
			String tags = (String) objects[LectureEnum.TAGS.getValue()];
			String lectureContent = (String) objects[LectureEnum.LECTURES_CONTENT.getValue()];
			String lectureGoal = (String) objects[LectureEnum.LECTURES_GOAL.getValue()];
			String lectureType = (String) objects[LectureEnum.LECTURES_TYPE.getValue()];
			Integer oderNo = (Integer) objects[LectureEnum.ORD_NO.getValue()];
			Boolean status = (Boolean) objects[LectureEnum.STATUS.getValue()];
			
			String insUid = (String) objects[LectureEnum.INS_UID.getValue()];
			Date insDate = (Date) objects[LectureEnum.INS_DT.getValue()];
			String updUid = (String) objects[LectureEnum.UPD_UID.getValue()];
			Date updDate = (Date) objects[LectureEnum.UPD_DT.getValue()];
			Boolean deleteFlag = (Boolean) objects[LectureEnum.DELETE_FLAG.getValue()];		
			
			Long lectureId = lecId!=null?lecId.longValue():0;
			Long chapterId = chapId!=null?chapId.longValue():0;
			
			LectureModel returnModel = new LectureModel(chapterId, lectureName, tags, lectureContent, lectureGoal, lectureType, insDate, updDate,oderNo, status);
			returnModel.setKey(lectureId);
			result.add(returnModel);
		}
		
		return result ;
 
    }
    
	public Long countLectureByTag(ParamSearchModel model) {
		List<PostModel> result = new ArrayList<PostModel>();
		String searchText = model.getSearchText() != null?model.getSearchText() : "";
		String fullTextSearch = "AND "
				+ "("
				+ "MATCH(lecture.LECTURES_NM, lecture.LECTURES_GOAL) AGAINST('"+searchText+"') "
				+ "OR LOWER(lecture.TAGS) LIKE LOWER('%"+searchText+"%') "
				+ ")";
		if(searchText.equals("")) {
			fullTextSearch = " ";
		}

		
		Query q = 
		entityManager.createNativeQuery(
				"SELECT COUNT(*) "	

			  + "FROM EAM_COURSE_SUBJECT_CHAPTER_LECTURES lecture "
			  + "LEFT JOIN TSST_USER user ON lecture.INS_UID = user.USER_UID "
			  + "LEFT JOIN TSST_USER_INFO userInfo ON user.USER_INFO_ID = userInfo.USER_INFO_ID "
			  + "LEFT JOIN EAM_STUDENT_INFO stuInfo ON user.USER_INFO_ID = stuInfo.STUDENT_INFO_ID "
			  + "LEFT JOIN EAM_TEACHER_INFO teaInfo ON user.USER_INFO_ID = teaInfo.TEACHER_INFO_ID "
			  + "WHERE 1 = 1 "
			  + fullTextSearch
			  + "ORDER BY lecture.INS_DT DESC "
//			  + "LIMIT " + postModel.getRows() + " "
//	  		  + "OFFSET " + postModel.getRows() * postModel.getPage()				
				);
		Object singleResult = new ArrayList<>();
		
		singleResult = q.getSingleResult();
		return ((BigInteger) singleResult) .longValue();
		
	}
	
	public Map getInfoCourse(Long id) {
		Map result = new HashMap<>();
		
		
		Query q = 
				entityManager.createNativeQuery(
						" SELECT lecture.LECTURES_ID,chapter.CHAPTER_ID,subject.SUBJECT_ID,course.COURSE_ID "
						+" FROM EAM_COURSE_SUBJECT_CHAPTER_LECTURES lecture "
						+" LEFT JOIN EAM_COURSE_SUBJECT_CHAPTER chapter "
						+" ON lecture.CHAPTER_ID = chapter.CHAPTER_ID "
						+"LEFT JOIN EAM_COURSE_SUBJECT subject "
						+" ON chapter.SUBJECT_ID = subject.SUBJECT_ID "
						+" LEFT JOIN EAM_COURSE_SUBJECT_MAP course "
						+" ON subject.SUBJECT_ID= course.SUBJECT_ID "
						+" WHERE lecture.LECTURES_ID ="+id
						);
		Object[] singleResult = (Object[]) q.getSingleResult();
		Long lectureId =  ((BigInteger) singleResult[0]).longValue();
		Long chapterId = ((BigInteger) singleResult[1]).longValue();
		Long subjectId = ((BigInteger) singleResult[2]).longValue();
		Long courseId = ((BigInteger) singleResult[3]).longValue();

		result.put("lectureId", lectureId);
		result.put("chapterId", chapterId);
		result.put("subjectId", subjectId);
		result.put("courseId", courseId);
//		Long lectureId = lecId!=null?lecId.longValue():0;
		
		return result;
	}
	
	public enum infoCourse{
		LECTURES_ID(0),
		CHAPTER_ID(1),
		SUBJECT_ID(2),
		COURSE_ID(3);
		
		private int value;

		infoCourse(int id) {
			this.value = id;
		}

		public int getValue() {
			return value;
		}
	}
    
	public enum LectureEnum {
		LECTURES_ID(0), 
		CHAPTER_ID(1), 
		LECTURES_NM(2),
		TAGS(3), 
		LECTURES_CONTENT(4), 
		LECTURES_GOAL(5),
		LECTURES_TYPE(6), 
		ORD_NO(7), 
		STATUS(8),
		INS_UID(9), 
		INS_DT(10),
		UPD_UID(11), 
		UPD_DT(12), 
		DELETE_FLAG(13),
		FULL_NAME(14),
		IMG_PATH(15),
		;
		

		private int value;

		LectureEnum(int id) {
			this.value = id;
		}

		public int getValue() {
			return value;
		}
	}
}
