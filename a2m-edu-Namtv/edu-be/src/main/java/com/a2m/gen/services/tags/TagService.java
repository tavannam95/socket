package com.a2m.gen.services.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.entities.tags.TagEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.tags.TagModel;
import com.a2m.gen.repository.tags.TagDao;
import com.a2m.gen.services.common.CommonService;
import com.google.gson.Gson;

@Service
public class TagService {
	
	@Autowired 
	private CommonService commonService;
	
	@Autowired
	private TagDao dao;

	@Transactional
	public List<TagModel> searchTag(TagModel search) {
		List<TagModel> result = new ArrayList<TagModel>();
		List<TagEntity> list = dao.findTagByTagName(search);
		
		for (TagEntity tagEntity : list) {
			TagModel model = new TagModel(tagEntity);
			result.add(model);
		}
		handleTag(null);
		return result;
	}
	
	@Transactional
	public List<HashMap<String, String>> searchUser(ParamBaseModel search) {
		return dao.searchUser(search);
	}
	
	@Transactional
	public void handleTag(String tags){
		if(tags == null) return;
		Gson gson = new Gson();
		String[] arr = gson.fromJson(tags, String[].class);
		
		for (String tagName : arr) {
			List<TagEntity> listTag = searchTagByName(tagName);
			if(listTag.size()>0) {
				countTagPlusOne(listTag.get(0));
			}else {
				insertTag(tagName);
			}
		}
	}
	
	@Transactional
	public void countTagPlusOne(TagEntity entity) {
		Long count = entity.getTagCount();
		entity.setTagCount(++count);
		dao.persist(entity);
	}
	
	@Transactional
	public void insertTag(String tagName) {
		TagModel model = new TagModel();
		model.setTagCount(Long.valueOf(1));
		model.setTagName(tagName);
		TagEntity entity = new TagEntity();
		dao.insertTag(model, entity);
	}
	
	@Transactional
	public List<TagEntity> searchTagByName(String tagName){
		TagModel search = new TagModel();
		search.setTagName(tagName);
		List<TagEntity> list = dao.findTagByTagName(search);
		return list;
	}
	
}
