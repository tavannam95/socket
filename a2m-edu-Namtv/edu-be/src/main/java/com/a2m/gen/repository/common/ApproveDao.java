package com.a2m.gen.repository.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.common.ApproveEntity;
import com.a2m.gen.models.common.ApproveModel;
import com.a2m.gen.utils.DatabaseUtil;


@Transactional
@Repository
public class ApproveDao{
	
	@PersistenceContext
	EntityManager entityManager;
	
    @SuppressWarnings("unlikely-arg-type")
	public List<ApproveEntity> search(ApproveModel search) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ApproveEntity> cq = cb.createQuery(ApproveEntity.class);
        Root<ApproveEntity> root = cq.from(ApproveEntity.class);  
        cq.orderBy(cb.desc(root.get("updDt")));
        cq.select(root);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        
        if (search.getEmpAprUid() != null && !"".equals(search.getEmpAprUid())) {
         	predicates.add( cb.equal(root.get("empAprUid"), search.getEmpAprUid()) );
        }
        
        if (search.getViewedAprHistory() != null && !"".equals(search.getViewedAprHistory())) {
         	predicates.add( cb.equal(root.get("viewedAprHistory"), search.getViewedAprHistory()) );
        }
        
        if (search.getInsUid() != null && !"".equals(search.getInsUid())) {
         	predicates.add( cb.equal(root.get("insUid"), search.getInsUid()) );
        }
        
        if (search.getApprovalStatus() != null && !"".equals(search.getApprovalStatus())) {
         	predicates.add( cb.equal(root.get("approvalStatus"), search.getApprovalStatus()) );
        }
        
//      predicates.add( cb.equal(root.get("approvalStatus"), CommonContants.APPROVAL_STATUS_APPROVAL) );
//    	predicates.add( cb.equal(root.get("approvalStatus"), CommonContants.APPROVAL_STATUS_REJECT) );
        
        if (search.getNoti() == true) {
        	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        	Predicate pre1
        	  = criteriaBuilder.equal(root.get("approvalStatus"), CommonContants.APPROVAL_STATUS_REJECT);
        	Predicate pre2
        	= criteriaBuilder.equal(root.get("approvalStatus"), CommonContants.APPROVAL_STATUS_APPROVAL);
        	
        	Predicate pre
        	  = criteriaBuilder.or(pre1, pre2);
        	predicates.add(pre);
        	
        }
        
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq).getResultList();  
    }
	
    public List<ApproveEntity> searchListPeding(Long uid, Boolean isAdmin) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ApproveEntity> cq = cb.createQuery(ApproveEntity.class);
        Root<ApproveEntity> root = cq.from(ApproveEntity.class);  
        cq.orderBy(cb.desc(root.get("updDt")));
        cq.select(root);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if(!isAdmin) {
        	predicates.add(cb.equal(root.get("empAprUid"), uid ));
        }
        
        predicates.add(cb.equal(root.get("approvalStatus"), CommonContants.APPROVAL_STATUS_PENDING ));
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq).getResultList();  
    }
    
    public ApproveEntity deleteApproval(Long id) {
    	ApproveEntity db = this.entityManager.find(ApproveEntity.class, id);
        entityManager.remove(db);
        return db;
    }    
    

	public List<HashMap<String, Object>> getListApprover() {
			List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

			
			Query q = 
			entityManager.createNativeQuery(
					  "SELECT u.USER_UID "// 0
					  + ", IF(u.USER_TYPE = 'STU', stu.FULL_NAME, IF(u.USER_TYPE = 'TEA', tea.FULL_NAME, info.FULL_NAME)) " // 1
					  + ", IF(u.USER_TYPE = 'STU', stu.IMG_PATH, IF(u.USER_TYPE = 'TEA', tea.IMG_PATH, info.IMG_PATH)) " // 2

				  + "FROM TSST_USER_ROLE r "
				  + "LEFT JOIN TSST_USER u ON u.USER_UID = r.USER_UID "
				  + "LEFT JOIN TSST_USER_INFO info ON u.USER_INFO_ID = info.USER_INFO_ID "
				  + "LEFT JOIN EAM_STUDENT_INFO stu ON u.USER_INFO_ID = stu.STUDENT_INFO_ID "
				  + "LEFT JOIN EAM_TEACHER_INFO tea ON u.USER_INFO_ID = tea.TEACHER_INFO_ID "
				  + "WHERE r.ROLE_ID = 'R015' "					
					);
			List<Object[]> resultList = q.getResultList();
		
			for (Object[] objects : resultList) {				
	            String uid = (String) objects[0];
	            String fullName = (String) objects[1];
	            String imgPath = (String) objects[2];
	            HashMap<String, Object> map = new HashMap<String, Object>();
	            map.put("userUid", uid);
	            map.put("fullName", fullName);
	            map.put("imgPath", imgPath);            
	            
	            result.add(map);
			}
			
			return result ;
	 
	}
	
	public ApproveEntity saveApprove(ApproveModel model, ApproveEntity db) {
		
    	db.setApprovalStatus(model.getApprovalStatus());
    	db.setRefId(model.getRefId());
    	db.setRefTable(model.getRefTable());
    	db.setDocumentUrl(model.getDocumentUrl());
    	db.setFeedback(model.getFeedBack());
    	db.setViewedAprHistory(model.getViewedAprHistory());
    	db.setEmpAprUid(model.getEmpAprUid());
    	
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }
	
	public ApproveEntity getApprove(Long key) {
		return entityManager.find(ApproveEntity.class, key);
	}
	
	public int setApprovalHistoryId(ApproveModel model) {
		Query q = 
				entityManager.createNativeQuery(
						
					   "UPDATE " + model.getRefTable() + " u "
					  + "SET u.APPROVAL_HISTORY_ID = " + model.getKey() + " "
					  + "WHERE u.SUBJECT_ID = "	+ model.getRefId()			
						);
		return q.executeUpdate();
	}
}

