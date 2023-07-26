package com.a2m.gen.entities.community;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "COMMUNITY_POST_NOTI")
public class PostNotiEntity extends DatabaseCommonModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POST_NOTI_ID")
	private Long postNotiId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POST_ID")
	private PostEntity postEntity;
	
	@Column(name = "USER_UID_NOTI")
	private String userUidNoti;
	
	@Column(name = "CHECKED")
	private Boolean checked;

	public Long getPostNotiId() {
		return postNotiId;
	}

	public void setPostNotiId(Long postNotiId) {
		this.postNotiId = postNotiId;
	}

	public String getUserUidNoti() {
		return userUidNoti;
	}

	public void setUserUidNoti(String userUidNoti) {
		this.userUidNoti = userUidNoti;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public PostEntity getPostEntity() {
		return postEntity;
	}

	public void setPostEntity(PostEntity postEntity) {
		this.postEntity = postEntity;
	}
	
	
}
