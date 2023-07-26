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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.IqTestDao;
import com.a2m.gen.models.iqTest.IqTestModel;
import com.a2m.gen.models.iqTest.NonUserModel;
import com.a2m.gen.models.iqTest.SubmitQuesModel;
import com.a2m.gen.services.iqTest.IqTestService;
import com.a2m.gen.utils.CastUtil;

@RestController
@RequestMapping(value = "/api/quesIq/iqTest")
public class IQTestController {

	@Autowired
	private IqTestDao iqTestDao;

	@Autowired
	private IqTestService iqTestService;
	
//	@Autowired
//	private CommonService commonService;

	@PostMapping(value = "/save")
	@ResponseBody
	@Transactional(rollbackFor = { Exception.class })
	public Map<Object, Object> save(@RequestBody(required = false) IqTestModel model) throws Exception {
		Map<Object, Object> response = new HashMap<Object, Object>();
		try {
			response = iqTestService.saveIqTest(model);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return response;
	}
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		try {
		    iqTestService.delete(id);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
		}	
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<?> search(IqTestModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<IqTestModel> lst = iqTestService.getIqTestList(search);
			Long totalItems = iqTestDao.countAllIqTest(search);

			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
			response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getIqTestById/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getIqTestById(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			IqTestModel detail = iqTestService.getIqTestById(Id);
			response.put(CommonContants.DETAIL_KEY, detail);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @PostMapping(value = "/submit")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> submit(@RequestBody SubmitQuesModel submitRequest) throws Exception {
        Map<Object, Object> response = new HashMap<Object, Object>();
        try {
            response = iqTestService.submit(submitRequest);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return response;
    }

    @GetMapping(value = "/getQuesSubmit")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getQuesSubmit(@RequestParam HashMap<String, Long> parameter) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long iqTestId = CastUtil.castToLong(parameter.get("iqTestId"));
//            Long userInfoId = CastUtil.castToLong(parameter.get("userInfoId"));
            Object detail = iqTestService.getIqTestSubmit(iqTestId);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getListStuSubtmited")
    public ResponseEntity<?> getListStuSubtmited(@RequestParam HashMap<String, Object> parameter) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {

//            Long iqtestId = CastUtil.castToLong(parameter.get("iqtestId"));
//            Object lst = iqTestService.getListStuSubtmited(parameter);
            Object lst = iqTestService.getListStuSubtmited(parameter);
            Long totalItems = iqTestDao.countStuSubtmited(parameter);
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
            response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
            response.put(CommonContants.LIST_KEY, lst);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/saveUser")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> saveUser(@RequestBody(required = false) NonUserModel model) throws Exception {
        Map<Object, Object> response = new HashMap<Object, Object>();
        try {
            response = iqTestService.saveUser(model);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return response;
    }
    
    @GetMapping(value = "/getListQuestionStatistical")
    public ResponseEntity<?> getListQuestionStatistical(@RequestParam HashMap<String, Object> parameter) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
            Object lst = iqTestService.getListQuestionStatistical(parameter);
            Long totalItems = iqTestDao.countQuestionStatistical(parameter);
            response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
            response.put(CommonContants.LIST_KEY, lst);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
