package com.a2m.gen.repository.community;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.entities.community.PostAnswerEntity;
import com.a2m.gen.entities.community.PostEntity;
import com.a2m.gen.entities.community.PostNotiEntity;
import com.a2m.gen.entities.community.PostReplyEntity;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.community.PostAnswerModel;
import com.a2m.gen.models.community.PostModel;
import com.a2m.gen.models.community.PostNotiModel;
import com.a2m.gen.models.community.PostReplyModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Transactional
@Repository
public class PostDao{
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	CommonService commonService;
	
    public List<PostNotiEntity> searchNoti(PostNotiModel args) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PostNotiEntity> cq = cb.createQuery(PostNotiEntity.class);
        Root<PostNotiEntity> root = cq.from(PostNotiEntity.class);
        cq.orderBy(cb.desc(root.get("postNotiId")));
        cq.select(root);

//        where start
        List<Predicate> predicates = new ArrayList<>();
       
        if (args.getUserUid() != null && !"".equals(args.getUserUid())) {
        	predicates.add(cb.equal(root.get("userUidNoti"), args.getUserUid()));
        }
        
        cq.where(predicates.toArray(new Predicate[0]));


        return entityManager.createQuery(cq).getResultList();
    }
    
    public int checkedNoti(PostNotiModel model) {
    	Query q = this.entityManager.createNativeQuery(
    			"UPDATE COMMUNITY_POST_NOTI a "
    			+ "SET a.CHECKED = true "
    			+ "WHERE a.POST_NOTI_ID = :key "
    			);
    	q.setParameter("key", model.getKey());
        return q.executeUpdate();
    }
	
	public PostEntity savePost(PostModel model, PostEntity db) {
		
    	db.setPostTitle(model.getPostTitle());
    	db.setPostContent(model.getPostContent());
    	db.setReferenceType(model.getReferenceType());
    	db.setReferenceId(model.getReferenceId());
    	db.setStatus(model.getStatus());
    	db.setTags(model.getTags());
    	db.setClassId(model.getClassId());
    	db.setType(model.getType());
    	db.setDeleteFlag(false);
    	
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
    }
	
	public PostAnswerEntity saveAnswer(PostAnswerModel model, PostAnswerEntity db) {
		PostModel postModel = new PostModel();
		postModel.setKey(model.getPostId());
		PostEntity post = getPost(postModel);
		
		db.setPostEntity(post);
    	db.setAnswerContent(model.getAnswerContent());
    	db.setDeleteFlag(false);
    	
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		
		return db;
    }
	
	public PostReplyEntity saveReply(PostReplyModel model, PostReplyEntity db) {
		PostAnswerModel answerModel = new PostAnswerModel();
		answerModel.setKey(model.getAnswerId());
		PostAnswerEntity postAnswer = getAnswer(answerModel);
		
		db.setPostAnswerEntity(postAnswer);
    	db.setReplyContent(model.getReplyContent());
    	db.setDeleteFlag(false);
    	
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		
		return db;
    }
	
	public PostEntity getPost(PostModel model) {
		return entityManager.find(PostEntity.class, model.getKey());
	}
	
	public PostEntity getPost(Long key) {
		return entityManager.find(PostEntity.class, key);
	}
	
	public PostAnswerEntity getAnswer(PostAnswerModel model) {
		return entityManager.find(PostAnswerEntity.class, model.getKey());
	}
	
	public PostReplyEntity getReply(PostReplyModel model) {
		return entityManager.find(PostReplyEntity.class, model.getKey());
	}
	
	public void deleteReply(String id) {
		Long longId = CastUtil.castToLong(id);
		PostReplyEntity entity = entityManager.find(PostReplyEntity.class, longId);
		entityManager.remove(entity);
	}
	
	public void deleteAnswer(String id) {
		Long longId = CastUtil.castToLong(id);
		PostAnswerEntity entity = entityManager.find(PostAnswerEntity.class, longId);
		entityManager.remove(entity);
	}

	
	public List<PostModel> searchPost(PostModel postModel) throws ParseException {
		String startTimeSearch = "";
		String endTimeSearch = "";
		if(postModel.getStartTime()!=null) {
			startTimeSearch = "AND post.INS_DT >= '"+postModel.getStartTime().toString()+ "'";
		}
		if(postModel.getEndTime()!=null) {
			endTimeSearch = "AND post.INS_DT <= '"+postModel.getEndTime().toString()+ "'";
		}
		
		
		List<PostModel> result = new ArrayList<PostModel>();
		String searchText = postModel.getSearchText() != null?postModel.getSearchText() : "";
		
		String type  = postModel.getType() != null?postModel.getType() : "";
		String classId  = postModel.getClassId() != null?postModel.getClassId().toString() : "";
		
		String typeSearch = "";
		if(!type.equals("")) {
			typeSearch = " AND post.TYPE = '"+type+"'";
		}
		
		String classIdSearch = "";
		if(!classId.equals("")) {
			classIdSearch = " AND post.CLASS_ID = '"+classId+"'";
		}
		
		String limit = "";
		limit = postModel.getRows()!=null ?"LIMIT " + postModel.getRows() + " " : "" ;
		 String ofset = "";
		ofset = postModel.getRows()!=null ?"OFFSET " + postModel.getRows() * postModel.getPage() : "" ;
		
		String score = ",MATCH(post.POST_TITLE, post.POST_CONTENT) AGAINST('"+searchText+"') AS score ";
		String order = " ORDER BY post.INS_DT DESC ";
		String fullTextSearch = "AND "
				+ "("
				+ "MATCH(post.POST_TITLE, post.POST_CONTENT) AGAINST('"+searchText+"') "
				+ "OR LOWER(post.TAGS) LIKE LOWER('%"+searchText+"%') "
				+ ")";
		if(searchText.equals("")) {
			fullTextSearch = " ";
			score = " ";
			order = " ORDER BY post.POST_ID DESC ";
		}
		String referType = postModel.getReferenceType() != null ? postModel.getReferenceType() : "";
		Long referId = postModel.getReferenceId() != null ? postModel.getReferenceId() : 0;
		String courseSearch = " ";
		if(!referType.equals("") && referId >0) {
			courseSearch = "AND (post.REFERENCE_TYPE = '"+referType+"' AND "
					  + "post.REFERENCE_ID = "+referId+") ";
		}
		
		Query q = 
		entityManager.createNativeQuery(
				  "SELECT post.POST_ID "
				  + ", post.POST_TITLE "
				  + ", post.POST_CONTENT "
				  + ", post.REFERENCE_TYPE "
				  + ", post.REFERENCE_ID "
				  + ", post.STATUS "
				  + ", post.TAGS "
				  + ", post.INS_UID "
				  + ", post.INS_DT "
				  + ", post.UPD_UID "
				  + ", post.UPD_DT "
				  + ", post.DELETE_FLAG "
				  + ", IF(user.USER_TYPE = 'STU', stuInfo.FULL_NAME, IF(user.USER_TYPE = 'TEA', teaInfo.FULL_NAME, userInfo.FULL_NAME)) "
				  + ", IF(user.USER_TYPE = 'STU', stuInfo.IMG_PATH, IF(user.USER_TYPE = 'TEA', teaInfo.IMG_PATH, userInfo.IMG_PATH)) "
				  + score

			  + "FROM COMMUNITY_POST post "
			  + "LEFT JOIN TSST_USER user ON post.INS_UID = user.USER_UID "
			  + "LEFT JOIN TSST_USER_INFO userInfo ON user.USER_INFO_ID = userInfo.USER_INFO_ID "
			  + "LEFT JOIN EAM_STUDENT_INFO stuInfo ON user.USER_INFO_ID = stuInfo.STUDENT_INFO_ID "
			  + "LEFT JOIN EAM_TEACHER_INFO teaInfo ON user.USER_INFO_ID = teaInfo.TEACHER_INFO_ID "
			  + "WHERE 1 = 1 "
			  + courseSearch
			  +typeSearch
			  +classIdSearch
			  //+ "WHERE MATCH(post.POST_TITLE) AGAINST('"+postModel.getSearchText()+"') "
			  + fullTextSearch
			  +startTimeSearch
			  +endTimeSearch
			  +order
//			  + "LIMIT " + postModel.getRows() + " "
//			  + "OFFSET " + postModel.getRows() * postModel.getPage());
			  +limit
			  +ofset
			  );
		List<Object[]> posts = q.getResultList();
	
		for (Object[] objects : posts) {
			//Object[] objects = (Object[]) object;
			
			BigInteger postId = (BigInteger) objects[PostEnum.POST_ID.getValue()];
			String postTitle = (String) objects[PostEnum.POST_TITLE.getValue()];
			String postContent = (String) objects[PostEnum.POST_CONTENT.getValue()];
			String referenceType = (String) objects[PostEnum.REFERENCE_TYPE.getValue()];
			BigInteger referenceId = (BigInteger) objects[PostEnum.REFERENCE_ID.getValue()];
			Boolean status = (Boolean) objects[PostEnum.STATUS.getValue()];
			String tags = (String) objects[PostEnum.TAGS.getValue()];
			
			String insUid = (String) objects[PostEnum.INS_UID.getValue()];
			Date insDate = (Date) objects[PostEnum.INS_DT.getValue()];
			String updUid = (String) objects[PostEnum.UPD_UID.getValue()];
			Date updDate = (Date) objects[PostEnum.UPD_DT.getValue()];
			Boolean deleteFlag = (Boolean) objects[PostEnum.DELETE_FLAG.getValue()];
			
			String fullName = (String) objects[PostEnum.FULL_NAME.getValue()];
			String imgPath = (String) objects[PostEnum.IMG_PATH.getValue()];
			
			Long refId = referenceId!=null?referenceId.longValue():0;
			
			PostModel returnModel = new PostModel(
					postId.longValue() , postTitle, postContent, referenceType, refId, status, tags,
					insUid, insDate, updUid, updDate, deleteFlag, fullName, imgPath
			);
			
			Map<String, Object> countAnswerAndRep = countAnswerAndReplyForPost(returnModel);
			returnModel.setCountAnswer( CastUtil.castToLong(countAnswerAndRep.get("countAnswer")) );
			returnModel.setCountReply( CastUtil.castToLong(countAnswerAndRep.get("countReply")) );
			result.add(returnModel);
		}
		
		return result;
	}
	
	public Map<String, Object> countAnswerAndReplyForPost(PostModel postModel){
		Map<String, Object> result = new HashedMap<>();
		
		
		Query q = 
				entityManager.createNativeQuery(
						"SELECT COUNT( DISTINCT (an.ANSWER_ID)) AS countAnswer "
								+ ", COUNT(re.REPLY_ID) AS countReply "
								
					  + "FROM COMMUNITY_POST post "
					  + "LEFT JOIN COMMUNITY_POST_ANSWER an ON post.POST_ID = an.POST_ID "
					  + "LEFT JOIN COMMUNITY_POST_REPLY re ON an.ANSWER_ID = re.ANSWER_ID "
					  + "WHERE post.POST_ID = " + postModel.getKey());
		
		List<Object[]> resultList = q.getResultList();
		
		for (Object[] objects : resultList) {
			//Object[] objects = (Object[]) object;
			
			BigInteger countAnswer = (BigInteger) objects[0];
			BigInteger countReply = (BigInteger) objects[1];
			
			result.put("countAnswer", countAnswer.longValue());
			result.put("countReply", countReply.longValue());
		}
		
		return result;
	}
	
	public Long countPost(PostModel postModel) {
		List<PostModel> result = new ArrayList<PostModel>();
		
		String startTimeSearch = "";
		String endTimeSearch = "";
		if(postModel.getStartTime()!=null) {
			startTimeSearch = "AND post.INS_DT >= '"+postModel.getStartTime().toString()+ "'";
		}
		if(postModel.getEndTime()!=null) {
			endTimeSearch = "AND post.INS_DT <= '"+postModel.getEndTime().toString()+ "'";
		}
		
		String searchText = postModel.getSearchText() != null?postModel.getSearchText() : "";
		String fullTextSearch = "AND "
				+ "("
				+ "MATCH(post.POST_TITLE, post.POST_CONTENT) AGAINST('"+searchText+"') "
				+ "OR LOWER(post.TAGS) LIKE LOWER('%"+searchText+"%') "
				+ ")";
		if(searchText.equals("")) {
			fullTextSearch = " ";
		}
		
		String type  = postModel.getType() != null?postModel.getType() : "";
		String classId  = postModel.getClassId() != null?postModel.getClassId().toString() : "";
		
		String typeSearch = "";
		if(!type.equals("")) {
			typeSearch = " AND post.TYPE = '"+type+"'";
		}
		
		String classIdSearch = "";
		if(!classId.equals("")) {
			classIdSearch = " AND post.CLASS_ID = '"+classId+"'";
		}
		
		String referType = postModel.getReferenceType() != null ? postModel.getReferenceType() : "";
		Long referId = postModel.getReferenceId() != null ? postModel.getReferenceId() : 0;
		String courseSearch = " ";
		if(!referType.equals("") && referId >0) {
			courseSearch = "AND (post.REFERENCE_TYPE = '"+referType+"' AND "
					  + "post.REFERENCE_ID = "+referId+") ";
		}
		
		Query q = 
		entityManager.createNativeQuery(
				"SELECT COUNT(*) "	

			  + "FROM COMMUNITY_POST post "
			  + "LEFT JOIN TSST_USER user ON post.INS_UID = user.USER_UID "
			  + "LEFT JOIN TSST_USER_INFO userInfo ON user.USER_INFO_ID = userInfo.USER_INFO_ID "
			  + "LEFT JOIN EAM_STUDENT_INFO stuInfo ON user.USER_INFO_ID = stuInfo.STUDENT_INFO_ID "
			  + "LEFT JOIN EAM_TEACHER_INFO teaInfo ON user.USER_INFO_ID = teaInfo.TEACHER_INFO_ID "
			  + "WHERE 1 = 1 "
			  + courseSearch
			  +typeSearch
			  +classIdSearch
			  //+ "WHERE MATCH(post.POST_TITLE) AGAINST('"+postModel.getSearchText()+"') "
			  + fullTextSearch
			  +startTimeSearch
			  +endTimeSearch
			  + "ORDER BY post.INS_DT DESC "
//			  + "LIMIT " + postModel.getRows() + " "
//	  		  + "OFFSET " + postModel.getRows() * postModel.getPage()				
				);
		Object singleResult = new ArrayList<>();
		
		singleResult = q.getSingleResult();
		return ((BigInteger) singleResult) .longValue();
		
	}
	
	public enum PostEnum {
		POST_ID(0), 
		POST_TITLE(1), 
		POST_CONTENT(2),
		REFERENCE_TYPE(3), 
		REFERENCE_ID(4), 
		STATUS(5),
		TAGS(6), 
		INS_UID(7), 
		INS_DT(8),
		UPD_UID(9), 
		UPD_DT(10), 
		DELETE_FLAG(11),
		FULL_NAME(12),
		IMG_PATH(13),
		;
		

		private int value;

		PostEnum(int id) {
			this.value = id;
		}

		public int getValue() {
			return value;
		}
	}
}

