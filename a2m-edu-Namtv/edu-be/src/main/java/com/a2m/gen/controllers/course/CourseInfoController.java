package com.a2m.gen.controllers.course;

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
import com.a2m.gen.models.edu.Edu010202RequestModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.course.CourseInfoService;
import com.a2m.gen.services.course.CourseProgramService;

@Controller
@RequestMapping(value = "/api/course/courseInfo")
public class CourseInfoController {
	
	 @Autowired 
	 private CommonService commonService;

	 @Autowired 
	 private CourseInfoService courseInfoService;
	 
	 @Autowired 
	 private CourseProgramService courseProgramService;
	 
	    @PostMapping(value = "/saveAll")
	    @ResponseBody
	    @Transactional(rollbackFor = { Exception.class })
	    public Map<Object, Object> saveAll(@RequestBody Edu010202RequestModel model) throws Exception {
	    	Map<Object, Object> res = new HashMap<Object, Object>();
	    	try {
	    		CourseInfoModel courseInfo = model.getCourseInfo();
	    		List<CourseProgramModel> coursePrograms = model.getCourseProgram();
//	    	Course Program
//	    	Remove old list
//		    	ParamSearchModel paramSearchModel = new ParamSearchModel();
//		    	paramSearchModel.setCourseId(Long.parseLong( model.getCourseId()));
//		    	List<CourseProgramModel> list = courseProgramService.getListCourseProgramByCourseId(paramSearchModel);
//		    	for (CourseProgramModel courseProgramModel : list) {
//				courseProgramService.delete(courseProgramModel.getKey());	
//				}
//		    Add new list
//		    	for (CourseProgramModel programModel : coursePrograms) {
//		    		courseProgramService.save(programModel);
//				}
	    		
//		    Course Infor
		    	res = courseInfoService.save(courseInfo);
	    		
	    		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
	    	} catch (Exception e) {
				res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
				res.put(CommonContants.MESSAGES_KEY, e.toString());
			}
			return res;
	    }
	    
	    @GetMapping(value = "/getAllList")
	    public ResponseEntity<?> getAllList(ParamSearchModel args) throws Exception {
	        Map<String, Object> response = new HashMap<>();
	        try {
	        List<CourseInfoModel> courseInfoModels = courseInfoService.getListCourseInFoByCourseId(args);
//	        List<CourseProgramModel> courseProgramModels = courseProgramService.getListCourseProgramByCourseId(args);
	        
	        Edu010202RequestModel res = new Edu010202RequestModel();
//	        res.setCourseProgram(courseProgramModels);
	        if(courseInfoModels.size()>0) {
	        	for (CourseInfoModel infoModel : courseInfoModels) {
					res.setCourseInfo(infoModel);
				}
	        }
	        
	        	
	        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
	        response.put(CommonContants.DETAIL_KEY, res);
//	        response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
	        } catch (Exception e) {
	            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
	            response.put(CommonContants.MESSAGES_KEY, e.toString());
	        }
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	 
//	    @PostMapping(value = "/save")
//	    @ResponseBody
//	    @Transactional(rollbackFor = { Exception.class })
//	    public Map<Object, Object> save(@RequestBody CourseInfoModel arg) throws Exception {
//	    	Map<Object, Object> res = new HashMap<Object, Object>();
//	    	try {
//	    		res = courseInfoService.save(arg);
//	    		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
//	    	} catch (Exception e) {
//				res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
//				res.put(CommonContants.MESSAGES_KEY, e.toString());
//			}
//			return res;
//	    }
	    
	    
	    
	    
	    @GetMapping(value = "/getCourseInfoByCourseId")
	    public ResponseEntity<?> getSubjectMap(ParamSearchModel args) throws Exception {
	        Map<String, Object> response = new HashMap<>();
	        try {
	        List<CourseInfoModel> lst = courseInfoService.getListCourseInFoByCourseId(args);
	        	
	        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
	        response.put(CommonContants.LIST_KEY, lst);
//	        response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
	        } catch (Exception e) {
	            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
	            response.put(CommonContants.MESSAGES_KEY, e.toString());
	        }
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	 

}
