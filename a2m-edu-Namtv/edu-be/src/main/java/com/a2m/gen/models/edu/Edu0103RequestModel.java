package com.a2m.gen.models.edu;

import java.util.List;

import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class Edu0103RequestModel extends ParamBaseModel{
	private String idsDel;
	
	private List<RoomModel> roomModels;
	
	public Edu0103RequestModel() {
		super();
	}

	public String getIdsDel() {
		return idsDel;
	}

	public void setIdsDel(String idsDel) {
		this.idsDel = idsDel;
	}

	public List<RoomModel> getRoomModels() {
		return roomModels;
	}

	public void setRoomModels(List<RoomModel> roomModels) {
		this.roomModels = roomModels;
	}
		
	
}
