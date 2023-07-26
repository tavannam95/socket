package com.a2m.gen.controllers.edu;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.RoomDao;
import com.a2m.gen.entities.edu.AemRoom;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.models.edu.Edu0103RequestModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.edu.Edu0103Service;

@RestController
@RequestMapping(value = "/api/edu/edu0103")
public class Edu0103Controller {
	 @Autowired 
	 private CommonService commonService;
	 
	 @Autowired 
	 private RoomDao roomDao;
	 
	 @Autowired 
	 private Edu0103Service edu0103Service;
  
    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody Edu0103RequestModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = edu0103Service.saveRoom(arg);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
//       
//	@DeleteMapping(value = "/delete/{id}")
//	public ResponseEntity<?> delete(@PathVariable("id") String userUid) {
//		try {
//			sys0103Service.delete(userUid);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ResponseEntity.ok(HttpStatus.OK);
//	}
	
	
	@GetMapping(value = "/search")
	public ResponseEntity<?> search(ParamBaseModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<RoomModel> lst = edu0103Service.getRooms(search);	
//		List<AemRoom> lst = roomDao.getRooms(search);
		Long totalItems = roomDao.countAllRoom(search);
		
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @PostMapping(value = "/deleteListCheck")
	@Transactional
    public ResponseEntity<?> deleteListCheck(@RequestBody List<RoomModel> listRoom) {
        Map<String, Object> response = new HashMap<>();
        try {
        	edu0103Service.roomForDel(listRoom);
        } catch (Exception e) {
//            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
//            response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
}
