package com.a2m.gen.utils;

import java.util.Date;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.models.ParamBaseModel;

/**
 * <strong>DatabaseUtil</strong><br>
 * <br>
 * 
 * @author copy of doanhq
 * @version $Id$
 */
public class ModelUtil{


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
	private static void setCommonFieldsWithUTC(DatabaseCommonModel dbentity, ParamBaseModel baseModel){
		baseModel.setInsDate(dbentity.getInsDt());
		baseModel.setInsUid(dbentity.getInsUid());
		
		baseModel.setUpdDate(dbentity.getUpdDt());
		baseModel.setUpdUid(dbentity.getUpdUid());
	}
}