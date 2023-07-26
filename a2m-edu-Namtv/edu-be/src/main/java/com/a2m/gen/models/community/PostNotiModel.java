package com.a2m.gen.models.community;

import com.a2m.gen.entities.community.PostNotiEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.utils.ModelUtil;

public class PostNotiModel extends ParamBaseModel {
	
	private Long postId;
	private Boolean checked;
	private String userUid;
	private String sender;
	private String postTitle;
	
	public PostNotiModel() {
		super();
		
	}
	
	public PostNotiModel(PostNotiEntity db) {
		super();
		this.key = db.getPostNotiId();
		this.postId = db.getPostEntity().getPostId();
		this.checked =  db.getChecked();
		this.userUid = db.getUserUidNoti();
		ModelUtil.setCommonFields(db, this);
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
}
