package com.a2m.gen.services.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.ScheduleDao;
import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.edu.AemRoom;
import com.a2m.gen.entities.edu.AemSchedule;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.EventModel;
import com.a2m.gen.models.edu.ScheduleModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.ExcelService;
import com.a2m.gen.utils.CastUtil;


@Service
public class Edu0204Service {
	 @Autowired 
	 private CommonService commonService;
     @Autowired
     private ScheduleDao schudleDao;
     
    public List<ScheduleModel> getSchedules(ScheduleModel search) throws Exception {
        List<ScheduleModel> scheduleModel = new ArrayList<ScheduleModel>();
        List<AemSchedule> lst = schudleDao.getSchedules(search);
        for (AemSchedule db : lst) {
            ScheduleModel model = new ScheduleModel(db);
            scheduleModel.add(model);
        }
        return scheduleModel;
    }

    public Long getCountSchedule(ParamBaseModel search) {
        return schudleDao.countAllSchedule(search);
    }

    public Long countSchedule(HashMap<String, Object> parameter) throws Exception {
        return schudleDao.countSchedule(parameter);
    }

    
	public Map<Object, Object> saveSchedule(List<ScheduleModel> list) throws Exception {
	    Map<Object, Object> res = new HashMap<Object, Object>();
        res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        String userUid = commonService.getUserUid();

        for(ScheduleModel model : list) {
            AemSchedule db = new AemSchedule();
            if (!"".equals(CastUtil.castToString(model.getKey()))) {
                db = schudleDao.getSchedule(model);
                model.setUpdUid(userUid);
                AemSchedule saveCourse = schudleDao.saveSchedule(model, db);
//              setCourseCode(saveCourse);
            } else {
                model.setInsUid(userUid);
                AemSchedule saveCourse = schudleDao.saveSchedule(model, db);
            }
            res.put(CommonContants.KEY, db.getScheduleId());
        }
        return res;
	}

    public ScheduleModel getScheduleById(Long Id) {
        ScheduleModel scheduleModel = new ScheduleModel();
        scheduleModel.setKey(Id);
        AemSchedule aemSchedule = schudleDao.getScheduleById(scheduleModel);
        ScheduleModel result = new ScheduleModel(aemSchedule);
        return result;
    }

	public Boolean deleteList(List<ScheduleModel> listSchedule) throws Exception {
		for(ScheduleModel scheduleModel : listSchedule) {
			Long id = scheduleModel.getKey();
			delete(id);
		}
		return true;
	}

    public Boolean delete(Long id) {
        boolean deleteEquiqment = schudleDao.delete(id);
    return deleteEquiqment;
    }
	
	public List<SubjectModel> getListSubjectByCourseId(Long Id) {
		List<SubjectModel> list  = new ArrayList<SubjectModel>();
		AemCourse course = schudleDao.getCourseById(Id);
		List<AemCourseSubjectMap> courseSubjectMap = course.getListOfCourseSubjectMap();
		for(AemCourseSubjectMap map : courseSubjectMap) {
	      AemCourseSubject entity = map.getAemCouSubject();
	      SubjectModel model = new SubjectModel(entity);
	      list.add(model);
		}
		return list;
	}
	
   public List<ClassModel> getClassList(ClassModel search) throws Exception {
        List<ClassModel> classModels = new ArrayList<ClassModel>();
        List<AemClass> lst = schudleDao.getClassList(search);
        for(AemClass db :lst) {
            ClassModel model = new ClassModel(db, true); 
            classModels.add(model);
        }
        return classModels;
    }
   
   public List<RoomModel> getRooms(RoomModel search) throws Exception {
       List<RoomModel> roomModels = new ArrayList<RoomModel>();
       List<AemRoom> lst = schudleDao.getRooms(search);
       for(AemRoom db :lst) {
           RoomModel model = new RoomModel(db); 
           roomModels.add(model);
       }
       return roomModels;
   }

   public List<ScheduleModel> getScheduleList(HashMap<String, Object> parameter) {
       List<ScheduleModel> scheduleModels = new ArrayList<ScheduleModel>();
       List<AemSchedule> lst = schudleDao.getScheduleList(parameter);
       for (AemSchedule db : lst) {
           ScheduleModel model = new ScheduleModel(db);
           scheduleModels.add(model);
       }
       return scheduleModels;
   }
}
