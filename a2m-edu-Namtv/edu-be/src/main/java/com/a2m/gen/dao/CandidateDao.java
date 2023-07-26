package com.a2m.gen.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.edu.AemCandidate;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemEvent;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.edu.CandidateModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class CandidateDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemCandidate> search (CandidateModel args) throws Exception {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemCandidate> cq = cb.createQuery(AemCandidate.class);
        Root<AemCandidate> candidate = cq.from(AemCandidate.class);  
        cq.orderBy(cb.desc(candidate.get("candidateId")));  
        cq.select(candidate).distinct(true);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
           predicates.add(cb.like(cb.lower(candidate.get("candidateName")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        if (args.getCourseId() != null && !"".equals(args.getCourseId())) {
            predicates.add(cb.equal(cb.lower(candidate.get("candiDateCourse")), args.getCourseId()));
         }
        if (args.getEventId() != null && !"".equals(args.getEventId())) {
            predicates.add(cb.equal(cb.lower(candidate.get("candiDateEvent").get("eventId")), args.getEventId()));
         }
        if(args.getProgressType() != null && !"".equals(args.getProgressType())){
        	
        	if (args.getProgressType().equals("Waiting")) {
        		predicates.add(cb.notEqual(candidate.get("candidateprogressType"), "Close"));
        		predicates.add(cb.notEqual(candidate.get("candidateprogressType"), "Confirm"));
        	}
        	else {
        		predicates.add(cb.equal(candidate.get("candidateprogressType"), args.getProgressType()));
        	}
        }
        if (args.getStartTime() != null && !"".equals(args.getStartTime())) {
        	
            predicates.add(cb.greaterThanOrEqualTo(candidate.<Date>get("insDt"), stringToDate(args.getStartTime(),false)));
         }
        if (args.getEndTime() != null && !"".equals(args.getEndTime())) {
        	predicates.add(cb.lessThanOrEqualTo(candidate.<Date>get("insDt"), stringToDate(args.getEndTime(),true)));
        }


//        	predicates.add(cb.notEqual(candidate.get("candidateprogressType"), "Close"));
        
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        if(args.getAll!=null && args.getAll==true) {
        	return entityManager.createQuery(cq).getResultList();
        }
        return entityManager.createQuery(cq)
//        		=== start paging
        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
                .getResultList();
    }
    
    public Long counCandidate(CandidateModel search) throws Exception{ 	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemCandidate> root = criteriaQuery.from(AemCandidate.class);
        criteriaQuery.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
            predicates.add(cb.like(cb.lower(root.get("candidateName")), "%" + search.getSearchText().toLowerCase() + "%"));
        }
        if (search.getCourseId() != null && !"".equals(search.getCourseId())) {
            predicates.add(cb.equal(cb.lower(root.get("candiDateCourse")), search.getCourseId()));
         }
        if (search.getEventId() != null && !"".equals(search.getEventId())) {
            predicates.add(cb.equal(cb.lower(root.get("candiDateEvent").get("eventId")), search.getEventId()));
         }
         if(search.getProgressType() != null && !"".equals(search.getProgressType())){
         	
         	if (search.getProgressType().equals("Waiting")) {
         		predicates.add(cb.notEqual(root.get("candidateprogressType"), "Close"));
         		predicates.add(cb.notEqual(root.get("candidateprogressType"), "Confirm"));
         	}
         	else {
         		predicates.add(cb.equal(root.get("candidateprogressType"), search.getProgressType()));
         	}
         }
         if (search.getStartTime() != null && !"".equals(search.getStartTime())) {
         	
             predicates.add(cb.greaterThanOrEqualTo(root.<Date>get("insDt"), stringToDate(search.getStartTime(),false)));
          }
         if (search.getEndTime() != null && !"".equals(search.getEndTime())) {
         	predicates.add(cb.lessThanOrEqualTo(root.<Date>get("insDt"), stringToDate(search.getEndTime(),true)));
         }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
    	return result;
    }
    
    public AemCandidate getCandidate(CandidateModel candidateModel) {
    	return this.entityManager.find(AemCandidate.class, candidateModel.getKey());
    }
    
    public AemCandidate saveCandidate(CandidateModel model, AemCandidate db) {
    	if(!"".equals(CastUtil.castToString(model.getCourseModel().getKey()))) {
	    	AemCourse courseEntity = this.entityManager.find(AemCourse.class, model.getCourseModel().getKey());
	    	db.setCandiDateCourse(courseEntity);
    	}else {
    		db.setCandiDateCourse(null);
    	}
    	if(!"".equals(CastUtil.castToString(model.getEventModel().getKey()))) {
    		AemEvent eventEntity = this.entityManager.find(AemEvent.class, model.getEventModel().getKey());
	    	db.setCandiDateEvent(eventEntity);
    	}else {
    		db.setCandiDateEvent(null);
    	}
    	db.setAllParamFormModel(model);
    	db.setInsDt(model.getInsDate());
    	db.setUpDt(model.getUpdDate());
    	db.setCandidateprogressType(model.getCandidateprogressType());
//		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }

    public boolean deleteCandidate(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    	CriteriaDelete<AemCandidate> cd = cb.createCriteriaDelete(AemCandidate.class);
    	Root<AemCandidate> sample = cd.from(AemCandidate.class);
    	
    	cd.where(cb.equal(sample.get("candidateId"), id));
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
