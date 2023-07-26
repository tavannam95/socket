package com.a2m.gen.controllers.edu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.edu.CandidateModel;
import com.a2m.gen.repository.edu.Edu0101Respository;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.edu.CandidateService;
import com.a2m.gen.services.edu.Edu0102Service;

@RestController
@RequestMapping(value = "/api/edu/edu0202")
public class Edu0202Controller {
	
	
	@Autowired
	private CandidateService candidateService;

	@Autowired
	private Edu0102Service edu0102Service;
	
	@Autowired
	private Edu0101Respository edu0101Respository;

	@Autowired
	private CommonService commonService;

    
	@GetMapping(value = "/getCandidateById/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getClassById(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			 CandidateModel lst = candidateService.getCandidateModelById(Id);
			response.put(CommonContants.LIST_KEY, lst);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @PostMapping(value = "/deleteListCheck")
	@Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> deleteListCheck(@RequestBody List<CandidateModel> listCandidate) {
        Map<String, Object> response = new HashMap<>();
        try {
            candidateService.candidateForDel(listCandidate);
        } catch (Exception e) {
//            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
//            response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
	@DeleteMapping(value = "/delete/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> delete(@PathVariable("id") Long Id) {
		try {
			candidateService.delete(Id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody CandidateModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = candidateService.saveCandidate(arg);
    	} catch (Exception e) {
    		e.printStackTrace();
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }

	@GetMapping(value = "/search")
	public ResponseEntity<?> search(CandidateModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
				
		List<CandidateModel> lst = candidateService.get(search);	
		Long count = candidateService.getCount(search);
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getCountCandidateAllType")
	public ResponseEntity<?> getCountCandidateAllType() throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			
			String count = candidateService.getCountCandidateAllType();
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
    @PostMapping(value = "/exportExcel")
    public ResponseEntity<?> exportPDF(@RequestBody CandidateModel search) {
        Map<String, Object> response = new HashMap<>();
        try {
            String  path = candidateService.exportList(search);
            response.put(CommonContants.DETAIL_KEY, path);
        } catch (Exception e) {
          e.printStackTrace();
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping(value = "/changeProgress")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> changeProgress(@RequestBody ParamSearchModel searchModel ) {
    	Map<String, Object> response = new HashMap<>();
    	try {
    		candidateService.changProgressByList(searchModel);
    		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
    	} catch (Exception e) {
    		e.printStackTrace();
    		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
    		response.put(CommonContants.MESSAGES_KEY, e.toString());
    	}
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
	

	
	
	
}


