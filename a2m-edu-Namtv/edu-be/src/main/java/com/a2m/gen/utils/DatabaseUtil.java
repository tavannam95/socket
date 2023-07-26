package com.a2m.gen.utils;

import java.util.Date;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.models.ParamBaseModel;

/**
 * <strong>DatabaseUtil</strong><br>
 * <br>
 * 
 * @author doanhq
 * @version $Id$
 */
public class DatabaseUtil{


	/**
	 * <strong>setCommonFields</strong><br>
	 * <br>
	 * 
	 * @param dbentity
	 * @param userId
	 */
	public static void setCommonFields(DatabaseCommonModel dbentity, ParamBaseModel baseModel){
		setCommonFieldsWithUTC(dbentity, baseModel);
	}

	/**
	 * <strong>setCommonFieldsWithUTC</strong><br>
	 * <br>
	 *
	 * @param dbentity
	 * @param userId
	 */
	public static void setCommonFieldsWithUTC(DatabaseCommonModel dbentity, ParamBaseModel baseModel){

//		Date now = CCTimezoneUtil.getUtc();
		Date now = new Date();
		
		if(!"".equals(baseModel.getUpdUid())) {
			dbentity.setUpdDt(now);
			dbentity.setUpdUid(baseModel.getUpdUid());
		}
//
		if(dbentity.getInsUid() == null){
			dbentity.setInsDt(now);
			dbentity.setInsUid(baseModel.getInsUid());
		}

		dbentity.setDeleteFlag(false);
	}
}