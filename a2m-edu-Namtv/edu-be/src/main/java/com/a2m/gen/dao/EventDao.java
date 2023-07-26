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

import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemEvent;
import com.a2m.gen.entities.edu.Equipment;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.Edu0104RequestModel;
import com.a2m.gen.models.edu.EventModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class EventDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemEvent> getList (ParamBaseModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemEvent> cq = cb.createQuery(AemEvent.class);
        Root<AemEvent> root = cq.from(AemEvent.class);  
        cq.orderBy(cb.desc(root.get("eventId")));  
        cq.select(root);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
           predicates.add(cb.like(cb.lower(root.get("eventTitle")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(room.get("roomNm"), args.getSearchText()));
        }
        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
	        predicates.add(cb.equal(root.get("eventStatus"), args.getSearchStatus()));
        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end

        if (args.getGetAll() != null && args.getGetAll()) {
            return entityManager.createQuery(cq).getResultList();
        }

        if(args.getRows()==null) {
        	return entityManager.createQuery(cq).getResultList();
        }else {
        	return entityManager.createQuery(cq)
//        		=== start paging
        			.setMaxResults(CastUtil.castToInteger(args.getRows()))
        			.setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
        			.getResultList();
		}
    }
    
    public Long count(ParamBaseModel search){ 	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemEvent> root = criteriaQuery.from(AemEvent.class);
        criteriaQuery.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
            predicates.add(cb.like(cb.lower(root.get("eventTitle")), "%" + search.getSearchText().toLowerCase() + "%"));
        }
        if (search.getSearchStatus() != null && !"".equals(search.getSearchStatus())) {
	        predicates.add(cb.equal(root.get("eventStatus"), search.getSearchStatus()));
        }
        
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
    	return result;
    }
//    
    public AemEvent getByModel(EventModel equiqmentModel) {
    	return this.entityManager.find(AemEvent.class, equiqmentModel.getKey());
    }
//    
    public AemEvent save(EventModel model, AemEvent db) {
    	db.setEventTitle(model.getEventTitle());
    	db.setEventContent(model.getEventContent());
    	db.setEventStatus(model.getStatus());
    	db.setFilePath(model.getFilePath());
    	AemCourse courseEntity = this.entityManager.find(AemCourse.class, model.getCourseModel().getKey());
    	db.setEventCourse(courseEntity);
		entityManager.persist(db);
		return db;
    }
//
    public boolean delete(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    	CriteriaDelete<AemEvent> cd = cb.createCriteriaDelete(AemEvent.class);
    	Root<AemEvent> sample = cd.from(AemEvent.class);
    	
    	cd.where(cb.equal(sample.get("eventId"), id));
    	this.entityManager.createQuery(cd).executeUpdate();
		return true;
    }


    
 
    
}
