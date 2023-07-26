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
import com.a2m.gen.dao.SbjChapterDao;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.course.Course0103Service;

@RestController
@RequestMapping(value = "/api/course/course0104")
public class Course0104Controller {
	 
	 @Autowired 
	 private SbjChapterDao SbjChapterDao;
	 
	 @Autowired 
	 private Course0103Service course0103Service;
  
    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody SbjChapterModel arg) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	try {
    		res = course0103Service.saveChapter(arg);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
//    @PostMapping(value = "/saveCSM")
//    @ResponseBody
//    @Transactional(rollbackFor = { Exception.class })
//    public Map<Object, Object> saveCSM(@RequestBody SubjectModel arg) throws Exception {
//        Map<Object, Object> res = new HashMap<Object, Object>();
//        try {
//            res = course0103Service.saveCSM(arg);
//        } catch (Exception e) {
//            res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
//            res.put(CommonContants.MESSAGES_KEY, e.toString());
//        }
//        return res;
//    }
//          
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		try {
			course0103Service.delete(id);
		} catch (Exception e) {
			e.printStackTrace();	
		}	
		return ResponseEntity.ok(HttpStatus.OK);
	}
		
	@GetMapping(value = "/search")
	public ResponseEntity<?> search(SbjChapterModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
		List<SbjChapterModel> lst = course0103Service.getSbjChapter(search);	
        Long totalItems = SbjChapterDao.countAllChapter(search);
		
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @GetMapping(value = "/getSbjChapterSelect")
    public ResponseEntity<?> getSbjChapterSelect(ParamBaseModel search) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
        List<SbjChapterModel> lst = course0103Service.getSbjChapterSelect(search);    
        //Long totalItems = SbjChapterDao.countAllChapter(search);
        
        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        response.put(CommonContants.LIST_KEY, lst);
        //response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getChapterById/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getChapterById(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
            SbjChapterModel detail = course0103Service.getChapterById(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getChapterBySubjectId")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getChapterBySubjectId(SbjChapterModel chapterModel) {
        Map<String, Object> response = new HashMap<>();
        try {
            SbjChapterModel detail = course0103Service.getChapterBySubjectId(chapterModel);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
