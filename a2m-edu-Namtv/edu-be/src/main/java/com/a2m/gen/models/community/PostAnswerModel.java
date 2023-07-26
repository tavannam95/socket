package com.a2m.gen.models.community;

import java.util.ArrayList;
import java.util.List;

import com.a2m.gen.entities.community.PostAnswerEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.TsstUserInfoModel;
import com.a2m.gen.utils.ModelUtil;

public class PostAnswerModel extends ParamBaseModel {
	
	private Long postId;
	
	private String answerContent;
	
	private List<PostReplyModel> listReply = new ArrayList<PostReplyModel>();
	
	private TsstUserInfoModel userInfoCreate;
	
	public PostAnswerModel() {
		super();
		
	}

	public PostAnswerModel(PostAnswerEntity postEntity) {
		this.key = postEntity.getAnswerId();
		this.postId = postEntity.getPostEntity().getPostId();
		this.answerContent = postEntity.getAnswerContent();		
		ModelUtil.setCommonFields(postEntity, this);
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public List<PostReplyModel> getListReply() {
		return listReply;
	}

	public void setListReply(List<PostReplyModel> listReply) {
		this.listReply = listReply;
	}

	public TsstUserInfoModel getUserInfoCreate() {
		return userInfoCreate;
	}

	public void setUserInfoCreate(TsstUserInfoModel userInfoCreate) {
		this.userInfoCreate = userInfoCreate;
	}



	
	
}
