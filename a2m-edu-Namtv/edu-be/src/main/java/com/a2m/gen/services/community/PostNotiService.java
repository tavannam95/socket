package com.a2m.gen.services.community;

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
import com.a2m.gen.repository.community.PostNotiDao;
import com.a2m.gen.repository.community.PostRepository;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.tags.TagService;
import com.a2m.gen.utils.CastUtil;

@Service
public class PostNotiService {
	
	@Autowired
	private PostNotiDao dao;
	
	@Autowired 
	private CommonService commonService;

	
	public PostNotiModel save(PostNotiModel model) throws Exception {
		String userUid = commonService.getUserUid();
		model.setInsUid(userUid);
		
		PostNotiEntity entity = new PostNotiEntity();
		PostNotiEntity resultEntity = dao.savePostNoti(model, entity);
		PostNotiModel result = new PostNotiModel(resultEntity);
		return result;
	}
	
	
	public void deleteById (Long id ) throws Exception {
		dao.delete(id);
	}
	
	public int deleteByPostId (Long id ) throws Exception {
		return dao.deleteByPostId(id);
	}

    

   
}
