package com.a2m.gen.entities.tags;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "COMMON_TAGS")
public class TagEntity extends DatabaseCommonModel {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long tagId;

    @Column(name = "TAG_NAME")
    private String tagName;

    @Column(name = "TAG_COUNT")
    private Long tagCount;

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Long getTagCount() {
		return tagCount;
	}

	public void setTagCount(Long tagCount) {
		this.tagCount = tagCount;
	}
}
