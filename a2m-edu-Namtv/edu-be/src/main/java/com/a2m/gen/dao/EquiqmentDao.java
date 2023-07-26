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

import com.a2m.gen.entities.edu.Equipment;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.Edu0104RequestModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class EquiqmentDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<Equipment> getEquiqment (ParamBaseModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Equipment> cq = cb.createQuery(Equipment.class);
        Root<Equipment> equiqment = cq.from(Equipment.class);  
        cq.orderBy(cb.desc(equiqment.get("equipmentId")));  
        cq.select(equiqment);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
           predicates.add(cb.like(cb.lower(equiqment.get("equiqmentName")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(room.get("roomNm"), args.getSearchText()));
        }
        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
	        predicates.add(cb.equal(equiqment.get("equiqmentStatus"), args.getSearchStatus()));
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
    
    public Long counEquiqment(ParamBaseModel search){ 	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Equipment> root = criteriaQuery.from(Equipment.class);
        criteriaQuery.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
            predicates.add(cb.like(cb.lower(root.get("equiqmentName")), "%" + search.getSearchText().toLowerCase() + "%"));
        }
        if (search.getSearchStatus() != null && !"".equals(search.getSearchStatus())) {
	        predicates.add(cb.equal(root.get("equiqmentStatus"), search.getSearchStatus()));
        }
        
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
    	return result;
    }
    
    public Equipment getEquiqment(Edu0104RequestModel equiqmentModel) {
    	return this.entityManager.find(Equipment.class, equiqmentModel.getKey());
    }
    
    public Equipment saveEquiqment(Edu0104RequestModel model, Equipment db) {
    	db.setEquiqmentName(model.getEquiqmentName());
    	db.setEquiqmentCD(model.getEquiqmentCD());
    	db.setEquiqmentStatus(model.getEquiqmentStatus());
    	db.setEquiqmentType(model.getEquiqmentType());
    	
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }

	public Boolean equiqmentForDel(List<Edu0104RequestModel> listEquiqment) throws Exception {
		for(Edu0104RequestModel equiqment : listEquiqment) {
			Long id = equiqment.getKey();
			deleteEquiqment(id);
		}
		return true;
	}

    public boolean deleteEquiqment(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    	CriteriaDelete<Equipment> cd = cb.createCriteriaDelete(Equipment.class);
    	Root<Equipment> sample = cd.from(Equipment.class);
    	
    	cd.where(cb.equal(sample.get("equipmentId"), id));
    	this.entityManager.createQuery(cd).executeUpdate();
		return true;
    }
    
    
 
    
}
