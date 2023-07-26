package com.a2m.gen.services.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.dao.ClassAnnouncementDao;
import com.a2m.gen.dao.ClassDao;
import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassAnnouncement;
import com.a2m.gen.entities.edu.AemClassAnnouncementLog;
import com.a2m.gen.models.edu.ClassAnnouncementLogModel;
import com.a2m.gen.models.edu.ClassAnnouncementModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;

@Service
public class Edu020103Service {
	@Autowired
	private CommonService commonService;

	@Autowired
	private ClassDao classDao;

	@Autowired
	private ClassAnnouncementDao dao;

	public Map<Object, Object> save(ClassAnnouncementModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();

		// Get Class entity
		Long classId = model.getClassModel().getKey();
		ClassModel classModel = new ClassModel();
		classModel.setKey(classId);
		AemClass aemClass = classDao.getClassEntity(classModel);

		// Set Class entity to ClassAnnoucement entity
		AemClassAnnouncement db = new AemClassAnnouncement();
		String userUid = commonService.getUserUid();

		if (!"".equals(CastUtil.castToString(model.getKey()))) {
			db = dao.getByModel(model);
			model.setUpdUid(userUid);
			AemClassAnnouncement save = dao.save(model, db);
//				res.put()
		} else {
			model.setInsUid(userUid);
			db.setAemClassAnnou(aemClass);
			AemClassAnnouncement save = dao.save(model, db);
		}
		return res;
	}

	public List<ClassAnnouncementModel> getListByClassId(ClassAnnouncementModel args) throws Exception {
		List<ClassAnnouncementModel> result = new ArrayList<>();
		List<AemClassAnnouncement> list = dao.getList(args);
		
		String userUid = commonService.getUserUid();
		
		if (list.size() > 0) {
			for (AemClassAnnouncement aemClassAnnouncement : list) {
				ClassAnnouncementModel model = new ClassAnnouncementModel(aemClassAnnouncement);
				
				//add log model
				ClassAnnouncementLogModel announcementLog = new ClassAnnouncementLogModel();
				List<AemClassAnnouncementLog> listLog = dao.checkLog(model.getKey(), userUid);
				
				if(listLog.size() > 0) {
					announcementLog = new ClassAnnouncementLogModel(listLog.get(0));
				}else {
					announcementLog.setClassAnnoucementId(model.getKey());
				}
				model.setAnnouncementLog(announcementLog);
				//add log model
	
				result.add(model);
			}
		}

		return result;
	}

	public Long count(ClassAnnouncementModel args) throws Exception {
		return dao.count(args);
	}

	public Boolean delete(Long id) throws Exception {
		return dao.delete(id);
	}

	public Map<Object, Object> log(ClassAnnouncementLogModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		String userUid = commonService.getUserUid();
		
		// Set Class entity to ClassAnnoucement entity
		AemClassAnnouncementLog db = new AemClassAnnouncementLog();
		
		if (!"".equals(CastUtil.castToString(model.getKey()))) {
			//only ins no need update
//			db = dao.getAemClassAnnouncementLog(model);
//			model.setUpdUid(userUid);
//			db = dao.log(model, db);
		} else {
			model.setInsUid(userUid);
			model.setUserUidLog(userUid);
			db = dao.log(model, db);
		}
		return res;
	}

}
