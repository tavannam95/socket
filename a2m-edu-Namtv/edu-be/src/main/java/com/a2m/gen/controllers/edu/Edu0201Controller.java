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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.community.PostModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.models.edu.TeacherModel;
import com.a2m.gen.repository.edu.Edu0101Respository;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.edu.Edu0201Service;

@RestController
@RequestMapping(value = "/api/edu/edu0201")
public class Edu0201Controller {
	
	
	@Autowired
	private Edu0201Service edu0201Service;

	@Autowired
	private Edu0101Respository edu0101Respository;

	@Autowired
	private CommonService commonService;

    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody ClassModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = edu0201Service.saveClass(arg);
    	} catch (Exception e) {
    		e.printStackTrace();
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
	@GetMapping(value = "/getClassById/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getClassById(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			 ClassModel lst = edu0201Service.getClassById(Id);
			response.put(CommonContants.LIST_KEY, lst);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
	@DeleteMapping(value = "/delete/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> delete(@PathVariable("id") Long Id) {
		try {
			edu0201Service.delete(Id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

    @PostMapping(value = "/deleteListCheck")
	@Transactional
    public ResponseEntity<?> deleteListCheck(@RequestBody List<ClassModel> listClass) {
        Map<String, Object> response = new HashMap<>();
        try {
        	edu0201Service.deleteList(listClass);
        } catch (Exception e) {
//            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
//            response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
	@GetMapping(value = "/search")
	public ResponseEntity<?> search(ClassModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<ClassModel> lst = edu0201Service.get(search);	
		Long count = edu0201Service.getCount(search);
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getListClassInprogress")
	public ResponseEntity<?> getListClassInprogress(ClassModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<ClassModel> lst = edu0201Service.getListClassInprogress(search);	
			Long count = edu0201Service.getCountInProgress(search);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
			response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getListClassPostByClassId")
	public ResponseEntity<?> getListClassPostByClassId(PostModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
//			List<ClassModel> lst = edu0201Service.getListClassInprogress(search);	
//			Long count = edu0201Service.getCountInProgress(search);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
//			response.put(CommonContants.LIST_KEY, lst);
//			response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchStudent")
	public ResponseEntity<?> searchStudent(ParamBaseModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<StudentModel> lst = edu0201Service.getListStudent(search);	
		Long count = edu0201Service.getCountStudent(search);
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchStudentByClassId/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> searchStudentByClassId(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<StudentModel> lst = edu0201Service.getListStudentByClassId(Id);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchTeacher")
	public ResponseEntity<?> searchTeacher(ParamBaseModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<TeacherModel> lst = edu0201Service.getListTeacher(search);	
		Long count = edu0201Service.getCountTeacher(search);
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchTeacherByClassId/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> searchTeacherByClassId(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<TeacherModel> lst = edu0201Service.getListTeacherByClassId(Id);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchClassByStudentId/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> searchClassByStudentId(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<ClassModel> lst = edu0201Service.getListClassModelByStudentId(Id);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getCourseByStudentId/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getCourseByStudentId(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<Edu0102RequestModel> lst = edu0201Service.getCourseByStudentId(Id);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getListClassByUserUid/{userUid}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getListClassByUserUid(@PathVariable("userUid") String userUid) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<ClassModel> lst = edu0201Service.getListClassByUserUid(userUid);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}


