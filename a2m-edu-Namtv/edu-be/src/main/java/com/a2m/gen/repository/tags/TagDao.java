package com.a2m.gen.repository.tags;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.entities.tags.TagEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.community.PostModel;
import com.a2m.gen.models.tags.TagModel;
import com.a2m.gen.repository.community.PostDao.PostEnum;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Transactional
@Repository
public class TagDao{
	
	@PersistenceContext
	EntityManager entityManager;

	
	public TagEntity insertTag(TagModel model, TagEntity db) {
		
    	db.setTagName(model.getTagName());
    	db.setTagCount(Long.valueOf(1));
    	db.setDeleteFlag(false);
    	
		DatabaseUtil.setCommonFields(db, model); 
		entityManager.persist(db);
		return db;
    }
	
	public TagEntity persist(TagEntity db) { 
		entityManager.persist(db);
		return db;
    }
	
	public TagEntity getTag(TagModel model) {
		return entityManager.find(TagEntity.class, model.getKey());
	}
	
    public List<TagEntity> findTagByTagName(TagModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> cq = cb.createQuery(TagEntity.class);
        Root<TagEntity> root = cq.from(TagEntity.class); 
        cq.select(root);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();

        if (args.getTagName() != null && !"".equals(args.getTagName())) {
	        predicates.add(cb.equal(root.get("tagName"), args.getTagName()));
        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
                .getResultList();  
    }
    
    public List<HashMap<String, String>> searchUser(ParamBaseModel model) {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		String searchText = model.getSearchText() != null?model.getSearchText() : "";
		
		String search = "AND "
				+ "( "
				+ "LOWER(stuInfo.FULL_NAME) LIKE LOWER('%"+searchText+"%') "
				+ "OR LOWER(teaInfo.FULL_NAME) LIKE LOWER('%"+searchText+"%') "
				+ "OR LOWER(userInfo.FULL_NAME) LIKE LOWER('%"+searchText+"%') "
				+ ") ";
		if(searchText.equals("") || searchText == null) {
			search = " ";
		}
		
		Query q = 
		entityManager.createNativeQuery(
				  "SELECT user.USER_UID " //0
				  + ", user.USER_TYPE " //1
				  + ", IF(user.USER_TYPE = 'STU', stuInfo.FULL_NAME, IF(user.USER_TYPE = 'TEA', teaInfo.FULL_NAME, userInfo.FULL_NAME)) " //2
				  + ", IF(user.USER_TYPE = 'STU', stuInfo.IMG_PATH, IF(user.USER_TYPE = 'TEA', teaInfo.IMG_PATH, userInfo.IMG_PATH)) " //3
				  + ", GROUP_CONCAT(role.ROLE_ID) " //4

			  + "FROM TSST_USER user "
			  + "LEFT JOIN TSST_USER_INFO userInfo ON user.USER_INFO_ID = userInfo.USER_INFO_ID "
			  + "LEFT JOIN EAM_STUDENT_INFO stuInfo ON user.USER_INFO_ID = stuInfo.STUDENT_INFO_ID "
			  + "LEFT JOIN EAM_TEACHER_INFO teaInfo ON user.USER_INFO_ID = teaInfo.TEACHER_INFO_ID "
			  + "LEFT JOIN TSST_USER_ROLE role ON user.USER_UID = role.USER_UID "
			  + "WHERE user.`STATUS` = TRUE "
			  + search
			  + "GROUP BY user.USER_UID "
			  );
		List<Object[]> resultSet = q.getResultList();
	
		for (Object[] objects : resultSet) {
			String userUid = (String) objects[0];
			String userType = (String) objects[1]!=null?(String) objects[1]:"";
			String fullName = (String) objects[2]!=null?(String) objects[2]:"";
			String imgPath = (String) objects[3]!=null?(String) objects[3]:"";
			String roles = (String) objects[4]!=null?(String) objects[4]:"";
			
			Map map = new HashMap<String, String>();
			map.put("userUid", userUid);
			map.put("userType", userType);
			map.put("fullName", fullName);
			map.put("imgPath", imgPath);
			map.put("roles", roles);
			result.add((HashMap<String, String>) map);
		
		}
		
		return result;
	}
}

