package com.a2m.gen.controllers.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.course.CourseInfoModel;
import com.a2m.gen.models.course.CourseProgramModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.course.CourseInfoService;
import com.a2m.gen.services.course.CourseProgramService;

@Controller
@RequestMapping(value = "/api/course/courseProgram")
public class CourseProgramController {
	
	 @Autowired 
	 private CommonService commonService;

	 @Autowired 
	 private CourseProgramService courseProgramService;
	 
	    @PostMapping(value = "/save")
	    @ResponseBody
	    @Transactional(rollbackFor = { Exception.class })
	    public Map<Object, Object> save(@RequestBody Map args) throws Exception {
	    	Map<Object, Object> res = new HashMap<Object, Object>();
	    	List<Map> listCourseProgram = (List<Map>) args.get("courseProgram");
	    	String courseId = (String) args.get("courseId");
	    	
	    	try {
//	    	Remove List old  CourseProgram
	    	ParamSearchModel paramSearchModel = new ParamSearchModel();
	    	paramSearchModel.setCourseId(Long.parseLong(courseId));
	    	List<CourseProgramModel> list = courseProgramService.getListCourseProgramByCourseId(paramSearchModel);
	    	for (CourseProgramModel courseProgramModel : list) {
			courseProgramService.delete(courseProgramModel.getKey());	
			}
	    	
//	    		set value for each CoursePRogramModel and add new list 
	    		for (Map element : listCourseProgram) {
					CourseProgramModel programModel = new CourseProgramModel();
//					String courseId =  element.get("courseId").toString();
					programModel.setCourseId( Long.parseLong(courseId));
					programModel.setTitle(element.get("title").toString());
					programModel.setShortDiscription(element.get("shortDiscription").toString());
					programModel.setLecture(element.get("lecture").toString());
					if(element.get("key")!=null) {
						programModel.setKey(Long.parseLong(element.get("key").toString()));
						programModel.setDeleteFlag(false);
					}
					courseProgramService.save(programModel);
				}
	    		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
	    	} catch (Exception e) {
				res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
				res.put(CommonContants.MESSAGES_KEY, e.toString());
			}
			return res;
	    }
	    
	    @GetMapping(value = "/getCourseProgramByCourseId")
	    public ResponseEntity<?> getSubjectMap(ParamSearchModel args) throws Exception {
	        Map<String, Object> response = new HashMap<>();
	        try {
	        	List<CourseProgramModel> lst = courseProgramService.getListCourseProgramByCourseId(args);
	        	
	        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
	        response.put(CommonContants.LIST_KEY, lst);
	        } catch (Exception e) {
	            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
	            response.put(CommonContants.MESSAGES_KEY, e.toString());
	        }
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	 

}
