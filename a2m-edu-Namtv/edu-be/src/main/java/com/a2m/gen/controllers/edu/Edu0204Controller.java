package com.a2m.gen.controllers.edu;

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
import org.springframework.web.multipart.MultipartFile;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.ScheduleDao;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.EventModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.models.edu.ScheduleModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.models.edu.TeacherModel;
import com.a2m.gen.repository.edu.Edu0101Respository;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.edu.Edu0204Service;

@RestController
@RequestMapping(value = "/api/edu/edu0204")
public class Edu0204Controller {
	
	
	@Autowired
	private Edu0204Service edu0204Service;

	@Autowired
	private ScheduleDao scheduleDao;

	@Autowired
	private CommonService commonService;

    @GetMapping(value = "/search")
    public ResponseEntity<?> search(ScheduleModel search) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
        List<ScheduleModel> lst = edu0204Service.getSchedules(search);  
        Long count = edu0204Service.getCountSchedule(search);
        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        response.put(CommonContants.LIST_KEY, lst);
        response.put(CommonContants.COUNT_ITEMS_KEY, count);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody List<ScheduleModel> arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = edu0204Service.saveSchedule(arg);
    	} catch (Exception e) {
    		e.printStackTrace();
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }

    @GetMapping(value = "/getScheduleById/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getScheduleById(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
            ScheduleModel detail = edu0204Service.getScheduleById(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/deleteListCheck")
	@Transactional
    public ResponseEntity<?> deleteListCheck(@RequestBody List<ScheduleModel> listSchedule) {
        Map<String, Object> response = new HashMap<>();
        try {
        	edu0204Service.deleteList(listSchedule);
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
			edu0204Service.delete(Id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/getListSubjectByCourseId/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getListSubjectByCourseId(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<SubjectModel> lst = edu0204Service.getListSubjectByCourseId(Id);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @GetMapping(value = "/getClassList")
    public ResponseEntity<?> getClassList(ClassModel search) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
        List<ClassModel> lst = edu0204Service.getClassList(search);  
        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        response.put(CommonContants.LIST_KEY, lst);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getRooms")
    public ResponseEntity<?> getRooms(RoomModel search) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
        List<RoomModel> lst = edu0204Service.getRooms(search);  
        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        response.put(CommonContants.LIST_KEY, lst);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/getScheduleList")
    public ResponseEntity<?> getScheduleList(@RequestParam HashMap<String, Object> parameter) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
        List<ScheduleModel> lst = edu0204Service.getScheduleList(parameter);    
        Long count = edu0204Service.countSchedule(parameter);
        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        response.put(CommonContants.LIST_KEY, lst);
        response.put(CommonContants.COUNT_ITEMS_KEY, count);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/checkDuplicates")
    public ResponseEntity<?> checkDuplicates(ScheduleModel search) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ScheduleModel> lst = edu0204Service.getSchedules(search);  
            Long count = edu0204Service.getCountSchedule(search);
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


