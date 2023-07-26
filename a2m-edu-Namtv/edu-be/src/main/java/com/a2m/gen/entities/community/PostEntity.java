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
import com.a2m.gen.entities.edu.AemClassStudentMap;

@Entity
@Table(name = "COMMUNITY_POST")
public class PostEntity extends DatabaseCommonModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POST_ID")
	private Long postId;
	
	@Column(name = "POST_TITLE")
	private String postTitle;
	
	@Column(name = "POST_CONTENT")
	private String postContent;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "CLASS_ID")
	private Long classId;
	
	@Column(name = "REFERENCE_TYPE")
	private String referenceType;
	
	@Column(name = "REFERENCE_ID")
	private Long referenceId;
	
	@Column(name = "STATUS")
	private Boolean status;
	
	@Column(name = "TAGS")
	private String tags;
	
	@OneToMany(mappedBy = "postEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PostAnswerEntity> listAnswer = new ArrayList<PostAnswerEntity>();
	
	@OneToMany(mappedBy = "postEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PostNotiEntity> listPostNoti = new ArrayList<PostNotiEntity>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INS_UID", insertable = false, updatable =false)
	private TsstUser tsstUser;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
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

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
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

	public List<PostAnswerEntity> getListAnswer() {
		return listAnswer;
	}

	public void setListAnswer(List<PostAnswerEntity> listAnswer) {
		this.listAnswer = listAnswer;
	}

	public TsstUser getTsstUser() {
		return tsstUser;
	}

	public void setTsstUser(TsstUser tsstUser) {
		this.tsstUser = tsstUser;
	}

	public List<PostNotiEntity> getListPostNoti() {
		return listPostNoti;
	}

	public void setListPostNoti(List<PostNotiEntity> listPostNoti) {
		this.listPostNoti = listPostNoti;
	}
}
