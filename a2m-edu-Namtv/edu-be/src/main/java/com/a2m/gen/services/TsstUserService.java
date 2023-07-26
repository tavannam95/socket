package com.a2m.gen.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.dao.TsstUserDao;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.models.ParamBaseModel;

@Service
public class TsstUserService {
    
	 @Autowired 
     private TsstUserDao tsstUserDao;
    
    public List<TsstUserModel> getTsstUser(TsstUserModel search) throws Exception {
        List<TsstUserModel> tsstUser = new ArrayList<TsstUserModel>();
        List<TsstUser> lst = tsstUserDao.getTsstUserList(search);
        for(TsstUser db :lst) {
            TsstUserModel model = new TsstUserModel(db); 
            tsstUser.add(model);
        }
        return tsstUser;
    }
}
