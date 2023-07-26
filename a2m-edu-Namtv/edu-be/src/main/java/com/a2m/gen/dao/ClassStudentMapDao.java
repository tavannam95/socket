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

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.ClassEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.ClassStudentMapModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class ClassStudentMapDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemClassStudentMap> getClassStudentsByStudentModel(StudentModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemClassStudentMap> cq = cb.createQuery(AemClassStudentMap.class);
        Root<AemClassStudentMap> clssStuEntity = cq.from(AemClassStudentMap.class);  
        cq.orderBy(cb.desc(clssStuEntity.get("tableId")));  
        cq.select(clssStuEntity);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();

        if (args.getKey() != null && !"".equals(args.getKey())) {
	        predicates.add(cb.equal(clssStuEntity.get("eamStudent"), args.getKey()));
        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
                .getResultList();  
    }
    
    
    public List<AemClassStudentMap> getClassStudentsByClassId(Long classId) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemClassStudentMap> cq = cb.createQuery(AemClassStudentMap.class);
        Root<AemClassStudentMap> clssStuEntity = cq.from(AemClassStudentMap.class);  
        cq.orderBy(cb.desc(clssStuEntity.get("tableId")));  
        cq.select(clssStuEntity);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();

        if (classId != null && !"".equals(classId)) {
	        predicates.add(cb.equal(clssStuEntity.get("aemClass"), classId));
        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
                .getResultList();  
    }
    
    public List<AemClassStudentMap> get(ClassModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemClassStudentMap> cq = cb.createQuery(AemClassStudentMap.class);
        Root<AemClassStudentMap> clssStuEntity = cq.from(AemClassStudentMap.class);  
        cq.orderBy(cb.desc(clssStuEntity.get("tableId")));  
        cq.select(clssStuEntity);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();

//        if (args.getKey() != null && !"".equals(args.getKey())) {
//	        predicates.add(cb.equal(clssStuEntity.get("CLASS_ID"), args.getKey()));
//        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
//        		=== start paging
        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();  
        
    }
    
    
    
    
    public Long count(ParamBaseModel search){ 	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<ClassEntity> root = criteriaQuery.from(ClassEntity.class);
        criteriaQuery.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
            predicates.add(cb.like(root.get("className"), "%" + search.getSearchText() + "%"));
        }
        if (search.getSearchStatus() != null && !"".equals(search.getSearchStatus())) {
	        predicates.add(cb.equal(root.get("classStatus"), search.getSearchStatus()));
        }
        
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
    	return result;
    }
    
    
    public ClassEntity getClassStudent(ClassStudentMapModel clssStuMap) {
    	return this.entityManager.find(ClassEntity.class, clssStuMap.getKey());
    }
    
    @Transactional
    public AemClassStudentMap saveClassStudent(ClassStudentMapModel model, AemClassStudentMap db) {
    	AemClassStudentMap aemClassStudentMap = new AemClassStudentMap();
    	AemClass aemClass = this.entityManager.find(AemClass.class, model.getClassModel().getKey());
    	AemStudentEntity studentEntity = this.entityManager.find(AemStudentEntity.class, model.getStudentModel().getKey());
    	aemClassStudentMap.setAemClass(aemClass);
    	aemClassStudentMap.setEamStudent(studentEntity);

		DatabaseUtil.setCommonFields(aemClassStudentMap, model); // change loginUserId when use
		entityManager.persist(aemClassStudentMap);
		return db;
    }
    
//    @Transactional
//    public AemStudentCourseMap saveStudentCourseMap(String studentId, ClassModel classModel, AemStudentCourseMap db) {
//
//        AemCourse course = entityManager.find(AemCourse.class, classModel.getCourseId());
//        db.setAemCourseS(course);
//        AemStudentEntity student = entityManager.find(AemStudentEntity.class, studentId);
//        db.setAemStudentC(student);
//
////        DatabaseUtil.setCommonFields(db, studentCourseMapModel); // change loginUserId when use
//        entityManager.persist(db);
//        return db;
//    }

    @Transactional
    public boolean deleteClassStudent(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    	CriteriaDelete<AemClassStudentMap> cd = cb.createCriteriaDelete(AemClassStudentMap.class);
    	Root<AemClassStudentMap> sample = cd.from(AemClassStudentMap.class);
    	
    	cd.where(cb.equal(sample.get("tableId"), id));
    	this.entityManager.createQuery(cd).executeUpdate();
    	
		return true;
    }      
    
}
