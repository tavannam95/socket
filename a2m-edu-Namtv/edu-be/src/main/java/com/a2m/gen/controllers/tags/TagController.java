package com.a2m.gen.controllers.tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.tags.TagModel;
import com.a2m.gen.services.tags.TagService;

@RestController
@RequestMapping(value = "api/public/tags")
public class TagController {

	@Autowired
	private TagService service;
	
	@GetMapping(value = "/search")
	public ResponseEntity<?> search(TagModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<TagModel> lst = service.searchTag(search);
			//Long totalItems = service.countAllLecture(search);

			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
			//response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchUser")
	public ResponseEntity<?> searchUser(ParamBaseModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<HashMap<String, String>> lst = service.searchUser(search);

			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
			//response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
