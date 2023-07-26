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
import com.a2m.gen.entities.edu.AemStudentCourseMap;
import com.a2m.gen.models.edu.StudentCourseMapModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.services.common.CommonService;

@Repository
public class StudentCourseDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemStudentCourseMap> getStudentCoursesByStudentModel(StudentModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemStudentCourseMap> cq = cb.createQuery(AemStudentCourseMap.class);
        Root<AemStudentCourseMap> root = cq.from(AemStudentCourseMap.class);  
        cq.orderBy(cb.desc(root.get("tableId")));  
        cq.select(root);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();

        if (args.getKey() != null && !"".equals(args.getKey())) {
	        predicates.add(cb.equal(root.get("aemStudentC"), args.getKey()));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq).getResultList();  
    }
    
    
//    
//    public AemStudentCourseMap getClassStudent(StudentCourseMapModel model) {
//    	return this.entityManager.find(AemStudentCourseMap.class, model.getKey());
//    }
//    
    public Boolean delete(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    	CriteriaDelete<AemStudentCourseMap> cd = cb.createCriteriaDelete(AemStudentCourseMap.class);
    	Root<AemStudentCourseMap> root = cd.from(AemStudentCourseMap.class);
    	cd.where(cb.equal(root.get("tableId"), id));
    	this.entityManager.createQuery(cd).executeUpdate();
		return true;
    }
    
}
