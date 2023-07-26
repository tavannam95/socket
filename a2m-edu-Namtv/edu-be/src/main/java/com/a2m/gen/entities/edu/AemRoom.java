package com.a2m.gen.entities.edu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "EAM_ROOM")
public class AemRoom extends DatabaseCommonModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROOM_ID")
	private Long roomId;
	
	@Column(name = "ROOM_NM", nullable = false, unique=true)
	private String roomNm;

	@Column(name = "STATUS")
	private Boolean status;

//	@Column(name = "INS_DT")
//	public Date insDt;
//	
//	@Column(name = "INS_UID")
//	private String insUid;
//	
//	@Column(name = "UPD_DT")
//	private Date updDt;
//	
//	@Column(name = "UPD_UID")
//	private String updUid;

    @OneToMany(mappedBy = "roomSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AemSchedule> listScheduleOfRoom = new ArrayList<AemSchedule>();

	public AemRoom() {
		super();
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getRoomNm() {
		return roomNm;
	}

	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}

	public Boolean isStatus() {
		return status;
	}


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
	
	
}
