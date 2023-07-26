package com.a2m.gen.models.edu;

import com.a2m.gen.entities.edu.AemRoom;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class RoomModel extends ParamBaseModel{

	private String roomNm;
	
	private Boolean status;
	
	private String state;

	public RoomModel() {
		super();
	}
	
	public RoomModel(AemRoom db) {
		this.key = db.getRoomId();
		this.roomNm = db.getRoomNm();
		this.status = db.getStatus();
	}
	
	public String getRoomNm() {
		return roomNm;
	}

	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
	
	

}
