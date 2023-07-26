package com.a2m.gen.controllers.edu;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.Equipment;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.Edu0103RequestModel;
import com.a2m.gen.models.edu.Edu0104RequestModel;
import com.a2m.gen.models.edu.EventModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.repository.edu.Edu0101Respository;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.edu.Edu0101Service;
import com.a2m.gen.services.edu.Edu0104Service;
import com.a2m.gen.services.edu.Edu0203Service;

@RestController
@RequestMapping(value = "/api/edu/edu0203")
public class Edu0203Controller {
	
	@Autowired
	private Edu0203Service edu0203Service;

	@Autowired
	private Edu0101Respository edu0101Respository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CommonService commonService;

    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody EventModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = edu0203Service.save(arg);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
//
	@DeleteMapping(value = "/delete/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> delete(@PathVariable("id") Long Id) {
		try {
			edu0203Service.delete(Id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

    @PostMapping(value = "/deleteListCheck")
	@Transactional
    public ResponseEntity<?> deleteListCheck(@RequestBody List<EventModel> listEvent) {
        Map<String, Object> response = new HashMap<>();
        try {
        	edu0203Service.deleteList(listEvent);
        } catch (Exception e) {
//            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
//            response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
	@GetMapping(value = "/getById/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getById(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			EventModel eventModel = edu0203Service.getbyId(Id);
			response.put(CommonContants.RESULT_KEY, eventModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<?> search(ParamBaseModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<EventModel> lst = edu0203Service.get(search);	
		Long count = edu0203Service.getCount(search);
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
