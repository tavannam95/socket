package com.a2m.gen.models.community;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.community.PostEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.TsstUserInfoModel;
import com.a2m.gen.utils.ModelUtil;

public class PostModel extends ParamSearchModel {
	
	private String postTitle;
	
	private String postContent;
	
	private String type;
	
	private Long classId;
	
	private String referenceType;
	
	private Long referenceId;
	
	private Boolean status;
	
	private String tags;
	
	private List<PostAnswerModel> listAnswer = new ArrayList<PostAnswerModel>();
	
	private List<PostNotiModel> listNotiModel = new ArrayList<PostNotiModel>();
	
	private TsstUserInfoModel userInfoCreate;
	
	private Long countAnswer;
	
	private Long countReply;
	
	public PostModel() {
		super();
		
	}

	public PostModel(PostEntity postEntity) {
		this.key = postEntity.getPostId();
		this.postTitle = postEntity.getPostTitle();
		this.postContent = postEntity.getPostContent();
		this.referenceType = postEntity.getReferenceType();
		this.referenceId = postEntity.getReferenceId();
		this.status = postEntity.getStatus();
		this.tags = postEntity.getTags();
		this.type = postEntity.getType();
		this.classId = postEntity.getClassId();
		
		ModelUtil.setCommonFields(postEntity, this);
	}

	public PostModel(
			Long postId, String postTitle, String postContent, String referenceType,
			Long referenceId, Boolean status, String tags,
			String insUid, Date insDate, String updUid, Date updDate,
			Boolean deleteFlag, String fullName, String imgPath
		) {
		
		this.key = postId;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.referenceType = referenceType;
		this.referenceId = referenceId;
		this.status = status;
		this.tags = tags;
		
		this.insUid = insUid;
		this.insDate = insDate;
		this.updUid = updUid;
		this.updDate = updDate;
		
		this.userInfoCreate = new TsstUserInfoModel(fullName, imgPath);
		
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<PostAnswerModel> getListAnswer() {
		return listAnswer;
	}

	public void setListAnswer(List<PostAnswerModel> listAnswer) {
		this.listAnswer = listAnswer;
	}

	public TsstUserInfoModel getUserInfoCreate() {
		return userInfoCreate;
	}

	public void setUserInfoCreate(TsstUserInfoModel userInfoCreate) {
		this.userInfoCreate = userInfoCreate;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public Long getCountAnswer() {
		return countAnswer;
	}

	public void setCountAnswer(Long countAnswer) {
		this.countAnswer = countAnswer;
	}

	public Long getCountReply() {
		return countReply;
	}

	public void setCountReply(Long countReply) {
		this.countReply = countReply;
	}

	public List<PostNotiModel> getListNotiModel() {
		return listNotiModel;
	}

	public void setListNotiModel(List<PostNotiModel> listNotiModel) {
		this.listNotiModel = listNotiModel;
	}


}
