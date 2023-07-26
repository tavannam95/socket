package com.a2m.gen.entities.edu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.entities.TccoFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "EAM_CSC_LECTURE_FILE")
public class AemCscLectureFile extends DatabaseCommonModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EAM_CSC_LECTURE_FILE_ID")
	private Long lectureFileId;
	
	@Column(name = "LECTURES_ID")
	private Long lectureId;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@Column(name = "FILE_ID")
	private String fileId;
	
	@OneToOne(fetch = FetchType.LAZY) // @ManyToOne is also possible
    @JoinColumn(name = "FILE_ID", referencedColumnName = "ATCH_FLE_SEQ", insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private TccoFile tccoFile;

	public Long getLectureFileId() {
		return lectureFileId;
	}

	public void setLectureFileId(Long lectureFileId) {
		this.lectureFileId = lectureFileId;
	}

	public Long getLectureId() {
		return lectureId;
	}

	public void setLectureId(Long lectureId) {
		this.lectureId = lectureId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public TccoFile getTccoFile() {
		return tccoFile;
	}

	public void setTccoFile(TccoFile tccoFile) {
		this.tccoFile = tccoFile;
	}
	
	
	
}
