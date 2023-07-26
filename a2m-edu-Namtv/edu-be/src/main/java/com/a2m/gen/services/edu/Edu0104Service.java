package com.a2m.gen.services.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.EquiqmentDao;
import com.a2m.gen.entities.edu.Equipment;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.Edu0104RequestModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;


@Service
public class Edu0104Service {
	 @Autowired 
	 private CommonService commonService;
	 
	 @Autowired 
	 private EquiqmentDao equiqmentDao;
	
	public List<Edu0104RequestModel> get(ParamBaseModel search) throws Exception {
		List<Edu0104RequestModel> equiqmentModels = new ArrayList<Edu0104RequestModel>();
		List<Equipment> lst = equiqmentDao.getEquiqment(search);
		for(Equipment db :lst) {
			Edu0104RequestModel model = new Edu0104RequestModel(db); 
			equiqmentModels.add(model);
		}
		return equiqmentModels;
	}

	public Map<Object, Object> saveEquiqment(Edu0104RequestModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		Equipment db = new Equipment();
		
		if(!"".equals(CastUtil.castToString(model.getKey()))) {
				db = equiqmentDao.getEquiqment(model);
				model.setUpdUid(userUid);
				Equipment saveEquiqment = equiqmentDao.saveEquiqment(model, db);
		}else {
				model.setInsUid(userUid);
				Equipment saveEquiqment = equiqmentDao.saveEquiqment(model, db);
		}
		return res;
	}
	
	public Long getCountEquiqment(ParamBaseModel search) {
		return equiqmentDao.counEquiqment(search);
	}

	public Boolean equiqmentForDel(List<Edu0104RequestModel> listEquiqment) throws Exception {
		equiqmentDao.equiqmentForDel(listEquiqment);
		return true;
	}

	public Boolean delete(Long id) {
		boolean deleteEquiqment = equiqmentDao.deleteEquiqment(id);
	return deleteEquiqment;
	}
	
}
