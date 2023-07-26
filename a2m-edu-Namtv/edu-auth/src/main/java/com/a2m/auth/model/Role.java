package com.a2m.auth.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TSST_ROLE")
public class Role {
	@Id
	@Column(name = "ROLE_ID")
	private String roleId;
	@Column(name = "ROLE_NM")
	private String roleNm;
	@Column(name = "USE_YN")
	private String useYn;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToMany
	@JoinTable(name = "TSST_USER_ROLE", 
		joinColumns = @JoinColumn(name = "ROLE_ID", nullable = false, updatable = true), 
		inverseJoinColumns = @JoinColumn(referencedColumnName = "USER_UID", nullable = false, updatable = true))
	private List<User> tsstUsers;
}
