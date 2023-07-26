package com.a2m.gen.controllers.community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.models.community.PostAnswerModel;
import com.a2m.gen.models.community.PostModel;
import com.a2m.gen.models.community.PostNotiModel;
import com.a2m.gen.models.community.PostReplyModel;
import com.a2m.gen.services.community.PostService;

@RestController
@RequestMapping(value = "/api/community/post")
public class PostController {

	@Autowired
	private PostService service;
	
	@GetMapping(value = "/searchPost")
	public ResponseEntity<?> searchPost(PostModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<PostModel> lst = service.searchPost(search);
			Long totalItems = service.countPost(search);

			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
			response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchNoti")
	public ResponseEntity<?> searchNoti(PostNotiModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<PostNotiModel> lst = service.searchNoti(search);

			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/checkedNoti")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> checkedNoti(@RequestBody PostNotiModel model) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = service.checkedNoti(model);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
	
	@GetMapping(value = "/getByClassId")
	public ResponseEntity<?> getListClassPostByClassId(PostModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<PostModel> lst = service.getByClassId(search);	
//			Long count = edu0201Service.getCountInProgress(search);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
//			response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
    @PostMapping(value = "/savePost")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody PostModel model) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = service.savePost(model);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
    @GetMapping(value = "/getPostById/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getPostById(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
            PostModel detail = service.getPostById(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getPostOnlyById/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getPostExluAnswerById(@PathVariable("id") Long Id) {
    	Map<String, Object> response = new HashMap<>();
    	try {
    		PostModel detail = service.getPostExluAnswerById(Id);
    		response.put(CommonContants.DETAIL_KEY, detail);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping(value = "/saveAnswer")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> saveAnswer(@RequestBody PostAnswerModel model) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = service.saveAnswer(model);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
    @PostMapping(value = "/saveReply")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> saveReply(@RequestBody PostReplyModel model) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = service.saveReply(model);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
    @DeleteMapping(value = "/deleteReply/{id}")
	public ResponseEntity<?> deleteReply(@PathVariable("id") String id) {
    	Map<Object, Object> res = new HashMap<Object, Object>();
		try {
			service.deleteReply(id);
		} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}	
		return ResponseEntity.ok(HttpStatus.OK);
	}
    
    @DeleteMapping(value = "/deleteAnswer/{id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable("id") String id) {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		service.deleteAnswer(id);
    	} catch (Exception e) {
    		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
    		res.put(CommonContants.MESSAGES_KEY, e.toString());
    	}	
    	return ResponseEntity.ok(HttpStatus.OK);
    }
    
    
}
