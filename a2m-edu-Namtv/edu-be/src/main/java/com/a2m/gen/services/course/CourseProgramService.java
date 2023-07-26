package com.a2m.gen.services.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.CourseDao;
import com.a2m.gen.dao.CourseInfoDao;
import com.a2m.gen.dao.CourseProgramDao;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseInfo;
import com.a2m.gen.entities.edu.AemCourseProgram;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.edu.AemEvent;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.course.CourseInfoModel;
import com.a2m.gen.models.course.CourseProgramModel;
import com.a2m.gen.models.course.CourseSubjectMapModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;

@Service
public class CourseProgramService {
	
	 @Autowired 
	 private CommonService commonService;
	
	@Autowired 
	private CourseProgramDao courseProgramDao;
	
	@Autowired 
	private CourseDao courseDao;
	
	public AemCourseProgram save(CourseProgramModel model) throws Exception {
	    Map<Object, Object> res = new HashMap<Object, Object>();
	    
	    //Get course entity
	    Long courseId = model.getCourseId();
	    Edu0102RequestModel courseModel = new Edu0102RequestModel();
	    courseModel.setKey(courseId);
	    AemCourse aemCourse = courseDao.getCourse(courseModel);
	    
	    //Set course entity to courseInfo entity 
	    AemCourseProgram db = new AemCourseProgram();
	    String userUid = commonService.getUserUid();
	    
	    AemCourseProgram save =  new AemCourseProgram();
	    
	    if(!"".equals(CastUtil.castToString(model.getKey()))) {
	    	model.setDeleteFlag(false);
			db = courseProgramDao.getCourseProgram(model);
			model.setUpdUid(userUid);
			  save = courseProgramDao.save(model, db);		
	    }else {
	    	model.setInsUid(userUid);
	    	db.setCourseEntity(aemCourse);
	    	   save = courseProgramDao.save(model, db);		
		}
		return save;
	}
	
	public List<CourseProgramModel> getListCourseProgramByCourseId(ParamSearchModel args ){
		List<CourseProgramModel> listCourseProgramModel = new ArrayList<>();
		List<AemCourseProgram> listAemCourseProgram = courseProgramDao.getCourseProgramByCourseId(args);
		for (AemCourseProgram aemCourseProgram : listAemCourseProgram) {
			CourseProgramModel model = new CourseProgramModel(aemCourseProgram);
			listCourseProgramModel.add(model);
		}
		return listCourseProgramModel;
	}
	
	public AemCourseProgram delete(Long id) {
		AemCourseProgram delete = courseProgramDao.delete(id);
		return delete;
	}

}
