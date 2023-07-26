package com.a2m.gen.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "GEN_LICENSE_MGT")
public class LicenseMgt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LICENSE_ID", nullable = false, length = 50)
    private String id;

    @Column(name = "CLIENT_ID", nullable = false, length = 50)
    private String clientId;

    @Column(name = "INS_DT")
    private Instant insDt;

    @Column(name = "EXPIRED_DT")
    private Instant expiredDt;
    
    @Column(name = "START_DT")
    private Instant startDt;

    @Column(name = "UPDATE_DT")
    private Instant updateDt;

    @Column(name = "TOKEN", length = 50)
    private String token;
    
    @Column(name = "PRICE", length = 50)
    private Double price;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "PROJECT_ID")
    private String projectId;

	public LicenseMgt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setId(String id) {
		this.id = id;
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

	public Instant getStartDt() {
		return startDt;
	}

	public void setStartDt(Instant startDt) {
		this.startDt = startDt;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}