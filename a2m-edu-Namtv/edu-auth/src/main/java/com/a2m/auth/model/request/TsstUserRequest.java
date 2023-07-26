package com.a2m.auth.model.request;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

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
public class TsstUserRequest {
	private String userUid;
	@NotNull
	private String userId;
	@NotNull
	private String password;
	private String fullName;
	@NotNull
	private String email;
	private boolean gender;
	private String address;
	private Date dateOfBirth;
	private Boolean status;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private Boolean twoFactorAuthEnable;
	private String twoFactorAuthKey;
	private List<String> roles;
	private String emailVerifyKey;
	private String cellPhone;
	private String imgPath;
	private Long empNo;
}
