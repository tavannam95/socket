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

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.AemTeacherEntity;
import com.a2m.gen.entities.edu.ClassEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.models.edu.TeacherModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class TeacherDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemTeacherEntity> get(ParamBaseModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemTeacherEntity> cq = cb.createQuery(AemTeacherEntity.class);
        Root<AemTeacherEntity> teacherEntity = cq.from(AemTeacherEntity.class);  
        cq.orderBy(cb.desc(teacherEntity.get("teacherInfoId")));  
        cq.select(teacherEntity);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
           predicates.add(cb.like(cb.lower(teacherEntity.get("fullName")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(room.get("roomNm"), args.getSearchText()));
        }
        predicates.add(cb.equal(teacherEntity.get("deleteFlag"), false));
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
    
    public Long count(ParamBaseModel search){ 	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemTeacherEntity> root = criteriaQuery.from(AemTeacherEntity.class);
        criteriaQuery.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
            predicates.add(cb.like(cb.lower( root.get("fullName")), "%" + search.getSearchText().toLowerCase() + "%"));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
    	return result;
    }
    
    
    public AemTeacherEntity getTeacher(TeacherModel teacherModel) {
    	return this.entityManager.find(AemTeacherEntity.class, teacherModel.getKey());
    }
//    
//    public AemClass saveClass(ClassModel model, AemClass db) {
//    	db.setClassNm(model.getClassName());
//    	db.setClassType(model.getClassType());
//    	db.setStatus(model.getClassStatus());
//    	
//		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
//		entityManager.persist(db);
//		return db;
//    }
//
    public boolean deleteTeacher(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    	CriteriaDelete<AemTeacherEntity> cd = cb.createCriteriaDelete(AemTeacherEntity.class);
    	Root<AemTeacherEntity> sample = cd.from(AemTeacherEntity.class);
    	
    	cd.where(cb.equal(sample.get("studentInfoId"), id));
    	this.entityManager.createQuery(cd).executeUpdate();
		return true;
    }
//    
    
 
    
}
