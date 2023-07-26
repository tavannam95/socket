package com.a2m.gen.models.tags;

import com.a2m.gen.entities.tags.TagEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.utils.ModelUtil;

public class TagModel extends ParamBaseModel {
	private String tagName;
	private Long tagCount;
	

	
	public TagModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public TagModel(TagEntity entity) {
		
		this.key = entity.getTagId();
		this.tagName = entity.getTagName();
		this.tagCount = entity.getTagCount();
		
		ModelUtil.setCommonFields(entity, this);
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
