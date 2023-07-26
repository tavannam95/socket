package com.a2m.gen.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TccoStd;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectHistory;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.AemTeacherEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.course.SubjectHistoryModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.TccoSTDService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Repository
public class SubjectDao {
    @Autowired
    private CommonService commonService;

    @Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;

    @Autowired
    private TccoSTDService tccoSTDService;

    public List<AemCourseSubject> getSubjects(ParamBaseModel args) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourseSubject> cq = cb.createQuery(AemCourseSubject.class);
        Root<AemCourseSubject> subject = cq.from(AemCourseSubject.class);

        cq.orderBy(cb.desc(subject.get("subjectId")));
        cq.select(subject);

//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
            predicates.add(cb.like(cb.lower(subject.get("subjectNm")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(subject.get("subjectNm"), args.getSearchText()));
        }

        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
            predicates.add(cb.equal(subject.get("status"), args.getSearchStatus()));
        }

        predicates.add(cb.equal(subject.get("deleteFlag"), false));

        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        if (args.getGetAll() != null && args.getGetAll()) {
            return entityManager.createQuery(cq).getResultList();
        }
        return entityManager.createQuery(cq)
//        		=== start paging
                .setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();
    }

    public Long countAllSubject(ParamBaseModel args) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemCourseSubject> root = criteriaQuery.from(AemCourseSubject.class);
        criteriaQuery.select(cb.count(root));

        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
            predicates.add(cb.like(cb.lower(root.get("subjectNm")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
        return result;
    }

    public AemCourseSubject saveSubject(SubjectModel model, AemCourseSubject db) {
        db.setSubjectId(model.getKey());
        db.setDocumentStatus(model.getDocumentStatus());
        db.setSubjectNm(model.getSubjectNm());
        db.setSubjectCode(model.getSubjectCode());
        db.setSubjectContent(model.getSubjectContent());
        db.setSubjectGoal(model.getSubjectGoal());
        db.setStartDt(model.getStartDt());
        db.setEndDt(model.getEndDt());
        db.setOrdNo(model.getOrdNo());
        db.setStatus(model.getStatus());
//    	model.getTsstUser();
        db.setTsstUser(model.getTsstUser() != null ? model.getTsstUser() : null);
        AemTeacherEntity aemTeacherEntity = model.getTeacherModel() != null
                ? new AemTeacherEntity(model.getTeacherModel())
                : null;
        db.setTeacherEntity(aemTeacherEntity);
        DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
        if (model.getKey() != null)
            entityManager.merge(db);
        else
            entityManager.persist(db);
        return db;
    }

    public AemCourseSubjectHistory saveSubjectHistory(SubjectHistoryModel model, AemCourseSubjectHistory db) {
        db.setSubjectHistoryId(model.getKey());
        db.setDocumentStatus(model.getDocumentStatus());
        db.setSubjectId(model.getSubjectId());
        db.setTeacherInfoId(model.getTeacherModel().getKey());
        db.setUserUid(model.getTsstUser().getUserUid());
        db.setSubjectNm(model.getSubjectNm());
        db.setSubjectCode(model.getSubjectCode());
        db.setSubjectContent(model.getSubjectContent());
        db.setSubjectGoal(model.getSubjectGoal());
        db.setApprovalHistoryId(model.getApprovalHistoryId());
        db.setOrdNo(model.getOrdNo());
        db.setStartDt(model.getStartDt());
        db.setEndDt(model.getEndDt());
        db.setStatus(model.getStatus());
        
        db.setEditListChapter(model.getEditListChapter());
        db.setEditStand(model.getEditStand());
        db.setEditSubjectForm(model.getEditSubjectForm());
        DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
        if (model.getKey() != null)
            entityManager.merge(db);
        else
            entityManager.persist(db);
        return db;
    }
    
    public AemCourseSubjectHistory mergeSubjectHistory(AemCourseSubjectHistory db) {
    	entityManager.merge(db);
        return db;
    }

    public AemCourseSubject deleteSubject(String id) {
        Long subjectId = CastUtil.castToLong(id);
        AemCourseSubject db = this.entityManager.find(AemCourseSubject.class, subjectId);
        db.setDeleteFlag(true);

        entityManager.persist(db);
        return db;
    }

//    public List<HashMap<String, Object>> getLectureScheduleBySubjectId(String subjectId) {
//        final int _START = 2;
//        List<TccoStd> tccoStds = tccoSTDService.getCommCdByUpCommCd("12");
//
//        String sqlSumIf = "";
//
//        for (TccoStd tccoStd : tccoStds) {
//            String tmp = ", SUM(IF( lecture.ORGANIZATION_FORMAL = '" + tccoStd.getCommCd()
//                    + "', lecture.TOTAL_LESSON ,0)) AS '" + tccoStd.getCommCd() + "' ";
//            sqlSumIf += tmp;
//        }
//
//        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
//
//        Query q = entityManager.createNativeQuery(
//                "SELECT chap.CHAPTER_ID " // 0
//                        + ", chap.CHAPTER_NM " // 1
//
//                        + sqlSumIf
//
//                        + "FROM EAM_COURSE_SUBJECT_CHAPTER chap "
//                        + "LEFT JOIN EAM_COURSE_SUBJECT_CHAPTER_LECTURES lecture ON chap.CHAPTER_ID = lecture.CHAPTER_ID "
//
//                        + "WHERE chap.SUBJECT_ID = " + subjectId + " "
//                        + " AND chap.DELETE_FLAG = false "
//                        + "GROUP BY chap.CHAPTER_ID "
//                        + "ORDER BY chap.CHAPTER_ID ASC , chap.INS_DT ASC "
//
//        );
//
//        List<Object[]> resultList = q.getResultList();
//
//        for (Object[] objects : resultList) {
//            // Object[] objects = (Object[]) object;
//
//            BigInteger chapterId = (BigInteger) objects[0];
//            String chapterNm = (String) objects[1];
//
//            HashMap<String, Object> organizationFormals = new HashMap<String, Object>();
//
//            int start = _START;
//            for (TccoStd tccoStd : tccoStds) {
//
//                BigDecimal value = (BigDecimal) objects[start++];
//                organizationFormals.put(tccoStd.getCommCd(), value.longValue());
//
//            }
//
//            HashMap<String, Object> tmp = new HashMap<String, Object>();
//            tmp.put("chapterId", chapterId);
//            tmp.put("chapterNm", chapterNm);
//            tmp.put("organizationFormals", organizationFormals);
//
//            result.add(tmp);
//        }
//
//        return result;
//
//    }
    
    public List<HashMap<String, Object>> getLectureScheduleBySubjectId(String subjectId) {
        final int _START = 2;
        List<TccoStd> tccoStds = tccoSTDService.getCommCdByUpCommCd("12");

        String sqlSumIf = "";

        for (TccoStd tccoStd : tccoStds) {
            String tmp = ", IF( chap.ORGANIZATION_FORMAL = '" + tccoStd.getCommCd()
                    + "', chap.TOTAL_LESSON ,0) AS '" + tccoStd.getCommCd() + "' ";
            sqlSumIf += tmp;
        }

        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

        Query q = entityManager.createNativeQuery(
                "SELECT chap.CHAPTER_ID " // 0
                        + ", chap.CHAPTER_NM " // 1

                        + sqlSumIf

                        + "FROM EAM_COURSE_SUBJECT_CHAPTER chap "
                        //+ "LEFT JOIN EAM_COURSE_SUBJECT_CHAPTER_LECTURES lecture ON chap.CHAPTER_ID = lecture.CHAPTER_ID "

                        + "WHERE chap.SUBJECT_ID = " + subjectId + " "
                        + " AND chap.DELETE_FLAG = false "
                        //+ "GROUP BY chap.CHAPTER_ID "
                        + "ORDER BY chap.CHAPTER_ID ASC , chap.INS_DT ASC "

        );

        List<Object[]> resultList = q.getResultList();

        for (Object[] objects : resultList) {
            // Object[] objects = (Object[]) object;

            BigInteger chapterId = (BigInteger) objects[0];
            String chapterNm = (String) objects[1];

            HashMap<String, Object> organizationFormals = new HashMap<String, Object>();

            int start = _START;
            for (TccoStd tccoStd : tccoStds) {

            	BigInteger value = (BigInteger) objects[start++];
                organizationFormals.put(tccoStd.getCommCd(), value.longValue());

            }

            HashMap<String, Object> tmp = new HashMap<String, Object>();
            tmp.put("chapterId", chapterId);
            tmp.put("chapterNm", chapterNm);
            tmp.put("organizationFormals", organizationFormals);

            result.add(tmp);
        }

        return result;

    }

    public AemCourseSubject getSubject(SubjectModel subjectModel) {
        return this.entityManager.find(AemCourseSubject.class, subjectModel.getKey());
    }
    
    public AemCourseSubject getSubject(Long id) {
        return this.entityManager.find(AemCourseSubject.class, id);
    }

    public String getUserIdByTeachId(Long Id) {
        String result = "";
        Query query = this.entityManager.createNativeQuery(
                "SELECT u.USER_UID FROM TSST_USER u WHERE :teacherInfoId = u.USER_INFO_ID  AND u.USER_TYPE = 'TEA'").setParameter("teacherInfoId", Id);
        List list = new ArrayList<>();
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object object : list) {
            Object objects = (Object) object;
            String userUid = (String) objects;
            
            result = userUid;
        }
        return result;
    }

    public String getUserIdByCourseUid(Long Id) {
        String result = "";
        Query query = this.entityManager.createNativeQuery(
                "SELECT c.INS_UID FROM EAM_COURSE_SUBJECT_MAP map "
                + "LEFT JOIN EAM_COURSE c on c.COURSE_ID = map.COURSE_ID "
                + "WHERE map.SUBJECT_ID = :subjectId").setParameter("subjectId", Id);
        List list = new ArrayList<>();
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object object : list) {
            Object objects = (Object) object;
            String userUid = (String) objects;
            
            result = userUid;
        }
        return result;
    }

    public AemCourseSubjectHistory getSubjectHistory(SubjectHistoryModel model) {
        return this.entityManager.find(AemCourseSubjectHistory.class, model.getKey());
    }
    
    public AemCourseSubjectHistory getSubjectHistory(Long key) {
        return this.entityManager.find(AemCourseSubjectHistory.class, key);
    }

    public AemCourseSubjectHistory searchSubjectHistory(SubjectHistoryModel model) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourseSubjectHistory> cq = cb.createQuery(AemCourseSubjectHistory.class);
        Root<AemCourseSubjectHistory> entity = cq.from(AemCourseSubjectHistory.class);
        cq.orderBy(cb.desc(entity.get("subjectHistoryId")));
        cq.select(entity);

//        where start
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(entity.get("subjectId"), model.getSubjectId())); // subjectId
        cq.where(predicates.toArray(new Predicate[0]));
//        where end

        return entityManager.createQuery(cq)
//        		=== start paging
                .setMaxResults(CastUtil.castToInteger(1))
//        		=== end paging
                .getSingleResult();
    }
    
    public Long countSubjectHistory(Long subjectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<AemCourseSubjectHistory> entity = cq.from(AemCourseSubjectHistory.class);
        cq.orderBy(cb.desc(entity.get("subjectHistoryId")));
        cq.select(cb.count(entity));

//        where start
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(entity.get("subjectId"), subjectId )); // subjectId
        cq.where(predicates.toArray(new Predicate[0]));
//        where end

        Long result = entityManager.createQuery(cq).getSingleResult();
        return result;
    }

    public AemTeacherEntity getTeacher(Long id) {
        return this.entityManager.find(AemTeacherEntity.class, id);
    }

    public TsstUser getTsstUserByUserUid(String userUid) {
        return this.entityManager.find(TsstUser.class, userUid);
    }
}
