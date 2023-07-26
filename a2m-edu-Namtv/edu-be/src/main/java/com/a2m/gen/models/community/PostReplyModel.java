package com.a2m.gen.models.community;

import com.a2m.gen.entities.community.PostReplyEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.TsstUserInfoModel;
import com.a2m.gen.utils.ModelUtil;

public class PostReplyModel extends ParamBaseModel {
	
	private Long answerId;
	
	private String replyContent;

	private TsstUserInfoModel userInfoCreate;
	
	public PostReplyModel() {
		super();
		
	}

	public PostReplyModel(PostReplyEntity entity) {
		this.key = entity.getAnswerId();
		this.answerId = entity.getPostAnswerEntity().getAnswerId();
		this.replyContent = entity.getReplyContent();		
		ModelUtil.setCommonFields(entity, this);
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public TsstUserInfoModel getUserInfoCreate() {
		return userInfoCreate;
	}

	public void setUserInfoCreate(TsstUserInfoModel userInfoCreate) {
		this.userInfoCreate = userInfoCreate;
	}



	
	
}
