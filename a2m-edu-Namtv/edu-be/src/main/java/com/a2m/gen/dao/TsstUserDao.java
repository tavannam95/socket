package com.a2m.gen.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemTeacherEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.models.edu.TeacherModel;

@Repository
public class TsstUserDao {
    @Autowired
    private EntityManager entityManager;
 
    public List<TsstUser> getTsstUserList(TsstUserModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TsstUser> cq = cb.createQuery(TsstUser.class);
        Root<TsstUser> tsstUser = cq.from(TsstUser.class);
        cq.select(tsstUser);
        
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
           predicates.add(cb.like(cb.lower(tsstUser.get("userId")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        if (args.getUserId() != null && !"".equals(args.getUserId())) {
        	predicates.add(cb.equal(tsstUser.get("userId"),  args.getUserId().toLowerCase()));
        }
        
        cq.where(predicates.toArray(new Predicate[0]));
        
        if( args.getGetAll() != null && args.getGetAll() ) {
        	return entityManager.createQuery(cq)
                    .getResultList();
        }
        
        return entityManager.createQuery(cq).getResultList();
    }
    public TsstUser getTsstUserByUserUid(String userUid) {
    	return this.entityManager.find(TsstUser.class, userUid);
    }
}
