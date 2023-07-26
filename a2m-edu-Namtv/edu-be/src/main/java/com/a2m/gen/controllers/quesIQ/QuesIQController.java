package com.a2m.gen.controllers.quesIQ;

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
import com.a2m.gen.dao.IqQuesDao;
import com.a2m.gen.models.course.SubmitQuizModel;
import com.a2m.gen.models.iqTest.IqTestQuesModel;
import com.a2m.gen.services.iqTest.IqQuesService;

@RestController
@RequestMapping(value = "/api/quesIq/iqQues")
public class QuesIQController {

	@Autowired
	private IqQuesDao iqTestDao;

	@Autowired
	private IqQuesService iqTestQuesService;
	
//	@Autowired
//	private CommonService commonService;

	@PostMapping(value = "/save")
	@ResponseBody
	@Transactional(rollbackFor = { Exception.class })
	public Map<Object, Object> save(@RequestBody IqTestQuesModel model) throws Exception {
		Map<Object, Object> response = new HashMap<Object, Object>();
		try {
			response = iqTestQuesService.saveIqQues(model);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return response;
	}
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		try {
		    iqTestQuesService.delete(id);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
		}	
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<?> search(IqTestQuesModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<IqTestQuesModel> lst = iqTestQuesService.getIqQuesList(search);
			Long totalItems = iqTestDao.countIqTQues(search);

			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
			response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getIqQuesById/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getQuizById(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			IqTestQuesModel detail = iqTestQuesService.getIqQuesById(Id);
			response.put(CommonContants.DETAIL_KEY, detail);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
