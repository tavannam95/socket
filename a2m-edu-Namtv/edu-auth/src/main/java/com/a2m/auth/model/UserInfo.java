package com.a2m.auth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TSST_USER_INFO")
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMP_NO")
	private Long empNo;
	@Column(name = "FULL_NAME")
	private String fullName;
	@Column(name = "DOB")
	private Date dateOfBirth;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "CELL_PHONE")
	private String cellPhone;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "GENDER")
	private boolean gender;
	@Column(name = "IMG_PATH")
	private String imgPath;
	@Column(name = "EMAIL_VERIFY_KEY")
	private String emailVerifyKey;
	@Column(name = "2FA_ENABLE")
	private boolean twoFactorAuthentication;
	@Column(name = "2FA_KEY")
	private String twoFactorAuthenticationKey;
	
	@OneToOne
	@JoinColumn(name = "EMP_NO", referencedColumnName = "EMP_NO")
	private User tsstUser;
}
