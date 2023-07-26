package com.a2m.gen.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.a2m.gen.constants.ValidationCode;

public class TsstUserRoleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = ValidationCode.NotNull)
	@NotBlank(message = ValidationCode.NotBlank)
	@Size(max = 20, message = ValidationCode.Size)
	private String roleId;
	
	private String userUid;
	
	private boolean touch;
	
	private boolean checked;

	public TsstUserRoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TsstUserRoleDto(
			@NotNull(message = "NotNull") @NotBlank(message = "NotBlank") @Size(max = 20, message = "Size,min={min},max={max}") String roleId,
			String userUid, boolean touch, boolean checked) {
		super();
		this.roleId = roleId;
		this.userUid = userUid;
		this.touch = touch;
		this.checked = checked;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public boolean isTouch() {
		return touch;
	}

	public void setTouch(boolean touch) {
		this.touch = touch;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
