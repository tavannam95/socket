package com.a2m.gen.dto;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
public class LicenseMgtDto {
	@Id
	@Column(name = "CLIENT_ID")
	private String licenseId;
    private String clientId;
    private Instant insDt;
    private Instant startDt;
    private Instant expiredDt;
    private Instant updateDt;
    private String token;
//    private String eventNm;
    private String projectId;
    private String projectName;
    private String status;
    private String price;
	public LicenseMgtDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public Instant getStartDt() {
		return startDt;
	}

	public void setStartDt(Instant startDt) {
		this.startDt = startDt;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Instant getInsDt() {
		return insDt;
	}
	public void setInsDt(Instant insDt) {
		this.insDt = insDt;
	}
	public Instant getExpiredDt() {
		return expiredDt;
	}
	public void setExpiredDt(Instant expiredDt) {
		this.expiredDt = expiredDt;
	}
	public Instant getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Instant updateDt) {
		this.updateDt = updateDt;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
//	public String getEventNm() {
//		return eventNm;
//	}
//	public void setEventNm(String eventNm) {
//		this.eventNm = eventNm;
//	}

    
}
