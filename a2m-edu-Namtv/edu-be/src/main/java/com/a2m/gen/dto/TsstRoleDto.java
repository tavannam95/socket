package com.a2m.gen.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.a2m.gen.constants.ValidationCode;

public class TsstRoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleId; 
    
    @Size(max = 255, message = ValidationCode.Size)
    @NotNull(message = ValidationCode.NotNull)
    @NotBlank(message = ValidationCode.NotBlank)
    private String roleNm; 
    
    @Size(max = 1, message = ValidationCode.Size)
    private String useYn; 
    
    @Size(max = 255, message = ValidationCode.Size)
    private String description; 
    
    private String createdBy; 
    
    private Date createdDate;
    
    private String updatedBy; 
    
    private Date updatedDate; 
    
    private String state;

	public TsstRoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TsstRoleDto(String roleId,
			@Size(max = 255, message = "Size,min={min},max={max}") @NotNull(message = "NotNull") @NotBlank(message = "NotBlank") String roleNm,
			@Size(max = 1, message = "Size,min={min},max={max}") String useYn,
			@Size(max = 255, message = "Size,min={min},max={max}") String description, String createdBy,
			Date createdDate, String updatedBy, Date updatedDate, String state) {
		super();
		this.roleId = roleId;
		this.roleNm = roleNm;
		this.useYn = useYn;
		this.description = description;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.state = state;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleNm() {
		return roleNm;
	}

	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
    
}
