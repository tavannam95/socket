package com.a2m.gen.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.edu.QuizItemAnswerEntity;
import com.a2m.gen.models.course.CourseSubjectMapModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class CourseSubjectMapDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemCourseSubjectMap> getCoursSubjectByCourseModel(Edu0102RequestModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourseSubjectMap> cq = cb.createQuery(AemCourseSubjectMap.class);
        Root<AemCourseSubjectMap> csm = cq.from(AemCourseSubjectMap.class);  
        cq.orderBy(cb.desc(csm.get("tableId")));  
        cq.select(csm);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();

        if (args.getKey() != null && !"".equals(args.getKey())) {
	        predicates.add(cb.equal(csm.get("aemCourse"), args.getKey()));
        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
                .getResultList();  
    }
    
    public List<AemCourseSubjectMap> get(Edu0102RequestModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourseSubjectMap> cq = cb.createQuery(AemCourseSubjectMap.class);
        Root<AemCourseSubjectMap> csm = cq.from(AemCourseSubjectMap.class);  
        cq.orderBy(cb.desc(csm.get("tableId")));  
        cq.select(csm);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
//        		=== start paging
        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();  
        
    }
    
    @Transactional
    public AemCourseSubjectMap saveCourseSubject(CourseSubjectMapModel model, AemCourseSubjectMap db) {
    	AemCourseSubjectMap aemCourseSubjectMap = new AemCourseSubjectMap();
    	aemCourseSubjectMap.setAemCouSubject(model.getAemCourseSubject());
        aemCourseSubjectMap.setAemCourse(model.getAemCourse());
		DatabaseUtil.setCommonFields(aemCourseSubjectMap, model); // change loginUserId when use
		entityManager.persist(aemCourseSubjectMap);
		return db;
    }
    
    @Transactional
    public AemCourseSubjectMap saveCourseSubject2(Long courseId, CourseSubjectMapModel model, Long subjectId) {
    	AemCourseSubjectMap aemCourseSubjectMap = new AemCourseSubjectMap();
    	aemCourseSubjectMap.setAemCouSubject( this.entityManager.find(AemCourseSubject.class, subjectId) );
        aemCourseSubjectMap.setAemCourse(this.entityManager.find(AemCourse.class, courseId));
        
		DatabaseUtil.setCommonFields(aemCourseSubjectMap, model); // change loginUserId when use
		entityManager.persist(aemCourseSubjectMap);
		return aemCourseSubjectMap;
    }

    @Transactional
    public boolean delete(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    	CriteriaDelete<AemCourseSubjectMap> cd = cb.createCriteriaDelete(AemCourseSubjectMap.class);
    	Root<AemCourseSubjectMap> sample = cd.from(AemCourseSubjectMap.class);
    	
    	cd.where(cb.equal(sample.get("tableId"), id));
    	this.entityManager.createQuery(cd).executeUpdate();
    	
		return true;
    }
    
    public List<AemCourseSubjectMap> getListCouSubMapBySubId(Long subjectId) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCourseSubjectMap> cq = cb.createQuery(AemCourseSubjectMap.class);
        Root<AemCourseSubjectMap> csm = cq.from(AemCourseSubjectMap.class);  
        cq.orderBy(cb.desc(csm.get("tableId")));  
        cq.select(csm);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();

        if (!subjectId.equals("")) {
	        predicates.add(cb.equal(csm.get("aemCouSubject"), CastUtil.castToLong(subjectId)));
        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
                .getResultList();  
    }
    
	public void deleteBySubjectId(Long subjectId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<AemCourseSubjectMap> criteriaDelete = criteriaBuilder
				.createCriteriaDelete(AemCourseSubjectMap.class);
		Root<AemCourseSubjectMap> root = criteriaDelete.from(AemCourseSubjectMap.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get("aemCouSubject"), subjectId));
		int rowsDeleted = entityManager.createQuery(criteriaDelete).executeUpdate();
		// System.out.println("entities deleted: " + rowsDeleted);
	}
    
}
