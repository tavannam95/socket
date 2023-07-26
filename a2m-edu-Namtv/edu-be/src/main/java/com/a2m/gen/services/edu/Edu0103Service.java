package com.a2m.gen.services.edu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.RoomDao;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemRoom;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.Edu0103RequestModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;


@Service
public class Edu0103Service {
	 @Autowired 
	 private CommonService commonService;
	 
	 @Autowired 
	 private RoomDao roomDao;
	
	public List<RoomModel> getRooms(ParamBaseModel search) throws Exception {
		List<RoomModel> roomModels = new ArrayList<RoomModel>();
		List<AemRoom> lst = roomDao.getRooms(search);
		for(AemRoom db :lst) {
			RoomModel model = new RoomModel(db); 
			roomModels.add(model);
		}
		return roomModels;
	}

	public Map<Object, Object> saveRoom(Edu0103RequestModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		List<RoomModel> roomModels = model.getRoomModels();
    	for (RoomModel roomModel : roomModels) {
    		AemRoom db = new AemRoom();
			if(!"".equals(CastUtil.castToString(roomModel.getKey()))) {
				if("U".equals(roomModel.getState())) {
					db = roomDao.getRoom(roomModel);
					roomModel.setUpdUid(userUid);
					roomDao.saveRoom(roomModel, db);
				}
			}else {
				roomModel.setInsUid(userUid);
				roomDao.saveRoom(roomModel, db);
			}
		}
    	
    	if(StringUtils.isNotEmpty(model.getIdsDel())) {
			String escapedXml = StringEscapeUtils.escapeXml10(model.getIdsDel());
			if(!escapedXml.equals(model.getIdsDel())) {
				throw new Exception("data invalid");
			}
			List<String> idList = new ArrayList<String>(Arrays.asList(model.getIdsDel().split(",")));
			for (String id : idList) {
				roomDao.deleteRoom(CastUtil.castToLong(id));
			}
		}
    	
//		Sample db = new Sample();
//		if(CCNumberUtil.checkNull(model.key) != 0) {
//			db = sampleDao.getSample(model);
//		}
//		
//		if(db == null) {
//			res.put(A2mConst.STATUS_KEY, A2mConst.RESULT_NG);
//			res.put(A2mConst.MESSAGES_KEY, "don't have data");
//			return res;
//		}
//		
//		db = sampleDao.saveSample(model, db);
		
		return res;
	}

	public Boolean roomForDel(List<RoomModel> listRoom) throws Exception {
		roomDao.roomForDel(listRoom);
		return true;
	}

}
