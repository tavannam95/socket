package com.a2m.gen.controllers.edu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.models.edu.ClassAnnouncementLogModel;
import com.a2m.gen.models.edu.ClassAnnouncementModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.edu.Edu020103Service;

@RestController
@RequestMapping(value = "/api/edu/edu020103")
public class Edu020103Controller {
	
	
	@Autowired
	private Edu020103Service edu020103Service;

	@Autowired
	private CommonService commonService;

    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody ClassAnnouncementModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = edu020103Service.save(arg);
    	} catch (Exception e) {
    		e.printStackTrace();
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
    @PostMapping(value = "/log")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> log(@RequestBody ClassAnnouncementLogModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = edu020103Service.log(arg);
    	} catch (Exception e) {
    		e.printStackTrace();
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
    @PostMapping(value = "/search")
	public ResponseEntity<?> search(@RequestBody ClassAnnouncementModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<ClassAnnouncementModel> lst = edu020103Service.getListByClassId(search);
		Long count = edu020103Service.count(search);
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
	@DeleteMapping(value = "/delete/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> delete(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			edu020103Service.delete(Id);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	
}


