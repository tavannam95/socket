package com.a2m.gen.controllers.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.a2m.gen.dao.SubjectDao;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.course.SubjectHistoryModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.course.Course0102Service;

@RestController
@RequestMapping(value = "/api/course/course0102")
public class Course0102Controller {
	 @Autowired 
	 private CommonService commonService;
	 
	 @Autowired 
	 private SubjectDao subjectDao;
	 
	 @Autowired 
	 private Course0102Service course0102Service;
  
    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody SubjectHistoryModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = course0102Service.saveSubject(arg);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
    @PostMapping(value = "/saveCSM")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> saveCSM(@RequestBody SubjectModel arg) throws Exception {
        Map<Object, Object> res = new HashMap<Object, Object>();
        try {
            res = course0102Service.saveCSM(arg);
        } catch (Exception e) {
            res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            res.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return res;
    }
    
    @PostMapping(value = "/doChooseSubject")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> doChooseSubject(@RequestBody SubjectModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = course0102Service.doChooseSubject(arg);
    	} catch (Exception e) {
    		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
    		res.put(CommonContants.MESSAGES_KEY, e.toString());
    	}
    	return res;
    }
          
	@DeleteMapping(value = "/delete/{subjectId}/{courseId}")
	public ResponseEntity<?> delete(@PathVariable("subjectId") String subjectId, @PathVariable("courseId") String courseId) {
		try {
			course0102Service.delete(subjectId, courseId);
		} catch (Exception e) {
			e.printStackTrace();	
		}	
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/setTrueDeleteFlag/{subjectId}")
	public ResponseEntity<?> setTrueDeleteFlag(@PathVariable("subjectId") String subjectId) {
		try {
			course0102Service.setTrueDeleteFlag(subjectId);
		} catch (Exception e) {
			e.printStackTrace();	
		}	
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity<?> search(ParamBaseModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<SubjectModel> lst = course0102Service.getSubjects(search);	
//		List<AemSubject> lst = subjectDao.getSubjects(search);
        Long totalItems = subjectDao.countAllSubject(search);
		
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @GetMapping(value = "/getSubjectMap")
    public ResponseEntity<?> getSubjectMap(ParamBaseModel search) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
        List<SubjectModel> lst = course0102Service.getSubjects(search); 
//      List<AemSubject> lst = subjectDao.getSubjects(search);
        Long totalItems = subjectDao.countAllSubject(search);
        
        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        response.put(CommonContants.LIST_KEY, lst);
        response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getSubjectById/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getSubjectById(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
            SubjectModel detail = course0102Service.getSubjectById(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
        	response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getSubjectHistory/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getSubjectHistory(@PathVariable("id") Long Id) {
    	Map<String, Object> response = new HashMap<>();
    	try {
    		SubjectHistoryModel detail = course0102Service.getSubjectHistory(Id);
    		response.put(CommonContants.DETAIL_KEY, detail);
    	} catch (Exception e) {
    		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
    		response.put(CommonContants.MESSAGES_KEY, e.toString());
    		e.printStackTrace();
    	}
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getLectureScheduleBySubjectId/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getLectureScheduleBySubjectId(@PathVariable("id") String Id) {
        Map<String, Object> response = new HashMap<>();
        try {
        	List<HashMap<String, Object>> list = course0102Service.getLectureScheduleBySubjectId(Id);
        	response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
            response.put(CommonContants.LIST_KEY, list);
        } catch (Exception e) {
        	response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
	@GetMapping(value = "getLectureScheduleBySubjectId/exportPDF/{id}")
	public ResponseEntity<?> exportPDFScheduleBySubjectId(HttpServletRequest request, @PathVariable("id") String Id) {
  	  System.out.println(" ===== exportPDF Controller =====");
		Map<String, Object> response = new HashMap<>();
		try {
			String  path = course0102Service.exportPDFScheduleBySubjectId(Id);
			response.put(CommonContants.DETAIL_KEY, path);
		} catch (Exception e) {
		  e.printStackTrace();
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "exportPDF/{id}")
	public ResponseEntity<?> exportPDF(HttpServletRequest request, @PathVariable("id") String Id) {
  	  System.out.println(" ===== exportPDF Controller =====");
		Map<String, Object> response = new HashMap<>();
		try {
			String  path = course0102Service.exportPDF(Id);
			response.put(CommonContants.DETAIL_KEY, path);
		} catch (Exception e) {
		  e.printStackTrace();
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @GetMapping(value = "exportDoc/{id}")
    public ResponseEntity<?> exportDoc(HttpServletRequest request, @PathVariable("id") String Id) {
      System.out.println(" ===== exportPDF Controller =====");
        Map<String, Object> response = new HashMap<>();
        try {
            String  path = course0102Service.exportWord(Id);
            response.put(CommonContants.DETAIL_KEY, path);
        } catch (Exception e) {
          e.printStackTrace();
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
