package com.a2m.gen.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseInfo;
import com.a2m.gen.entities.edu.AemCourseProgram;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.course.CourseInfoModel;
import com.a2m.gen.models.course.CourseProgramModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.StudentModel;
//import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class CourseProgramDao {
	// @Autowired 
	//  private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemCourseProgram> getCoursesInfo(ParamBaseModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourseProgram> cq = cb.createQuery(AemCourseProgram.class);
        Root<AemCourseProgram> course = cq.from(AemCourseProgram.class);  
        cq.orderBy(cb.desc(course.get("courseProgramId")));
        cq.select(course);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
           predicates.add(cb.like(cb.lower(course.get("title")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
	        predicates.add(cb.equal(course.get("deleteFlag"),false));
        
        
        cq.where(predicates.toArray(new Predicate[0]));
        
        if( args.getGetAll() != null && args.getGetAll() ) {
        	return entityManager.createQuery(cq)
//            		.setMaxResults(CastUtil.castToInteger(args.getRows()))
//                    .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
                    .getResultList();
        }
        
        return entityManager.createQuery(cq)
        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
                .getResultList();
    }
    
//    public Long countAllCourse(ParamBaseModel search){ 	
//    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
//        Root<AemCourse> root = criteriaQuery.from(AemCourse.class);
//        criteriaQuery.select(cb.count(root));
//        
//        List<Predicate> predicates = new ArrayList<>();
//        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
//            predicates.add(cb.like(cb.lower(root.get("courseGoal")), "%" + search.getSearchText().toLowerCase() + "%"));
//        }
//        predicates.add(cb.equal(root.get("deleteFlag"),false));
//        
//        criteriaQuery.where(predicates.toArray(new Predicate[0]));
//
//        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
//    	return result;
//    }
    
    public AemCourseProgram getCourseProgram(CourseProgramModel model) {
    	return this.entityManager.find(AemCourseProgram.class, model.getKey());
    }
    
    public AemCourseProgram save(CourseProgramModel model , AemCourseProgram db) {
//    	db.set
    	db.setTitle(model.getTitle());
    	db.setShortDiscription(model.getShortDiscription());
    	db.setLecture(model.getLecture());
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }

    public AemCourseProgram delete(Long id) {
    	Long courseProgramId = CastUtil.castToLong(id);
    	AemCourseProgram db = this.entityManager.find(AemCourseProgram.class, courseProgramId);
    	db.setDeleteFlag(true);
    	
		entityManager.persist(db);
		return db;
    }
    
    public List<AemCourseProgram> getCourseProgramByCourseId(ParamSearchModel args){
    	List<AemCourseProgram> result = new ArrayList<>();
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourseProgram> cq = cb.createQuery(AemCourseProgram.class);
        Root<AemCourseProgram> course = cq.from(AemCourseProgram.class);  
//        cq.orderBy(cb.desc(course.get("courseProgramId")));
        cq.select(course);
        
        List<Predicate> predicates = new ArrayList<>();
        if (args.getCourseId() != null && !"".equals(args.getCourseId())) {
        	predicates.add(cb.equal(course.get("courseEntity"),args.getCourseId()));
        }
	        predicates.add(cb.equal(course.get("deleteFlag"),false));
        
        
        cq.where(predicates.toArray(new Predicate[0]));
    	
        return  entityManager.createQuery(cq).getResultList();
    }
    
    
 
    
}
