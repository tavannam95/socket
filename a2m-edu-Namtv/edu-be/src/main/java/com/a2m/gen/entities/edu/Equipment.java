package com.a2m.gen.entities.edu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "EAM_EQUIPMENT")
public class Equipment extends DatabaseCommonModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EQUIPMENT_ID")
	private Long equipmentId;
	
	@Column(name = "EQUIPMENT_NM")
	private String equiqmentName;
	
	@Column(name = "EQUIPMENT_TYPE")
	private String equiqmentType;
	
	@Column(name = "EQUIPMENT_CD")
	private String equiqmentCD;
	
	@Column(name = "STATUS")
	private Boolean equiqmentStatus;
	
	
	
	public Equipment() {
		super();
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}



	public String getEquiqmentName() {
		return equiqmentName;
	}

	public void setEquiqmentName(String equiqmentName) {
		this.equiqmentName = equiqmentName;
	}

	public String getEquiqmentType() {
		return equiqmentType;
	}

	public void setEquiqmentType(String equiqmentType) {
		this.equiqmentType = equiqmentType;
	}

	public String getEquiqmentCD() {
		return equiqmentCD;
	}

	public void setEquiqmentCD(String equiqmentCD) {
		this.equiqmentCD = equiqmentCD;
	}

	public Boolean getEquiqmentStatus() {
		return equiqmentStatus;
	}

	public void setEquiqmentStatus(Boolean equiqmentStatus) {
		this.equiqmentStatus = equiqmentStatus;
	}


//	public boolean isFirstLogin() {
//		return isFirstLogin;
//	}
//
//	public void setFirstLogin(boolean isFirstLogin) {
//		this.isFirstLogin = isFirstLogin;
//	}
	
	
	
}
