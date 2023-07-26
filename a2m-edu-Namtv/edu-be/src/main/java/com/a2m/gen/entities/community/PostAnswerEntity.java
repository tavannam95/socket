package com.a2m.gen.entities.community;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.entities.TsstUser;

@Entity
@Table(name = "COMMUNITY_POST_ANSWER")
public class PostAnswerEntity extends DatabaseCommonModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ANSWER_ID")
	private Long answerId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POST_ID")
	private PostEntity postEntity;
	
	@Column(name = "ANSWER_CONTENT")
	private String answerContent;
	
	@OneToMany(mappedBy = "postAnswerEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PostReplyEntity> listReply = new ArrayList<PostReplyEntity>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INS_UID", insertable = false, updatable =false)
	private TsstUser tsstUser;

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public PostEntity getPostEntity() {
		return postEntity;
	}

	public void setPostEntity(PostEntity postEntity) {
		this.postEntity = postEntity;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public List<PostReplyEntity> getListReply() {
		return listReply;
	}

	public void setListReply(List<PostReplyEntity> listReply) {
		this.listReply = listReply;
	}

	public TsstUser getTsstUser() {
		return tsstUser;
	}

	public void setTsstUser(TsstUser tsstUser) {
		this.tsstUser = tsstUser;
	}


	
}
