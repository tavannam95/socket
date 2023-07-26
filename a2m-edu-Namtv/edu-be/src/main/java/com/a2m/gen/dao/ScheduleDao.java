package com.a2m.gen.dao;

import java.math.BigInteger;
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

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemRoom;
import com.a2m.gen.entities.edu.AemSchedule;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.ScheduleModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.ExcelService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Repository
public class ScheduleDao {
	@Autowired
	private CommonService commonService;

    @Autowired
    private ExcelService excelService;


	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
	private EntityManager entityManager;

    public List<AemSchedule> getSchedules(ScheduleModel args) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemSchedule> cq = cb.createQuery(AemSchedule.class);
        Root<AemSchedule> schedule = cq.from(AemSchedule.class);
        cq.orderBy(cb.desc(schedule.get("scheduleId")));
        cq.select(schedule);

//        where start
        List<Predicate> predicates = new ArrayList<>();
//        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
//            predicates.add(cb.like(cb.lower(course.get("")), "%" + args.getSearchText().toLowerCase() + "%"));
//        }
        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
            predicates.add(cb.equal(schedule.get("status"), args.getSearchStatus()));
        }
        if (args.getRoomId() != null && !"".equals(args.getRoomId())) {
            predicates.add(cb.equal(schedule.get("roomSchedule"), args.getRoomId()));
        }

        if (args.getClassId() != null && !"".equals(args.getClassId())) {
            predicates.add(cb.equal(schedule.get("classSchedule"), args.getRoomId()));
        }

        if (args.getStartTime() != null && !"".equals(args.getStartTime())) {
        	
            predicates.add(cb.greaterThanOrEqualTo(schedule.<Date>get("startDate"), stringToDate(args.getStartTime(),false)));
         }
        if (args.getEndTime() != null && !"".equals(args.getEndTime())) {
        	predicates.add(cb.lessThanOrEqualTo(schedule.<Date>get("startDate"), stringToDate(args.getEndTime(),true)));
        }

        predicates.add(cb.equal(schedule.get("deleteFlag"), false));

        cq.where(predicates.toArray(new Predicate[0]));

        if (args.getGetAll() != null && args.getGetAll()) {
            return entityManager.createQuery(cq).getResultList();
        }

        if (args.getRows() == null || args.getAll != null) {
            return entityManager.createQuery(cq).getResultList();
        }
        return entityManager.createQuery(cq)
                .setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
                .getResultList();
    }

    public Long countAllSchedule(ParamBaseModel search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemSchedule> root = criteriaQuery.from(AemSchedule.class);
        criteriaQuery.select(cb.count(root));

        List<Predicate> predicates = new ArrayList<>();
//        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
//            predicates.add(cb.like(cb.lower(root.get("courseNm")), "%" + search.getSearchText().toLowerCase() + "%"));
//        }
        
        if (search.getSearchStatus() != null && !"".equals(search.getSearchStatus())) {
            predicates.add(cb.equal(root.get("status"), search.getSearchStatus()));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
        return result;
    }

    public Long countSchedule(HashMap<String, Object> parameter) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemSchedule> root = criteriaQuery.from(AemSchedule.class);
        criteriaQuery.select(cb.count(root));

        List<Predicate> predicates = new ArrayList<>();
//        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
//            predicates.add(cb.like(cb.lower(root.get("courseNm")), "%" + search.getSearchText().toLowerCase() + "%"));
//        }
        if(!CastUtil.castToString(parameter.get("classId")).equals("")) {
            predicates.add(cb.equal(root.get("classSchedule"), Long.parseLong(CastUtil.castToString(parameter.get("classId")))));
        }
        if(!CastUtil.castToString(parameter.get("roomId")).equals("")) {
            predicates.add(cb.equal(root.get("roomSchedule"), Long.parseLong(CastUtil.castToString(parameter.get("roomId")))));
        }
        if(!CastUtil.castToString(parameter.get("startTime")).equals("")) {
        	String startTime = CastUtil.castToString(parameter.get("startTime"));
        	startTime = startTime.replace('-', '/');
        	String endTime = CastUtil.castToString(parameter.get("endTime"));
        	endTime = endTime.replace('-', '/');
            if (startTime != null && !"".equals(startTime)) {
                predicates.add(cb.greaterThanOrEqualTo(root.<Date>get("startDate"), stringToDate(startTime,false)));
             }
            if (endTime != null && !"".equals(endTime)) {
            	predicates.add(cb.lessThanOrEqualTo(root.<Date>get("startDate"), stringToDate(endTime,true)));
            }

        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
        return result;
    }

    public AemSchedule saveSchedule(ScheduleModel model, AemSchedule db) {

        AemRoom roomSchedule = this.entityManager.find(AemRoom.class, model.getRoomId());
        db.setRoomSchedule(roomSchedule);
        AemClass classSchedule = this.entityManager.find(AemClass.class, model.getClassId());
        db.setClassSchedule(classSchedule);
        AemCourseSubject subjectSchedule = this.entityManager.find(AemCourseSubject.class, model.getSubjectId());
        db.setSubjectSchedule(subjectSchedule);
        db.setStatus(model.getStatus());
        db.setIsFinish(model.getIsFinish());
        db.setStartDate(model.getStartDate());
        db.setEndDate(model.getEndDate());
        DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
        entityManager.persist(db);
        return db;
    }

    public AemSchedule getScheduleById(ScheduleModel model) {
        return this.entityManager.find(AemSchedule.class, model.getKey());
    }

	public List<AemClass> getClassList(ClassModel args) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AemClass> cq = cb.createQuery(AemClass.class);
		Root<AemClass> classEntity = cq.from(AemClass.class);
		cq.orderBy(cb.desc(classEntity.get("classId")));
		cq.select(classEntity);

//        where start
		List<Predicate> predicates = new ArrayList<>();
		
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

    public AemSchedule getSchedule(ScheduleModel model) {
        return this.entityManager.find(AemSchedule.class, model.getKey());
    }

    public AemCourse getCourseById(Long courseId) {
        return this.entityManager.find(AemCourse.class, courseId);
    }

    public List<AemRoom> getRooms(ParamBaseModel args) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemRoom> cq = cb.createQuery(AemRoom.class);
        Root<AemRoom> room = cq.from(AemRoom.class);
        cq.orderBy(cb.desc(room.get("roomId")));
        cq.select(room);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        cq.where(predicates.toArray(new Predicate[0]));
//        where end

        if (args.getRows() == null || args.getAll != null) {
            return entityManager.createQuery(cq).getResultList();
        }
        return entityManager.createQuery(cq)
                .setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
                .getResultList();
    }

    public List<AemSchedule> getScheduleList(HashMap<String, Object> parameter) {
        List<AemSchedule> result = new ArrayList<AemSchedule>();
        String searchByClass = "";
        String searchByRoom = "";
        String getListSchedule = "";
        String getSchedule = "WHERE 1=1 " ;
        String getTime = "";
        if( CastUtil.castToString(parameter.get("userType")).equals("teacher")){
            getSchedule = "LEFT JOIN EAM_COURSE_SUBJECT sub ON s.SUBJECT_ID = sub.SUBJECT_ID "
                    + "A sub.TEACHER_INFO_ID = " + CastUtil.castToString(parameter.get("userId"))+" ";
        }else if( CastUtil.castToString(parameter.get("userType")).equals("assist") ) {
            getSchedule = "LEFT JOIN EAM_COURSE_SUBJECT sub ON s.SUBJECT_ID = sub.SUBJECT_ID "
                    + "WHERE sub.USER_UID  = " + CastUtil.castToString(parameter.get("userId"))+" ";
        }else if( CastUtil.castToString(parameter.get("userType")).equals("student") ) {
            getSchedule = "LEFT JOIN EAM_CLASS_STUDENT_MAP c ON s.CLASS_ID = c.CLASS_ID "
                    + "WHERE c.STUDENT_INFO_ID = " + CastUtil.castToString(parameter.get("userId"))+" ";
        }
        if(!CastUtil.castToString(parameter.get("classId")).equals("")) {
            searchByClass = "AND s.CLASS_ID = " + CastUtil.castToString(parameter.get("classId"))+" " ;
        }
        if(!CastUtil.castToString(parameter.get("roomId")).equals("")) {
            searchByRoom = "AND s.ROOM_ID = " + CastUtil.castToString(parameter.get("roomId"))+" " ;
        }
        if(!CastUtil.castToString(parameter.get("startTime")).equals("")) {
        	getTime = "AND( s.START_DATE BETWEEN '" + CastUtil.castToString(parameter.get("startTime"))
        	+"' AND '" + CastUtil.castToString(parameter.get("endTime")) + "') ";
        }
        if(!CastUtil.castToString(parameter.get("rows")).equals("")) {
            getListSchedule = "LIMIT " + parameter.get("rows") + " "
          + "OFFSET " + (Integer.valueOf((String) parameter.get("rows")) * Integer.valueOf((String) parameter.get("page")));
        }
        Query query  = this.entityManager.createNativeQuery(
                "SELECT s.SCHEDULE_ID " //0
                + ",s.ROOM_ID " //1
                + ",s.CLASS_ID " //2
                + ",s.SUBJECT_ID " //3
                + ",s.START_DATE " //4
                + ",s.END_DATE " //5
                
                + "from EAM_SCHEDULE s "
                + getSchedule     
                + searchByClass
                + searchByRoom
                + getTime
                + "ORDER BY s.INS_DT DESC " 
                + getListSchedule
             );
        
        List list = new ArrayList<>();
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        for (Object object : list) {
            Object[] objects = (Object[]) object;
            BigInteger scheduleId = (BigInteger) objects[0];
            BigInteger roomId = (BigInteger) objects[1];
            BigInteger classId = (BigInteger) objects[2];
            BigInteger subjectId = (BigInteger) objects[3];
            Date startDate = (Date) objects[4];
            Date endDate = (Date) objects[5];
            
            
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("scheduleId", scheduleId);
//            map.put("roomId", roomId);
//            map.put("classId", classId);
//            map.put("subjectId", subjectId);
//            map.put("startDate", startDate);
//            map.put("endDate", endDate);   
            
            AemSchedule schedule = new AemSchedule();
            schedule.setScheduleId(scheduleId.longValue());
            AemRoom roomSchedule = this.entityManager.find(AemRoom.class, roomId.longValue());
            schedule.setRoomSchedule(roomSchedule);
            AemClass classSchedule = this.entityManager.find(AemClass.class, classId.longValue());
            schedule.setClassSchedule(classSchedule);
            AemCourseSubject subjectSchedule = this.entityManager.find(AemCourseSubject.class, subjectId.longValue());
            schedule.setSubjectSchedule(subjectSchedule);
            schedule.setStartDate(startDate);
            schedule.setEndDate(endDate);
            result.add(schedule);
        }
        
        return result;
    }

    public boolean delete(Long id) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<AemSchedule> cd = cb.createCriteriaDelete(AemSchedule.class);
        Root<AemSchedule> sample = cd.from(AemSchedule.class);
        
        cd.where(cb.equal(sample.get("scheduleId"), id));
        this.entityManager.createQuery(cd).executeUpdate();
        return true;
    }

    public Date stringToDate(String param,Boolean flag) throws Exception {
    	Date date = new Date(param);
    	if(flag) {
    		Calendar c = Calendar.getInstance(); 
    		c.setTime(date); 
    		c.add(Calendar.DATE, 1);
    		date = c.getTime();
    	}
		return date;
    }
 
}
