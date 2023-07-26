package com.a2m.gen.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.edu.AemRoom;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class RoomDao {
	@Autowired 
	 private CommonService commonService;
	
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemRoom> getRooms(ParamBaseModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemRoom> cq = cb.createQuery(AemRoom.class);
        Root<AemRoom> room = cq.from(AemRoom.class);
        cq.orderBy(cb.desc(room.get("roomId")));
        cq.select(room);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
            predicates.add(cb.like(cb.lower(room.get("roomNm")), "%" + args.getSearchText().toLowerCase() + "%"));
//            predicates.add(cb.equal(room.get("roomNm"), args.getSearchText()));
        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end

		if (args.getRows() == null || args.getAll != null) {
			return entityManager.createQuery(cq).getResultList();
		}
        return entityManager.createQuery(cq)
                .setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
                .getResultList();
    }
    
    public Long countAllRoom(ParamBaseModel search){ 	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemRoom> root = criteriaQuery.from(AemRoom.class);
        criteriaQuery.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
            predicates.add(cb.like(cb.lower(root.get("roomNm")), "%" + search.getSearchText().toLowerCase() + "%"));
        }
        
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
    	return result;
    }
    
    public AemRoom getRoom(RoomModel roomModel) {
    	return this.entityManager.find(AemRoom.class, roomModel.getKey());
    }
    
    public AemRoom saveRoom(RoomModel model, AemRoom db) {
    	db.setRoomNm(model.getRoomNm());
    	db.setStatus(model.getStatus());
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }

	public Boolean roomForDel(List<RoomModel> listRoom) throws Exception {
		for(RoomModel room : listRoom) {
			Long id = room.getKey();
			deleteRoom(id);
		}
		return true;
	}

    public boolean deleteRoom(Long id) {
    	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
//    	
    	CriteriaDelete<AemRoom> cd = cb.createCriteriaDelete(AemRoom.class);
    	Root<AemRoom> sample = cd.from(AemRoom.class);
    	cd.where(cb.equal(sample.get("roomId"), id));
    	this.entityManager.createQuery(cd).executeUpdate();
    	
		return true;
    }
    
    
 
    
}
