package com.a2m.gen.controllers.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.common.ApproveModel;
import com.a2m.gen.models.community.PostModel;
import com.a2m.gen.models.course.LectureModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.services.common.ApproveService;

@RestController
@RequestMapping(value = "/api/approve")
public class ApproveController {

	@Autowired
	private ApproveService service;
	
    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody ApproveModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = service.saveApprove(arg);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
    @PostMapping(value = "/cancelSubmit")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> cancelSubmit(@RequestBody ApproveModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = service.cancelSubmit(arg);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
    @GetMapping(value = "/getApprovalById/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getLectureById(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
        	ApproveModel detail = service.getApprove(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping(value = "/getListApprover")
	public ResponseEntity<?> getListApprover() throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<HashMap<String, Object>> lst = service.getListApprover();
			//Long totalItems = service.countPost(search);

			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK); 
			response.put(CommonContants.LIST_KEY, lst);
			//response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
    @GetMapping(value = "/searchListPeding/{uid}/{isAdmin}")
    public ResponseEntity<?> searchListPeding(@PathVariable("uid") Long uid, @PathVariable("isAdmin") Boolean isAdmin) {
        Map<String, Object> response = new HashMap<>();
        try {
        	List<ApproveModel> list = service.searchListPeding(uid, isAdmin);
        	response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
            response.put(CommonContants.LIST_KEY, list);
        } catch (Exception e) {
        	response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
	@GetMapping(value = "/search")
	public ResponseEntity<?> search(ApproveModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<ApproveModel> list = service.search(search);	
    	response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        response.put(CommonContants.LIST_KEY, list);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
}
