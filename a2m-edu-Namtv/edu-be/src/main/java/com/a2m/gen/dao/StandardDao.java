package com.a2m.gen.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.edu.AemCouSbjChapter;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectStandard;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardHistory;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardNote;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardNoteHistory;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.course.SubjectStandardModel;
import com.a2m.gen.models.course.SubjectStandardHistoryModel;
import com.a2m.gen.models.course.SubjectStandardNoteModel;
import com.a2m.gen.models.course.SubjectStandardResultHistoryModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Repository
public class StandardDao {
    @Autowired
    private CommonService commonService;

    @Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;

    public AemCourseSubjectStandard getStandard(SubjectStandardModel model) {
        return this.entityManager.find(AemCourseSubjectStandard.class, model.getKey());
    }
    
    public AemCourseSubjectStandard getStandard(Long key) {
        return this.entityManager.find(AemCourseSubjectStandard.class, key);
    }
    
    public AemCourseSubjectStandardHistory getStandardHistory(Long key) {
        return this.entityManager.find(AemCourseSubjectStandardHistory.class, key);
    }
    
    public AemCourseSubjectStandardHistory getStandardHistory(SubjectStandardHistoryModel model) {
        return this.entityManager.find(AemCourseSubjectStandardHistory.class, model.getKey());
    }

    public AemCourseSubjectStandardNote getStandardNote(SubjectStandardNoteModel model) {
        return this.entityManager.find(AemCourseSubjectStandardNote.class, model.getKey());
    }
    
    public AemCourseSubjectStandardNoteHistory getStandardNoteHistory(SubjectStandardNoteModel model) {
        return this.entityManager.find(AemCourseSubjectStandardNoteHistory.class, model.getKey());
    }

    public AemCourseSubject getSubject(Long id) {
        return this.entityManager.find(AemCourseSubject.class, id);
    }
    
    public List<AemCourseSubjectStandardHistory> getListStandardHistory(SubjectStandardHistoryModel model) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourseSubjectStandardHistory> cq = cb.createQuery(AemCourseSubjectStandardHistory.class);
        Root<AemCourseSubjectStandardHistory> root = cq.from(AemCourseSubjectStandardHistory.class);  
         
        cq.select(root);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        
        if (model.getSubjectId() != null && !"".equals( CastUtil.castToString(model.getSubjectId()) )) {
        	predicates.add(cb.equal(root.get("subjectId"), model.getSubjectId()));
		}
        
        if (model.getIsApproval() != null && !"".equals( CastUtil.castToString(model.getIsApproval()) )) {
        	predicates.add(cb.equal(root.get("isApproval"), model.getIsApproval()));  
		}
      
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        
        if (model.getStandType() != null && !"".equals( CastUtil.castToString(model.getStandType()) )) {
        	predicates.add(cb.equal(root.get("standType"), model.getStandType()));  
		}
        
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        return entityManager.createQuery(cq)
                .getResultList();
    }

    public List<AemCourseSubjectStandardNoteHistory> getListStandardNoteHistory(SubjectStandardResultHistoryModel model) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourseSubjectStandardNoteHistory> cq = cb.createQuery(AemCourseSubjectStandardNoteHistory.class);
        Root<AemCourseSubjectStandardNoteHistory> root = cq.from(AemCourseSubjectStandardNoteHistory.class);  
         
        cq.select(root);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        
        if (model.getChapterId() != null && !"".equals( CastUtil.castToString(model.getChapterId()) )) {
            predicates.add(cb.equal(root.get("chapterId"), model.getChapterId()));
        }

        if (model.getStandHistoryId() != null && !"".equals( CastUtil.castToString(model.getStandHistoryId()) )) {
            predicates.add(cb.equal(root.get("standHistoryId"), model.getStandHistoryId()));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        
        if (model.getStandType() != null && !"".equals( CastUtil.castToString(model.getStandType()) )) {
            predicates.add(cb.equal(root.get("standType"), model.getStandType()));  
        }
        
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        return entityManager.createQuery(cq)
                .getResultList();
    }

    public List<AemCourseSubjectStandardNoteHistory> getListStandardNoteHistoryByStandId(SubjectStandardResultHistoryModel model) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourseSubjectStandardNoteHistory> cq = cb.createQuery(AemCourseSubjectStandardNoteHistory.class);
        Root<AemCourseSubjectStandardNoteHistory> root = cq.from(AemCourseSubjectStandardNoteHistory.class);  
         
        cq.select(root);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (model.getStandHistoryId() != null && !"".equals( CastUtil.castToString(model.getStandHistoryId()) )) {
            predicates.add(cb.equal(root.get("standHistoryId"), model.getStandHistoryId()));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        
        if (model.getStandType() != null && !"".equals( CastUtil.castToString(model.getStandType()) )) {
            predicates.add(cb.equal(root.get("standType"), model.getStandType()));  
        }
        
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        return entityManager.createQuery(cq)
                .getResultList();
    }

    public AemCourseSubjectStandard saveStandard(SubjectStandardModel model, AemCourseSubjectStandard db) {
        AemCourseSubject subject = entityManager.find(AemCourseSubject.class, model.getSubjectId());
        db.setDocumentStatus(model.getDocumentStatus());
        db.setIsApproval(model.getIsApproval());
        db.setSubjectStand(subject);
        db.setStandCode(model.getStandCode());
        db.setStandContent(model.getStandContent());
        db.setStandType(model.getStandType());
        db.setStatus(model.getStatus());

        DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
        if (model.getKey() != null) {
            entityManager.merge(db);
        } else {
            entityManager.persist(db);
        }
        return db;
    }
    
    public AemCourseSubjectStandardHistory saveStandardHistory(SubjectStandardHistoryModel model, AemCourseSubjectStandardHistory db) {
        db.setDocumentStatus(model.getDocumentStatus());
        db.setIsApproval(model.getIsApproval());
        db.setStandId(model.getStandId());
        db.setSubjectId(model.getSubjectId());        
        db.setStandCode(model.getStandCode());
        db.setStandContent(model.getStandContent());
        db.setStandType(model.getStandType());
        db.setStatus(model.getStatus());

        DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
        if (model.getKey() != null) {
            entityManager.merge(db);
        } else {
            entityManager.persist(db);
        }
        return db;
    }
    
    public int updateStandardHistoryCustom(SubjectStandardHistoryModel model) {
		Query q = 
				entityManager.createNativeQuery(
						
					   "UPDATE " + "EAM_COURSE_SUBJECT_STANDARD_HISTORY" + " u "
					  + "SET "
					  + "u.DOCUMENT_STATUS = :DOCUMENT_STATUS "
					  + ", u.IS_APPROVAL = :IS_APPROVAL "
					  + ", u.STAND_ID = :STAND_ID "
					  + ", u.SUBJECT_ID = :SUBJECT_ID "
					  + ", u.STAND_CODE = :STAND_CODE "
					  + ", u.STAND_CONTENT = :STAND_CONTENT "
					  + ", u.STAND_TYPE = :STAND_TYPE "
					  + ", u.STATUS = :STATUS "
					  + ", u.INS_UID = :INS_UID "
					  + ", u.INS_DT = :INS_DT "
					  + ", u.UPD_UID = :UPD_UID "
					  + ", u.UPD_DT = :UPD_DT "
					  + ", u.DELETE_FLAG = :DELETE_FLAG "
					  + "WHERE u.STAND_ID = :STAND_ID "
					  + "AND u.IS_APPROVAL = false "
						).setParameter("DOCUMENT_STATUS", model.getDocumentStatus())
		.setParameter("IS_APPROVAL", model.getIsApproval())
		.setParameter("STAND_ID", model.getStandId())
		.setParameter("SUBJECT_ID", model.getSubjectId())
		.setParameter("STAND_CODE", model.getStandCode())
		.setParameter("STAND_CONTENT", model.getStandContent())
		.setParameter("STAND_TYPE", model.getStandType())
		.setParameter("STATUS", model.getStatus())
		.setParameter("INS_UID", model.getInsUid())
		.setParameter("INS_DT", model.getInsDate())
		.setParameter("UPD_UID", model.getUpdUid())
		.setParameter("UPD_DT", model.getUpdDate())
		.setParameter("DELETE_FLAG", model.getDeleteFlag());
		
		return q.executeUpdate();
    }

    public AemCourseSubjectStandardNote saveStandardNote(SubjectStandardNoteModel standNote,
        AemCourseSubjectStandardNote db) {
        AemCourseSubjectStandard stand = entityManager.find(AemCourseSubjectStandard.class, standNote.getStandId());
        db.setStandNote(stand);
        AemCouSbjChapter chapter = entityManager.find(AemCouSbjChapter.class, standNote.getChapterId());
        db.setChapterStand(chapter);
        db.setStandType(standNote.getStandType());
        db.setStandResult(standNote.getStandResult());
        db.setStatus(standNote.getStatus());

        DatabaseUtil.setCommonFields(db, standNote); // change loginUserId when use
//        if (standNote.getKey() != null) {
//            entityManager.merge(db);
//        } else {
            entityManager.persist(db);
//        }
        return db;
    }
    
    public AemCourseSubjectStandardNoteHistory saveStandardNoteHistory(SubjectStandardNoteModel standNote,
        	AemCourseSubjectStandardNoteHistory db) {
            db.setStandHistoryId(standNote.getStandHistoryId());
            db.setChapterId(standNote.getChapterId());
            db.setStandType(standNote.getStandType());
            db.setStandResult(standNote.getStandResult());
            db.setStatus(standNote.getStatus());

            DatabaseUtil.setCommonFields(db, standNote); // change loginUserId when use
//            if (standNote.getKey() != null) {
//                entityManager.merge(db);
//            } else {
                entityManager.persist(db);
//            }
            return db;
    }

//    public AemCourseSubjectStandard deleteStand(Long id) {
//        AemCourseSubjectStandard db = this.entityManager.find(AemCourseSubjectStandard.class, id);
//        entityManager.remove(db);
//        return db;
//    }
    
    public AemCourseSubjectStandardHistory deleteStandHistory(Long id) {
    	AemCourseSubjectStandardHistory db = this.entityManager.find(AemCourseSubjectStandardHistory.class, id);
        entityManager.remove(db);
        return db;
    }
    
    public int deleteStandHisWithStandId(Long standId) {
    	Query q = this.entityManager.createNativeQuery(
    			"DELETE FROM EAM_COURSE_SUBJECT_STANDARD_HISTORY "
    			+ "WHERE STAND_ID = :STAND_ID "
    			);
    	q.setParameter("STAND_ID", standId);
        return q.executeUpdate();
    }
    
    public int deleteStandHisWithSubjectIdAndType(Long subjectId, String standType) {
    	Query q = this.entityManager.createNativeQuery(
    			"DELETE FROM EAM_COURSE_SUBJECT_STANDARD_HISTORY "
    			+ "WHERE SUBJECT_ID = :SUBJECT_ID AND STAND_TYPE = :STAND_TYPE "
    			);
    	q.setParameter("SUBJECT_ID", subjectId).setParameter("STAND_TYPE", standType);
        return q.executeUpdate();
    }

    
//    public AemCourseSubjectStandardNote deleteNote(Long id) {
//        AemCourseSubjectStandardNote db = this.entityManager.find(AemCourseSubjectStandardNote.class, id);
//        db.setDeleteFlag(true);
//        entityManager.persist(db);
//        return db;
//    }
    
    public int deleteNote(Long standId) {
    	Query q = this.entityManager.createNativeQuery(
    			"DELETE FROM EAM_COURSE_SUBJECT_STANDARD_RESULT "
    			+ "WHERE STAND_ID = :STAND_ID "
    			);
    	q.setParameter("STAND_ID", standId);
        return q.executeUpdate();
    }
    
    public int deleteStand(Long standId) {
    	Query q = this.entityManager.createNativeQuery(
    			"DELETE FROM EAM_COURSE_SUBJECT_STANDARD "
    			+ "WHERE STAND_ID = :STAND_ID "
    			);
    	q.setParameter("STAND_ID", standId);
        return q.executeUpdate();
    }

}
