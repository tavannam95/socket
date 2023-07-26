package com.a2m.gen.controllers.course;

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
import com.a2m.gen.dao.LectureDao;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.course.LectureModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.course.Course0101Service;

@RestController
@RequestMapping(value = "/api/course/course0101")
public class Course0101Controller {
	 @Autowired 
	 private CommonService commonService;
	 
	 @Autowired 
	 private LectureDao subjectDao;
	 
	 @Autowired 
	 private Course0101Service course0101Service;
  
    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody LectureModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = course0101Service.saveLecture(arg);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
       
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		try {
			course0101Service.delete(id);
		} catch (Exception e) {
			e.printStackTrace();	
		}	
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/search")
	public ResponseEntity<?> search(ParamSearchModel search)  {
		Map<String, Object> response = new HashMap<>();
		try {
		List<LectureModel> lst = course0101Service.getLectures(search);	
//		List<AemLecture> lst = subjectDao.getLectures(search);
        Long totalItems = subjectDao.countAllLecture(search);
		
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @GetMapping(value = "/getLectureById/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getLectureById(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
            LectureModel detail = course0101Service.getLectureById(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getInfoCourse/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getInfoCourse(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
             Map detail = course0101Service.getInfoCourse(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace();
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
	@GetMapping(value = "/searchByTag")
	public ResponseEntity<?> searchByTag(ParamSearchModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<LectureModel> lst = course0101Service.searchByTag(search);	
//		List<AemLecture> lst = subjectDao.getLectures(search);
        Long totalItems = subjectDao.countLectureByTag(search);
		
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
}
