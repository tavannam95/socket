package com.a2m.gen.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCsChapterFile;
import com.a2m.gen.entities.edu.AemDoUserMapEntity;
import com.a2m.gen.entities.edu.QuizEntity;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemCouSbjChapter;
import com.a2m.gen.entities.edu.AemCouSbjChtrLecture;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.community.PostModel;
import com.a2m.gen.models.course.ChapterFileModel;
import com.a2m.gen.models.course.LectureModel;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.edu.DocUserMapModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class SbjChapterDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemCouSbjChapter> getChapters(SbjChapterModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCouSbjChapter> cq = cb.createQuery(AemCouSbjChapter.class);
        Root<AemCouSbjChapter> chapter = cq.from(AemCouSbjChapter.class);
        cq.orderBy(cb.asc(chapter.get("chapterId")));
        cq.select(chapter);
        
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
            predicates.add(cb.like(cb.lower( chapter.get("chapterNm")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        
        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
        	predicates.add(cb.equal(chapter.get("status"), args.getSearchStatus()));
        }
        
        if (args.getDocumentStatus() != null && !"".equals(args.getDocumentStatus())) {
        	predicates.add(cb.equal(chapter.get("documentStatus"), args.getDocumentStatus()));
        }
        
        predicates.add(cb.equal(chapter.get("deleteFlag"), false));
        predicates.add(cb.equal(chapter.get("aemCouSubject"), args.key));
        cq.where(predicates.toArray(new Predicate[0]));
        
        if( args.getGetAll() != null && args.getGetAll()  ) {
        	return entityManager.createQuery(cq).getResultList();
        }
        
        return entityManager.createQuery(cq)
//        		=== start paging
        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();
    }
    
    public List<AemCouSbjChapter> getChapterSelect(ParamBaseModel args) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCouSbjChapter> cq = cb.createQuery(AemCouSbjChapter.class);
        Root<AemCouSbjChapter> chapter = cq.from(AemCouSbjChapter.class);
        cq.orderBy(cb.desc(chapter.get("chapterId")));
        cq.select(chapter);
        
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
            predicates.add(cb.like(cb.lower( chapter.get("chapterNm")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        
        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
            predicates.add(cb.equal(chapter.get("status"), args.getSearchStatus()));
        }
        
        predicates.add(cb.equal(chapter.get("deleteFlag"), false));
        cq.where(predicates.toArray(new Predicate[0]));
        
        if( args.getGetAll() != null && args.getGetAll()  ) {
            return entityManager.createQuery(cq).getResultList();
        }
        
        return entityManager.createQuery(cq)
//              === start paging
                .setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//              === end paging
                .getResultList();
    }
    
    public Long countAllChapter(ParamBaseModel args){ 	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemCouSbjChapter> root = criteriaQuery.from(AemCouSbjChapter.class);
        criteriaQuery.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
            predicates.add(cb.like(cb.lower(root.get("chapterNm")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        predicates.add(cb.equal(root.get("aemCouSubject"), args.key));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
    	return result;
    }
    
    public AemCouSbjChapter getChapter(SbjChapterModel sbjChapterModel) {
    	return this.entityManager.find(AemCouSbjChapter.class, sbjChapterModel.getKey());
    }
    
    public AemDoUserMapEntity getViewDoc(DocUserMapModel model) {
    	return this.entityManager.find(AemDoUserMapEntity.class, model.getKey());
    }
    
    public AemCouSbjChapter save(SbjChapterModel model, AemCouSbjChapter db) {
        db.setChapterId(model.getKey());
        db.setDocumentStatus(model.getDocumentStatus());
        db.setChapterNm(model.getChapterNm());
        db.setTotalLesson(model.getTotalLesson());
        db.setOrganizationFormal(model.getOrganizationFormal());
        db.setTags(model.getTags());
    	db.setChapterContent(model.getChapterContent());
    	db.setChapterType(model.getChapterType());
    	db.setOdrNo(model.getOdrNo());
    	db.setStartDt(model.getStartDt());
    	db.setEndDt(model.getEndDt());
    	db.setStatus(model.getStatus());
    	
    	AemCourseSubject aemSubject = entityManager.find(AemCourseSubject.class, model.getSubjectId());
        db.setAemCouSubject(aemSubject);
    	
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }

    public AemDoUserMapEntity saveViewDoc(DocUserMapModel model, AemDoUserMapEntity db) {
        db.setTableId(model.getKey());

        if(model.getQuizId() != null) {
            QuizEntity quizEntity = entityManager.find(QuizEntity.class, model.getQuizId());
            db.setQuizEntity(quizEntity);
        }else {
        	db.setQuizEntity(null);
        }

        if(model.getDocId() != null) {
            AemCouSbjChtrLecture lectureEntity = entityManager.find(AemCouSbjChtrLecture.class, model.getDocId());
            db.setAemCouSbjChtrLecture(lectureEntity);
        }else {
        	db.setAemCouSbjChtrLecture(null);
        }
        
        TsstUser tsstUser = entityManager.find(TsstUser.class, model.getUserUid());
        db.setUserUid(tsstUser);
        
//        db.setQuizId(model.getQuizId());
//    	db.setDocId(model.getDocId());
//    	db.setUserUid(model.getUserUid());
    	db.setIsRead(model.getIsRead());
    	db.setIsComplete(model.getIsComplete());
        db.setDeleteFlag(false);
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }
    
    public AemCsChapterFile insertChapterFile(ChapterFileModel model, AemCsChapterFile db, Long chapterId) {
    	db.setFileId(null);
    	db.setChapterId(chapterId);
    	db.setFileId(model.getTccoFileModel().getAtchFleSeq());
    	db.setFileType(model.getFileType());
    	
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }
    
    public AemCsChapterFile dropChapterFile(ChapterFileModel model, AemCsChapterFile db, Long chapterId) {
    	AemCsChapterFile file = entityManager.find(AemCsChapterFile.class, model.getKey());
    	
    	entityManager.remove(file);
		return db;
    }
    
    public List<AemCsChapterFile> getChapterFiles(Long chapterId) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCsChapterFile> cq = cb.createQuery(AemCsChapterFile.class);
        Root<AemCsChapterFile> root = cq.from(AemCsChapterFile.class);
        
        //Join<AemCscLectureFile,TccoFile> leftJoin = aemCscLectureFile.join("tccoFile", JoinType.LEFT);
//        leftJoin.on(
//        			cb.and(
//        		            cb.equal(leftJoin.get("atchFleSeq"), cb.parameter(Integer.class, "atchFleSeq")),
//        		            cb.equal(leftJoin.get("atchFleSeq"), leftJoin.get("atchFleSeq"))
//        		        )   
//                );
        
        cq.orderBy(cb.desc(root.get("chapterId")));
        cq.select(root);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        
        if (chapterId != null) {
            predicates.add(cb.equal(root.get("chapterId"), chapterId));
        }
        
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
//        		=== start paging
//        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
//                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();
    }

    public AemCouSbjChapter deleteChapter(SbjChapterModel model, AemCouSbjChapter db) {
    	db.setDeleteFlag(true);
    	AemCourseSubject aemSubject = entityManager.find(AemCourseSubject.class, model.getSubjectId());
    	db.setAemCouSubject(aemSubject);
		entityManager.persist(db);
		return db;
    }
    
	public Map getInfoCourse(Long id) {
		Map result = new HashMap<>();
		
		
		Query q = 
				entityManager.createNativeQuery(
						 "SELECT chapter.CHAPTER_ID,subject.SUBJECT_ID,course.COURSE_ID" 
						+" from  EAM_COURSE_SUBJECT_CHAPTER chapter" 
						+" LEFT JOIN EAM_COURSE_SUBJECT subject "
						 +" ON chapter.SUBJECT_ID = subject.SUBJECT_ID "
						 +" LEFT JOIN EAM_COURSE_SUBJECT_MAP course" 
						 +" ON subject.SUBJECT_ID= course.SUBJECT_ID" 
						 +" WHERE chapter.CHAPTER_ID = :id"
						).setParameter("id", id);
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
	
    public List<AemCouSbjChapter> searchByTag(ParamSearchModel searchModel) {
		List<LectureModel> result = new ArrayList<LectureModel>();
		String searchText = searchModel.getSearchText() != null?searchModel.getSearchText() : "";
		String limit = "";
		limit = searchModel.getRows()!=null ?"LIMIT " + searchModel.getRows() + " " : "" ;
		 String ofset = "";
		ofset = searchModel.getRows()!=null ?"OFFSET " + searchModel.getRows() * searchModel.getPage() : "" ;
		String fullTextSearch = "AND "
				+ "("
				+ "MATCH(chapter.CHAPTER_NM) AGAINST('"+searchText+"') "
				+ "OR LOWER(chapter.TAGS) LIKE LOWER('%"+searchText+"%') "
				+ ")";
		if(searchText.equals("")) {
			fullTextSearch = " ";
		}
		
		Query q = 
		entityManager.createNativeQuery(
				"SELECT chapter.CHAPTER_ID,chapter.SUBJECT_ID,chapter.DOCUMENT_STATUS,chapter.CHAPTER_NM,chapter.TOTAL_LESSON,chapter.ORGANIZATION_FORMAL,"
				+" chapter.TAGS,chapter.CHAPTER_CONTENT,chapter.CHAPTER_TYPE,chapter.ORD_NO,chapter.START_DT,chapter.END_DT,chapter.`STATUS`,chapter.INS_UID,chapter.INS_DT,"
				+" chapter.UPD_UID,chapter.UPD_DT,chapter.DELETE_FLAG"
//				+" IF(user.USER_TYPE = 'STU', stuInfo.FULL_NAME, IF(user.USER_TYPE = 'TEA', teaInfo.FULL_NAME, userInfo.FULL_NAME)) , "
//				+" IF(user.USER_TYPE = 'STU', stuInfo.IMG_PATH, IF(user.USER_TYPE = 'TEA', teaInfo.IMG_PATH, userInfo.IMG_PATH)) "
				+" FROM EAM_COURSE_SUBJECT_CHAPTER chapter LEFT JOIN TSST_USER user ON chapter.INS_UID = user.USER_UID "
				+" LEFT JOIN TSST_USER_INFO userInfo ON user.USER_INFO_ID = userInfo.USER_INFO_ID "
				+" LEFT JOIN EAM_STUDENT_INFO stuInfo  ON user.USER_INFO_ID = stuInfo.STUDENT_INFO_ID "
				+" LEFT JOIN EAM_TEACHER_INFO teaInfo ON user.USER_INFO_ID = teaInfo.TEACHER_INFO_ID "
				+" WHERE 1 = 1 "
			  + fullTextSearch
			  + "ORDER BY chapter.INS_DT DESC "
			  +limit
			  +ofset
			  ,AemCouSbjChapter.class
				
				);
		  List<AemCouSbjChapter> resultList = q.getResultList();
//		List<Object[]> lectures = q.getResultList();
	
//		for (Object[] objects : lectures) {
//			//Object[] objects = (Object[]) object;
//			
//			BigInteger lecId = (BigInteger) objects[LectureEnum.LECTURES_ID.getValue()];
//			BigInteger chapId = (BigInteger) objects[LectureEnum.CHAPTER_ID.getValue()];
//			String lectureName = (String) objects[LectureEnum.LECTURES_NM.getValue()];
//			String tags = (String) objects[LectureEnum.TAGS.getValue()];
//			String lectureContent = (String) objects[LectureEnum.LECTURES_CONTENT.getValue()];
//			String lectureGoal = (String) objects[LectureEnum.LECTURES_GOAL.getValue()];
//			String lectureType = (String) objects[LectureEnum.LECTURES_TYPE.getValue()];
//			Integer oderNo = (Integer) objects[LectureEnum.ORD_NO.getValue()];
//			Boolean status = (Boolean) objects[LectureEnum.STATUS.getValue()];
//			
//			String insUid = (String) objects[LectureEnum.INS_UID.getValue()];
//			Date insDate = (Date) objects[LectureEnum.INS_DT.getValue()];
//			String updUid = (String) objects[LectureEnum.UPD_UID.getValue()];
//			Date updDate = (Date) objects[LectureEnum.UPD_DT.getValue()];
//			Boolean deleteFlag = (Boolean) objects[LectureEnum.DELETE_FLAG.getValue()];		
//			
//			Long lectureId = lecId!=null?lecId.longValue():0;
//			Long chapterId = chapId!=null?chapId.longValue():0;
//			
//			LectureModel returnModel = new LectureModel(chapterId, lectureName, tags, lectureContent, lectureGoal, lectureType, insDate, updDate,oderNo, status);
//			returnModel.setKey(lectureId);
//			result.add(returnModel);
//		}
		
//		return result ;
		return resultList ;
 
    }
    
	public Long countChapterByTag(ParamSearchModel model) {
		List<PostModel> result = new ArrayList<PostModel>();
		String searchText = model.getSearchText() != null?model.getSearchText() : "";
		String fullTextSearch = "AND "
				+ "("
				+ "MATCH(chapter.CHAPTER_NM) AGAINST('"+searchText+"') "
				+ "OR LOWER(chapter.TAGS) LIKE LOWER('%"+searchText+"%') "
				+ ")";
		if(searchText.equals("")) {
			fullTextSearch = " ";
		}

		
		Query q = 
		entityManager.createNativeQuery(
				"SELECT COUNT(*) "	
				+"  FROM EAM_COURSE_SUBJECT_CHAPTER chapter LEFT JOIN TSST_USER user ON chapter.INS_UID = user.USER_UID "
				+" LEFT JOIN TSST_USER_INFO userInfo ON user.USER_INFO_ID = userInfo.USER_INFO_ID "
				+" LEFT JOIN EAM_STUDENT_INFO stuInfo  ON user.USER_INFO_ID = stuInfo.STUDENT_INFO_ID" 
				+" LEFT JOIN EAM_TEACHER_INFO teaInfo ON user.USER_INFO_ID = teaInfo.TEACHER_INFO_ID "
				+"  WHERE 1 = 1 "
			  + fullTextSearch
			  + "ORDER BY chapter.INS_DT DESC "
//			  + "LIMIT " + postModel.getRows() + " "
//	  		  + "OFFSET " + postModel.getRows() * postModel.getPage()				
				);
		Object singleResult = new ArrayList<>();
		
		singleResult = q.getSingleResult();
		return ((BigInteger) singleResult) .longValue();
		
	}

}
