package com.a2m.gen.controllers.edu;

import java.util.ArrayList;
import java.util.Date;
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
import com.a2m.gen.dao.SubjectDao;
import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.services.ProgressBarService;
import com.a2m.gen.services.course.Course0102Service;
import com.a2m.gen.services.edu.Edu0101Service;
import com.a2m.gen.services.edu.Edu0102Service;

@RestController
@RequestMapping(value = "/api/edu/edu0102")
public class Edu0102Controller {
	@Autowired
	private Edu0101Service edu0101Service;
	
	@Autowired
	private Edu0102Service edu0102Service;
    
    @Autowired 
    private Course0102Service course0102Service;
    
    @Autowired 
    private SubjectDao subjectDao;
    
    @Autowired
    private ProgressBarService progressBarService;
    
    
    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody Edu0102RequestModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = edu0102Service.saveCourse(arg);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
	@DeleteMapping(value = "/delete/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> delete(@PathVariable("id") Long Id) {
		try {
			edu0102Service.delete(Id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<?> search(ParamSearchModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<Edu0102RequestModel> lst = edu0102Service.getCourses(search);	
		Long count = edu0102Service.getCountCourse(search);
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @GetMapping(value = "/getCourseByUserUid")
    public ResponseEntity<?> getCoursebyCourseView(ParamBaseModel search) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
        Object lst = edu0102Service.getCoursesByCourseView(search);  
//        Long count = edu0102Service.getCountCourse(search);
        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        response.put(CommonContants.LIST_KEY, lst);
//        response.put(CommonContants.COUNT_ITEMS_KEY, count);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
	@GetMapping(value = "/getCourses")
	public ResponseEntity<?> getCourses(ParamSearchModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<Edu0102RequestModel> lst = edu0102Service.getCourses(search);	
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getListCourseInprogress")
	public ResponseEntity<?> getListCourseInprogress(ParamBaseModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<Edu0102RequestModel> lst = edu0102Service.getListCourseInprogress();	
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getListCourseByUserUid")
	public ResponseEntity<?> getListCourseByUserUid(ParamBaseModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			String userUid = search.getKey().toString();
			List<Edu0102RequestModel> lst = edu0102Service.getListCourseByUserUid(userUid);	
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @GetMapping(value = "/getCourseById/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getCourseById(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Edu0102RequestModel detail = edu0102Service.getCourseById(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getCourseByCondition")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getCourseByCondition(Edu0102RequestModel courseModel) {
        Map<String, Object> response = new HashMap<>();
        try {
            Edu0102RequestModel detail = edu0102Service.getCourseByCondition(courseModel);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getSubjectList")
    public ResponseEntity<?> getSubjectList(ParamBaseModel search) throws Exception {
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
    
    @GetMapping(value = "/getNameProgress")
    public ResponseEntity<?> getNameProgress(@RequestParam Map<String, Object> search) throws Exception {
        //Map<String, Object> response = new HashMap<>();
        Map<String, Object> progress = progressBarService.getNameProgress(search);

        //response.put("list", progress.);

        return new ResponseEntity<>(progress, HttpStatus.OK);
    }
}    
