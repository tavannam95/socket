package com.a2m.gen.services.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.EquiqmentDao;
import com.a2m.gen.dao.EventDao;
import com.a2m.gen.entities.edu.AemEvent;
import com.a2m.gen.entities.edu.Equipment;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.Edu0104RequestModel;
import com.a2m.gen.models.edu.EventModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;


@Service
public class Edu0203Service {
	 @Autowired 
	 private CommonService commonService;
	 
	 @Autowired 
	 private EventDao eventDao;
	
	public List<EventModel> get(ParamBaseModel search) throws Exception {
		List<EventModel> listEvent = new ArrayList<EventModel>();
		List<AemEvent> lst = eventDao.getList(search);
		for(AemEvent db :lst) {
			EventModel model = new EventModel(db); 
			listEvent.add(model);
		}
		return listEvent;
	}

	public Map<Object, Object> save(EventModel arg) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		AemEvent db = new AemEvent();
		
		if(!"".equals(CastUtil.castToString(arg.getKey()))) {
				db = eventDao.getByModel(arg);
				arg.setUpdUid(userUid);
				AemEvent save = eventDao.save(arg, db);
		}else {
				arg.setInsUid(userUid);
				AemEvent save = eventDao.save(arg, db);
		}
		return res;
	}
//	
	public Long getCount(ParamBaseModel search) {
		return eventDao.count(search);
	}
//	
	public EventModel getbyId (Long id) {
		EventModel eventModel = new EventModel();
		eventModel.setKey(id);
		AemEvent aemEvent = eventDao.getByModel(eventModel);
		EventModel model = new EventModel(aemEvent);
		return model;
	}
//	
	public Boolean deleteList(List<EventModel> listEvent) throws Exception {
		for(EventModel eventModel : listEvent) {
			Long id = eventModel.getKey();
			delete(id);
		}
		return true;
	}

	public Boolean delete(Long id) {
		EventModel eventModel = new EventModel();
		eventModel.setKey(id);
		AemEvent aemEvent = eventDao.getByModel(eventModel);
		String filePath = aemEvent.getFilePath();
		commonService.deleteFile(filePath);
		boolean delete = eventDao.delete(id);
	return delete;
	}
	
}
