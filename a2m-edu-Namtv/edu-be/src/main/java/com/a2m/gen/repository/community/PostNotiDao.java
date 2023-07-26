package com.a2m.gen.repository.community;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.entities.community.PostAnswerEntity;
import com.a2m.gen.entities.community.PostEntity;
import com.a2m.gen.entities.community.PostNotiEntity;
import com.a2m.gen.entities.community.PostReplyEntity;
import com.a2m.gen.models.community.PostAnswerModel;
import com.a2m.gen.models.community.PostModel;
import com.a2m.gen.models.community.PostNotiModel;
import com.a2m.gen.models.community.PostReplyModel;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Transactional
@Repository
public class PostNotiDao{
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	PostDao postDao ;

	
	public PostNotiEntity savePostNoti(PostNotiModel model, PostNotiEntity db) {
		
		
		// get Post Entity
		Long  postId = model.getPostId();
		PostModel postModel = new PostModel();
		postModel.setKey(postId);
		PostEntity postEntity = postDao.getPost(postModel);
		
		// Set data to Entity
//		db.setPostNotiId(model.getKey());
    	db.setPostEntity(postEntity);
    	db.setUserUidNoti(model.getUserUid());
    	db.setChecked(false);
    	db.setDeleteFlag(false);
    	
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }
	
	public void delete(Long id) {
		PostNotiEntity entity = entityManager.find(PostNotiEntity.class, id);
		entityManager.remove(entity);
	}
	
	public int deleteByPostId(Long id) {
    	Query q = this.entityManager.createNativeQuery(
    			"DELETE FROM COMMUNITY_POST_NOTI "
    			+ "WHERE POST_ID = :postId "
    			);
    	q.setParameter("postId", id);
        return q.executeUpdate();
	}
	
	
}

