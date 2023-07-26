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

import com.a2m.gen.entities.iqTest.AemIqTestMap;
import com.a2m.gen.models.iqTest.IqTestModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;

@Repository
public class IqTestQuesMapDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
    private EntityManager entityManager;
 
    public List<AemIqTestMap> getCoursSubjectByCourseModel(IqTestModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemIqTestMap> cq = cb.createQuery(AemIqTestMap.class);
        Root<AemIqTestMap> csm = cq.from(AemIqTestMap.class);  
        cq.orderBy(cb.desc(csm.get("tableId")));  
        cq.select(csm);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();

        if (args.getKey() != null && !"".equals(args.getKey())) {
	        predicates.add(cb.equal(csm.get("aemIqTest"), args.getKey()));
        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
                .getResultList();  
    }
    
    public List<AemIqTestMap> get(IqTestModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemIqTestMap> cq = cb.createQuery(AemIqTestMap.class);
        Root<AemIqTestMap> csm = cq.from(AemIqTestMap.class);  
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
    
//     @Transactional
//     public AemIqTestMap saveCourseSubject(IqTestQuesMapModel model, AemIqTestMap db) {
//     	AemIqTestMap AemIqTestMap = new AemIqTestMap();
//     	AemIqTestMap.setAemCouSubject(model.getAemCourseSubject());
//         AemIqTestMap.setAemCourse(model.getAemCourse());
// 		DatabaseUtil.setCommonFields(AemIqTestMap, model); // change loginUserId when use
// 		entityManager.persist(AemIqTestMap);
// 		return db;
//     }

    @Transactional
    public boolean delete(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    	CriteriaDelete<AemIqTestMap> cd = cb.createCriteriaDelete(AemIqTestMap.class);
    	Root<AemIqTestMap> sample = cd.from(AemIqTestMap.class);
    	
    	cd.where(cb.equal(sample.get("tableId"), id));
    	this.entityManager.createQuery(cd).executeUpdate();
    	
		return true;
    }
    
    public List<AemIqTestMap> getListCouSubMapBySubId(String subjectId) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemIqTestMap> cq = cb.createQuery(AemIqTestMap.class);
        Root<AemIqTestMap> csm = cq.from(AemIqTestMap.class);  
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
    
}
