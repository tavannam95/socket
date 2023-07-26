package com.a2m.gen.models.edu;

import java.util.List;

import com.a2m.gen.entities.edu.Equipment;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author phongnc
 */
public class Edu0104RequestModel extends ParamBaseModel{
	private String equiqmentName;
	
	private String equiqmentType;
	
	private String equiqmentCD;
	
	private Boolean equiqmentStatus;

	public Edu0104RequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Edu0104RequestModel(Equipment eq) {
		this.key = eq.getEquipmentId();
		this.equiqmentName = eq.getEquiqmentName();
		this.equiqmentStatus = eq.getEquiqmentStatus();
		this.equiqmentType = eq.getEquiqmentType();
		this.equiqmentCD = eq.getEquiqmentCD();
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
	
	
}
