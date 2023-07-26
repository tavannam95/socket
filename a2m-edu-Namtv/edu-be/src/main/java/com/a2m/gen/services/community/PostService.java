package com.a2m.gen.services.community;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.community.PostAnswerEntity;
import com.a2m.gen.entities.community.PostEntity;
import com.a2m.gen.entities.community.PostNotiEntity;
import com.a2m.gen.entities.community.PostReplyEntity;
import com.a2m.gen.models.TsstUserInfoModel;
import com.a2m.gen.models.community.PostAnswerModel;
import com.a2m.gen.models.community.PostModel;
import com.a2m.gen.models.community.PostNotiModel;
import com.a2m.gen.models.community.PostReplyModel;
import com.a2m.gen.repository.community.PostDao;
import com.a2m.gen.repository.community.PostRepository;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.UserInfoService;
import com.a2m.gen.services.tags.TagService;
import com.a2m.gen.utils.CastUtil;

@Service
public class PostService {
	
	@Autowired 
	private CommonService commonService;
	
	@Autowired
	private PostDao dao;
	
	@Autowired
	private PostRepository repo;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private PostNotiService notiService;
	
	@Autowired
	private UserInfoService userInfoService;

	@Transactional
	public List<PostModel> searchPost(PostModel search) {
		List<PostModel> listPostModel = new ArrayList<PostModel>();
		
		try {
			listPostModel = dao.searchPost(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listPostModel;
	}
	
	@Transactional
	public List<PostNotiModel> searchNoti(PostNotiModel search) {
		List<PostNotiModel> listPostModel = new ArrayList<PostNotiModel>();
		
		try {
			List<PostNotiEntity> lstEntity = dao.searchNoti(search);
			for (PostNotiEntity postNotiEntity : lstEntity) {
				PostNotiModel model = new PostNotiModel(postNotiEntity);
				
				//add reporter
				Map<String, Object> senderInfo = userInfoService.getUserInfo2(postNotiEntity.getInsUid());
				model.setSender( CastUtil.castToString(senderInfo.get("fullName")));
				//end add reporter
				
				//add title
				PostEntity post = dao.getPost(model.getPostId());
				model.setPostTitle(post.getPostTitle());
				
				listPostModel.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listPostModel;
	}
	
	public List<PostModel> getByClassId(PostModel search) throws ParseException{
		List<PostModel> resultList = dao.searchPost(search);
		return resultList;
	}
	
	public  Long countPost(PostModel search) {
		Long result = dao.countPost(search);
		return result;
	}

	private void setListAnswer(PostModel postModel, PostEntity postEntity) {		
		List<PostAnswerModel> listAnswer = new ArrayList<PostAnswerModel>();
		
		List<PostAnswerEntity> listAnswerEntity = postEntity.getListAnswer();
		for (PostAnswerEntity postAnswerEntity : listAnswerEntity) {
			PostAnswerModel answer = new PostAnswerModel(postAnswerEntity);
			
				TsstUser user = postAnswerEntity.getTsstUser();
				TsstUserInfoModel userInfo = new TsstUserInfoModel(user);
				answer.setUserInfoCreate(userInfo);
			
			setListReply(answer, postAnswerEntity);			
			listAnswer.add(answer);
		}
		postModel.setListAnswer(listAnswer);
	}
	
	private void setListReply(PostAnswerModel answerModel, PostAnswerEntity answerEntity) {
		List<PostReplyModel> listReply = new ArrayList<PostReplyModel>();
		
		List<PostReplyEntity> listReplyEntity = answerEntity.getListReply();
		for (PostReplyEntity postReplyEntity : listReplyEntity) {
			PostReplyModel reply = new PostReplyModel(postReplyEntity);
			
				TsstUser user = postReplyEntity.getTsstUser();
				TsstUserInfoModel userInfo = new TsstUserInfoModel(user);
				reply.setUserInfoCreate(userInfo);
			
			listReply.add(reply);
		}
		answerModel.setListReply(listReply);
	}
	
	@Transactional
	public Map<Object, Object> savePost(PostModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		List<PostNotiModel> listNoti = model.getListNotiModel();
		
		PostEntity db = new PostEntity();
		if(!"".equals(CastUtil.castToString(model.getKey()))) {
			//update
			db = dao.getPost(model);
			model.setUpdUid(userUid);
			tagService.handleTag(model.getTags());
			db = dao.savePost(model, db);
			//remove old list Noti
			notiService.deleteByPostId(db.getPostId());
			//Add new list Noti
			saveListNoti(listNoti,db.getPostId());
		}else {
			//insert
			model.setInsUid(userUid);
			tagService.handleTag(model.getTags());
			db = dao.savePost(model, db);
			//save list noti 
			saveListNoti(listNoti,db.getPostId());
		}
		res.put(CommonContants.KEY, db.getPostId());
		return res;
	}
	
	public List<PostNotiModel> saveListNoti(List<PostNotiModel> listNoti,Long postId) throws Exception {
		List<PostNotiModel> resultList = new ArrayList<>();
		for (PostNotiModel postNotiModel : listNoti) {
			postNotiModel.setPostId(postId);
			PostNotiModel notiResultModel = notiService.save(postNotiModel);
			resultList.add(notiResultModel);
		}
		return resultList;
	}
	
	public void removeListNoti(List<PostNotiEntity> listPostNoti)throws Exception{
		for (PostNotiEntity postNotiEntity : listPostNoti) {
			notiService.deleteById(postNotiEntity.getPostNotiId());
		}
	}
	
    public PostModel getPostById (Long Id) {
        PostModel postModel = new PostModel();
        postModel.setKey(Id);
        PostEntity postEntity =  dao.getPost(postModel);
        
        postModel = new PostModel(postEntity);
			TsstUser user = postEntity.getTsstUser();
			TsstUserInfoModel userInfo = new TsstUserInfoModel(user);
			postModel.setUserInfoCreate(userInfo);
		
		setListAnswer(postModel, postEntity);
        return postModel;
        
    }
    
    public PostModel getPostExluAnswerById (Long Id) {
        PostModel postModel = new PostModel();
        postModel.setKey(Id);
        PostEntity postEntity =  dao.getPost(postModel);
        
        postModel = new PostModel(postEntity);
			TsstUser user = postEntity.getTsstUser();
			TsstUserInfoModel userInfo = new TsstUserInfoModel(user);
			postModel.setUserInfoCreate(userInfo);
		
		//setListAnswer(postModel, postEntity);
			
		//noti tag
		List<PostNotiEntity> listPostNoti = postEntity.getListPostNoti();
		for (PostNotiEntity postNotiEntity : listPostNoti) {
			PostNotiModel noti = new PostNotiModel(postNotiEntity);
			postModel.getListNotiModel().add(noti);
		}
        return postModel;
        
    }
    
	@Transactional
	public Map<Object, Object> saveAnswer(PostAnswerModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		PostAnswerEntity db = new PostAnswerEntity();
		if(!"".equals(CastUtil.castToString(model.getKey()))) {
			//update
			model.setUpdUid(userUid);
			db = dao.getAnswer(model);
			db = dao.saveAnswer(model, db);
		}else {
			//insert
			model.setInsUid(userUid);
			db = dao.saveAnswer(model, db);
			
		}
		
		PostAnswerModel answer = new PostAnswerModel(db);		
			TsstUser user = commonService.findByUserUid(db.getInsUid());
			TsstUserInfoModel userInfo = new TsstUserInfoModel(user);
			answer.setUserInfoCreate(userInfo);
		res.put(CommonContants.DETAIL_KEY, answer);
		return res;
	}
	
	@Transactional
	public Map<Object, Object> saveReply(PostReplyModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		PostReplyEntity db = new PostReplyEntity();
		if(!"".equals(CastUtil.castToString(model.getKey()))) {
			//update
			model.setUpdUid(userUid);
			db = dao.getReply(model);
			db = dao.saveReply(model, db);
		}else {
			//insert
			model.setInsUid(userUid);
			db = dao.saveReply(model, db);
			
		}
		
		PostReplyModel reply = new PostReplyModel(db);		
			TsstUser user = commonService.findByUserUid(db.getInsUid());
			TsstUserInfoModel userInfo = new TsstUserInfoModel(user);
			reply.setUserInfoCreate(userInfo);
		res.put(CommonContants.DETAIL_KEY, reply);
		return res;
	}
	
	@Transactional
	public Map<Object, Object> checkedNoti(PostNotiModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);

		int id = dao.checkedNoti(model);
		
		res.put(CommonContants.DETAIL_KEY, id);
		return res;
	}
	
	@Transactional
	public Map<Object, Object> deleteReply(String id) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		
		dao.deleteReply(id);
		return res;
	}
	
	@Transactional
	public Map<Object, Object> deleteAnswer(String id) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		
		dao.deleteAnswer(id);
		return res;
	}
}
