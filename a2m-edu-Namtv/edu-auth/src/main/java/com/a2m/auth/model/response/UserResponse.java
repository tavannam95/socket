package com.a2m.auth.model.response;

import java.util.Date;
import java.util.List;

import com.a2m.auth.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
	
	private String userUid;
	private String userId;
	private String password;
	private String empNo;
	private String empName;
	private Boolean status;
	private Date createdDate;
	private Boolean twoFactorAuthEnable;
	private String twoFactorAuthKey;
	private String email;
	private boolean gender;
	private String address;
	private Date dateOfBirth;
	private String imgPath;
	private List<Role> roles;
	private String emailVerifyKey;
}
