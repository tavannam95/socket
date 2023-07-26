package com.a2m.auth.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Entity
@Table(name = "TSST_USER")
public class User {
	@Id
	@Column(name = "USER_UID")
	private String userUid;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "PWD")
	private String password;
	@Column(name = "STATUS")
	private boolean status;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	@Column(name = "IS_FIRST_LOGIN")
	private boolean isFirstLogin;
	@Column(name = "USER_TYPE")
	private String userType;
	
	@OneToOne(mappedBy = "tsstUser")
	private UserInfo empMgt;
	
//	private List<String> roles;
	
	@ManyToMany(mappedBy = "tsstUsers")
	private List<Role> tsstRoles;
}
