package com.a2m.gen.entities.edu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.models.edu.ClassModel;

@Entity
@Table(name = "EAM_CLASS_ANNOUNCEMENT")
public class AemClassAnnouncement extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLASS_ANNOUNCEMENT_ID")
	private Long classAnnoucementId;

	@Column(name = "CLASS_ANNOUNCEMENT_TITLE")
	private String title;
	
	@Column(name = "CLASS_ANNOUNCEMENT_CONTENT")
	private String content;

	
	@Column(name = "START_DT")
	private Date startDate;
	
	@Column(name = "END_DT")
	private Date endDate;

	@Column(name = "STATUS")
	private Boolean status;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "CLASS_ID",nullable = true)
	private AemClass aemClassAnnou;

	public AemClassAnnouncement() {
		super();
	}
	

	public AemClassAnnouncement(Long classAnnoucementId, String title, String content, Date startDate, Date endDate,
			Boolean status, AemClass aemClassAnnou) {
		super();
		this.classAnnoucementId = classAnnoucementId;
		this.title = title;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.aemClassAnnou = aemClassAnnou;
	}


	public Long getClassAnnoucementId() {
		return classAnnoucementId;
	}

	public void setClassAnnoucementId(Long classAnnoucementId) {
		this.classAnnoucementId = classAnnoucementId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public AemClass getAemClassAnnou() {
		return aemClassAnnou;
	}

	public void setAemClassAnnou(AemClass aemClassAnnou) {
		this.aemClassAnnou = aemClassAnnou;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
