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

import com.a2m.gen.entities.edu.AemClassTeacherMap;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.ClassEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.ClassStudentMapModel;
import com.a2m.gen.models.edu.ClassTeacherMapModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class ClassTeacherMapDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<ClassEntity> get(ParamBaseModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClassEntity> cq = cb.createQuery(ClassEntity.class);
        Root<ClassEntity> classEntity = cq.from(ClassEntity.class);  
        cq.orderBy(cb.desc(classEntity.get("classId")));  
        cq.select(classEntity);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
           predicates.add(cb.like(classEntity.get("className"), "%" + args.getSearchText() + "%"));
//            predicates.add(cb.equal(room.get("roomNm"), args.getSearchText()));
        }
        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
	        predicates.add(cb.equal(classEntity.get("classStatus"), args.getSearchStatus()));
        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
//        		=== start paging
        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();
    }
    
    public List<AemClassTeacherMap> get(ClassModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemClassTeacherMap> cq = cb.createQuery(AemClassTeacherMap.class);
        Root<AemClassTeacherMap> clssStuEntity = cq.from(AemClassTeacherMap.class);  
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
    
    
    public AemClassTeacherMap saveClassTeacher(ClassTeacherMapModel model, AemClassTeacherMap db) {
    	db.setAemClassT(model.getAemClass());
    	db.setEamTeacher(model.getEamTeacherEntity());

		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }

    public boolean deleteClassTeacher(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    	CriteriaDelete<AemClassTeacherMap> cd = cb.createCriteriaDelete(AemClassTeacherMap.class);
    	Root<AemClassTeacherMap> sample = cd.from(AemClassTeacherMap.class);
    	
    	cd.where(cb.equal(sample.get("tableId"), id));
    	this.entityManager.createQuery(cd).executeUpdate();
		return true;
    }
    
    
 
    
}
