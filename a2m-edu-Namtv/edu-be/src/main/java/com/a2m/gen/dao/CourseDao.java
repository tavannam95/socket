package com.a2m.gen.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.repository.TsstRoleRepository;
import com.a2m.gen.services.common.CommonService;
//import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Repository
public class CourseDao {
    @Autowired
    private CommonService commonService;

    @Autowired
    private TsstUserDao userDao;

    @Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;

    public List<AemCourse> getCourses(ParamSearchModel args) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourse> cq = cb.createQuery(AemCourse.class);
        Root<AemCourse> course = cq.from(AemCourse.class);
        cq.orderBy(cb.desc(course.get("courseId")));
        cq.select(course);

//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
            predicates.add(cb.like(cb.lower(course.get("courseNm")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
            predicates.add(cb.equal(course.get("status"), args.getSearchStatus()));
        }
        
        if (args.getDisplayStatus() != null && !"".equals(args.getDisplayStatus())) {
        	predicates.add(cb.equal(course.get("displayStatus"), args.getSearchStatus()));
        }

        predicates.add(cb.equal(course.get("deleteFlag"), false));

        cq.where(predicates.toArray(new Predicate[0]));

        if (args.getGetAll() != null && args.getGetAll()) {
            return entityManager.createQuery(cq).getResultList();
        }

        return entityManager.createQuery(cq)
                .setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
                .getResultList();
    }

    public Long countAllCourse(ParamBaseModel search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemCourse> root = criteriaQuery.from(AemCourse.class);
        criteriaQuery.select(cb.count(root));

        List<Predicate> predicates = new ArrayList<>();
        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
            predicates.add(cb.like(cb.lower(root.get("courseNm")), "%" + search.getSearchText().toLowerCase() + "%"));
        }
        if (search.getSearchStatus() != null && !"".equals(search.getSearchStatus())) {
            predicates.add(cb.equal(root.get("status"), search.getSearchStatus()));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
        return result;
    }

    public AemCourse getCourse(Edu0102RequestModel model) {
        return this.entityManager.find(AemCourse.class, model.getKey());
    }

//  
//    public AemCourse getCourselist(Edu0102RequestModel Edu0102RequestModel) {
//        return this.entityManager.find(AemCourse.class, Edu0102RequestModel.getKey());
//    }
    public AemCourse saveCourse(Edu0102RequestModel model, AemCourse db) {
        db.setCourseNm(model.getCourseNm());
        db.setCourseCode(model.getCourseCode());
        db.setCourseContent(model.getCourseContent());
        db.setCourseGoal(model.getCourseGoal());
        db.setCourseStartDt(model.getCourseStartDate());
        db.setCourseEndDt(model.getCourseEndDate());
        db.setStatus(model.getStatus());
        db.setDisplayStatus(model.getDisplayStatus());
        db.setIsComingSoon(model.getIsComingSoon());
        db.setImgPath(model.getImgPath());
        DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
        entityManager.persist(db);
        return db;
    }

    public AemCourse deleteCourse(Long id) {
        Long CourseId = CastUtil.castToLong(id);
        AemCourse db = this.entityManager.find(AemCourse.class, CourseId);
        db.setDeleteFlag(true);

        entityManager.persist(db);
        return db;
    }

    public List<AemCourse> getListCourseByUserUid(String userUid) throws Exception {

        TsstUser tsstUser = userDao.getTsstUserByUserUid(userUid);
        String userType = tsstUser.getUserType();

        List<AemCourse> listCourse = new ArrayList<>();
        String queryString = "";
        if (userType.equals("STU")) {
            queryString = "SELECT DISTINCT "
                    + " course.COURSE_ID,course.COURSE_CODE,course.COURSE_NM,course.START_DT,course.END_DT,course.COURSE_TYPE,course.COURSE_CONTENT,course.COURSE_GOAL,"
                    + " course.`STATUS`,course.ISCOMINGSOON,course.IMG_PATH,course.INS_UID,course.INS_DT,course.UPD_UID,course.UPD_DT,course.DELETE_FLAG"
                    + " FROM EAM_COURSE AS course"
                    +" LEFT JOIN EAM_CLASS class ON course.COURSE_ID = class.COURSE_ID"
                    +" LEFT JOIN EAM_CLASS_STUDENT_MAP classStudent ON class.CLASS_ID = classStudent.CLASS_ID"
                    +" LEFT JOIN EAM_STUDENT_INFO student ON classStudent.STUDENT_INFO_ID = student.STUDENT_INFO_ID"
                    +" LEFT JOIN TSST_USER USER ON student.STUDENT_INFO_ID = USER.USER_INFO_ID"
                    + " WHERE  USER.USER_UID ="
                    + ":userUid";
        } else if (userType.equals("TEA")) {
            queryString = "SELECT DISTINCT"
                    + " course.COURSE_ID,course.COURSE_CODE,course.COURSE_NM,course.START_DT,course.END_DT,course.COURSE_TYPE,course.COURSE_CONTENT,course.COURSE_GOAL,"
                    + " course.`STATUS`,course.ISCOMINGSOON,course.IMG_PATH,course.INS_UID,course.INS_DT,course.UPD_UID,course.UPD_DT,course.DELETE_FLAG"
                    + " FROM EAM_COURSE AS course"
                    +" LEFT JOIN EAM_COURSE_SUBJECT_MAP AS  courseSubject ON course.COURSE_ID = courseSubject.COURSE_ID" 
                    + " LEFT JOIN EAM_COURSE_SUBJECT as subject ON courseSubject.SUBJECT_ID = subject.SUBJECT_ID" 
                    +" LEFT JOIN EAM_TEACHER_INFO AS teacher ON subject.TEACHER_INFO_ID = teacher.TEACHER_INFO_ID" 
                    +" LEFT JOIN TSST_USER AS USER ON teacher.TEACHER_INFO_ID = USER.USER_INFO_ID "
                    + " WHERE  USER.USER_UID ="
                    + ":userUid"
                    + " UNION "
                    + " SELECT DISTINCT"
                    + " course.COURSE_ID,course.COURSE_CODE,course.COURSE_NM,course.START_DT,course.END_DT,course.COURSE_TYPE,course.COURSE_CONTENT,course.COURSE_GOAL,"
                    + " course.`STATUS`,course.ISCOMINGSOON,course.IMG_PATH,course.INS_UID,course.INS_DT,course.UPD_UID,course.UPD_DT,course.DELETE_FLAG"
                    + " FROM EAM_COURSE AS course"
                    + " WHERE  course.INS_UID ="
                    + ":userUid";
        } else if (userType.equals("EMP")) {
            queryString = "SELECT DISTINCT"
                    + " course.COURSE_ID,course.COURSE_CODE,course.COURSE_NM,course.START_DT,course.END_DT,course.COURSE_TYPE,course.COURSE_CONTENT,course.COURSE_GOAL,"
                    + " course.`STATUS`,course.ISCOMINGSOON,course.IMG_PATH,course.INS_UID,course.INS_DT,course.UPD_UID,course.UPD_DT,course.DELETE_FLAG"
                    + " FROM EAM_COURSE AS course "
                    + " LEFT JOIN EAM_COURSE_SUBJECT_MAP AS  courseSubject"
                    + " ON course.COURSE_ID = courseSubject.COURSE_ID"
                    + " LEFT JOIN EAM_COURSE_SUBJECT as subject"
                    + " ON courseSubject.SUBJECT_ID = subject.SUBJECT_ID"
                    + " LEFT JOIN TSST_USER AS USER "
                    + " ON subject.USER_UID = USER.USER_UID"
                    + " WHERE  USER.USER_UID ="
                    + ":userUid";

        }

        Query query = this.entityManager.createNativeQuery(queryString).setParameter("userUid", userUid);
        List resultList = query.getResultList();
        if (resultList.size() > 0) {
            for (Object object : resultList) {
                Object[] objects = (Object[]) object;
                Long courseId = ((BigInteger) objects[0]).longValue();
                String courseCode = objects[1] != null ? objects[1].toString() : null;
                String courseNm = objects[2] != null ? objects[2].toString() : null;
                Date startDate = objects[3] != null ? (Date) objects[3] : null;
                Date endDate = objects[4] != null ? (Date) objects[4] : null;
                String courseType = objects[5] != null ? objects[5].toString() : null;
                String courseContent = objects[6] != null ? objects[6].toString() : null;
                String courseGoal = objects[7] != null ? objects[7].toString() : null;
                Boolean status = objects[8] != null ? (Boolean) objects[8] : null;
                Boolean isComingSoon = objects[9] != null ? (Boolean) objects[9] : null;
                String img_path = objects[10] != null ? objects[10].toString() : null;
                String ins_uid = objects[11] != null ? objects[11].toString() : null;
                Date ins_dt = objects[12] != null ? (Date) objects[12] : null;
                String upd_uid = objects[13] != null ? objects[13].toString() : null;
                Date upd_dt = objects[14] != null ? (Date) objects[14] : null;
                Boolean deleteFlag = objects[15] != null ? (Boolean) objects[15] : null;

                AemCourse aemCourse = new AemCourse();
                aemCourse.setCourseId(courseId);
                aemCourse.setCourseCode(courseCode);
                aemCourse.setCourseNm(courseNm);
                aemCourse.setCourseStartDt(startDate);
                aemCourse.setCourseEndDt(endDate);
                aemCourse.setCourseContent(courseContent);
                aemCourse.setCourseGoal(courseGoal);
                aemCourse.setStatus(status);
                aemCourse.setIsComingSoon(isComingSoon);
                aemCourse.setImgPath(img_path);
                aemCourse.setInsUid(ins_uid);
                aemCourse.setInsDt(ins_dt);
                aemCourse.setUpdUid(upd_uid);
                aemCourse.setUpdDt(upd_dt);
                aemCourse.setDeleteFlag(deleteFlag);

                listCourse.add(aemCourse);
//                System.out.println(objects[0]);
            }
        }

        return listCourse;
    }

    public List<AemCourse> getListCourseInprogress() {

        List<AemCourse> result = new ArrayList<>();

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        String string = "SELECT DISTINCT"
                + " course.COURSE_ID,course.COURSE_CODE,course.COURSE_NM,course.START_DT,course.END_DT,course.COURSE_TYPE,course.COURSE_CONTENT,course.COURSE_GOAL,"
                + " course.`STATUS`,course.DISPLAY_STATUS,course.ISCOMINGSOON,course.IMG_PATH,course.INS_UID,course.INS_DT,course.UPD_UID,course.UPD_DT,course.DELETE_FLAG"
                + " FROM EAM_COURSE course"
                + " LEFT JOIN EAM_CLASS class"
                + " ON course.COURSE_ID = class.COURSE_ID"
                + " WHERE class.START_DT <= "
                + ":timeStamp"
//                + " AND class.END_DT >= "
//                + ":timeStamp"
                +" AND class.ISFINISH <> 1";

        Query query = this.entityManager.createNativeQuery(string,AemCourse.class).setParameter("timeStamp", timeStamp);
        ;
        List<AemCourse> resultList = query.getResultList();
//        if (resultList.size() > 0) {
//            for (Object object : resultList) {
//                Object[] objects = (Object[]) object;
//                Long courseId = ((BigInteger) objects[0]).longValue();
//                String courseCode = objects[1] != null ? objects[1].toString() : null;
//                String courseNm = objects[2] != null ? objects[2].toString() : null;
//                Date startDate = objects[3] != null ? (Date) objects[3] : null;
//                Date endDate = objects[4] != null ? (Date) objects[4] : null;
//                String courseType = objects[5] != null ? objects[5].toString() : null;
//                String courseContent = objects[6] != null ? objects[6].toString() : null;
//                String courseGoal = objects[7] != null ? objects[7].toString() : null;
//                Boolean status = objects[8] != null ? (Boolean) objects[8] : null;
//                Boolean displayStatus = objects[8] != null ? (Boolean) objects[8] : null;
//                Boolean isComingSoon = objects[9] != null ? (Boolean) objects[9] : null;
//                String img_path = objects[10] != null ? objects[10].toString() : null;
//                String ins_uid = objects[11] != null ? objects[11].toString() : null;
//                Date ins_dt = objects[12] != null ? (Date) objects[12] : null;
//                String upd_uid = objects[13] != null ? objects[13].toString() : null;
//                Date upd_dt = objects[14] != null ? (Date) objects[14] : null;
//                Boolean deleteFlag = objects[15] != null ? (Boolean) objects[15] : null;

//                AemCourse aemCourse = new AemCourse();
//                aemCourse.setCourseId(courseId);
//                aemCourse.setCourseCode(courseCode);
//                aemCourse.setCourseNm(courseNm);
//                aemCourse.setCourseStartDt(startDate);
//                aemCourse.setCourseEndDt(endDate);
//                aemCourse.setCourseContent(courseContent);
//                aemCourse.setCourseGoal(courseGoal);
//                aemCourse.setStatus(status);
//                aemCourse.setDisplayStatus(displayStatus);
//                aemCourse.setIsComingSoon(isComingSoon);
//                aemCourse.setImgPath(img_path);
//                aemCourse.setInsUid(ins_uid);
//                aemCourse.setInsDt(ins_dt);
//                aemCourse.setUpdUid(upd_uid);
//                aemCourse.setUpdDt(upd_dt);
//                aemCourse.setDeleteFlag(deleteFlag);

//                result.add(aemCourse);
//            }
//        }
        return resultList;
    }

    public Object getCourseByUserUid(ParamBaseModel search) {
        List result = new ArrayList<>();
        Query query = this.entityManager.createNativeQuery(
                "SELECT course.COURSE_ID,"
                        + "course.COURSE_CODE,"
                        + "course.COURSE_NM,"
                        + "course.START_DT,"
                        + "course.END_DT,"
                        + "course.COURSE_TYPE,"
                        + "course.COURSE_CONTENT,"
                        + "course.COURSE_GOAL,"
                        + "course.STATUS,"
                        + "course.DISPLAY_STATUS,"
                        + "course.ISCOMINGSOON,"
                        + "course.IMG_PATH,"
                        + "course.INS_UID,"
                        + "course.INS_DT,"
                        + "course.UPD_UID,"
                        + "course.UPD_DT,"
                        + "course.DELETE_FLAG "
                        + "FROM EAM_COURSE AS course "
                        + "LEFT JOIN EAM_CLASS AS cl ON cl.COURSE_ID = course.COURSE_ID "
                        + "LEFT JOIN EAM_CLASS_STUDENT_MAP AS csm ON csm.CLASS_ID = cl.CLASS_ID "
                        + "LEFT JOIN EAM_STUDENT_INFO AS si ON si.STUDENT_INFO_ID = csm.STUDENT_INFO_ID "
                        + "LEFT JOIN TSST_USER AS tu ON tu.USER_INFO_ID = si.STUDENT_INFO_ID AND tu.USER_TYPE='STU' "
                        + "WHERE tu.USER_UID=:userUid "
                        + "AND UPPER(course.COURSE_NM) like CONCAT('%',UPPER(:courseNm),'%') "
                        + "AND course.DELETE_FLAG=false "
                        + "AND course.STATUS=true "
                        + "GROUP BY course.COURSE_ID")
                .setParameter("userUid", search.getKey()).setParameter("courseNm", search.getSearchText());
        List list = new ArrayList<>();
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object object : list) {
            Object[] objects = (Object[]) object;
            BigInteger courseId = (BigInteger) objects[0];
            String courseCode = (String) objects[1];
            String courseName = (String) objects[2];
            Date startDt = (Date) objects[3];
            Date endDt = (Date) objects[4];
            String courseType = (String) objects[5];
            String courseContent = (String) objects[6];
            String courseGoal = (String) objects[7];
            Boolean status = (Boolean) objects[8];
            Boolean displayStatus = (Boolean) objects[9];
            Boolean isComingsoon = (Boolean) objects[10];
            String imgPath = (String) objects[11];
            String insUid = (String) objects[12];
            Date insDate = (Date) objects[13];
            String updUid = (String) objects[14];
            Date updDate = (Date) objects[15];
            Boolean deleteFlag = (Boolean) objects[16];

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("key", courseId);
            map.put("courseCode", courseCode);
            map.put("courseNm", courseName);
            map.put("courseEndDate", endDt);
            map.put("courseStartDate", startDt);
            map.put("courseType", courseType);
            map.put("courseContent", courseContent);
            map.put("courseGoal", courseGoal);
            map.put("status", status);
            map.put("displayStatus", displayStatus);
            map.put("isComingsoon", isComingsoon);
            map.put("imgPath", imgPath);
            map.put("insUid", insUid);
            map.put("insDate", insDate);
            map.put("updUid", updUid);
            map.put("updDate", updDate);
            map.put("deleteFlag", deleteFlag);
            result.add(map);
        }
        return result;
    }

}
