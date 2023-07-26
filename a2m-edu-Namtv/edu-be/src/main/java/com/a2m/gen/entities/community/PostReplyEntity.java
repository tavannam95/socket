package com.a2m.gen.entities.community;

import javax.persistence.CascadeType;
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
import com.a2m.gen.entities.TsstUser;

@Entity
@Table(name = "COMMUNITY_POST_REPLY")
public class PostReplyEntity extends DatabaseCommonModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REPLY_ID")
	private Long answerId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANSWER_ID")
	private PostAnswerEntity postAnswerEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INS_UID", insertable = false, updatable =false)
	private TsstUser tsstUser;
	
	@Column(name = "REPLY_CONTENT")
	private String replyContent;

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public PostAnswerEntity getPostAnswerEntity() {
		return postAnswerEntity;
	}

	public void setPostAnswerEntity(PostAnswerEntity postAnswerEntity) {
		this.postAnswerEntity = postAnswerEntity;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public TsstUser getTsstUser() {
		return tsstUser;
	}

	public void setTsstUser(TsstUser tsstUser) {
		this.tsstUser = tsstUser;
	}
}
