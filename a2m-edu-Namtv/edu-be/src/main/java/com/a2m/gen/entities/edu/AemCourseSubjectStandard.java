package com.a2m.gen.entities.edu;

import java.util.ArrayList;
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
import com.a2m.gen.models.course.SubjectStandardModel;

@Entity
@Table(name = "EAM_COURSE_SUBJECT_STANDARD")
public class AemCourseSubjectStandard extends DatabaseCommonModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAND_ID")
    private Long standId;
    
    @Column(name = "IS_APPROVAL")
    private Boolean isApproval;
    
	@Column(name = "DOCUMENT_STATUS")
	private String documentStatus;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "SUBJECT_ID")
    private AemCourseSubject subjectStand;

    @Column(name = "STAND_CODE")
    private String standCode;

    @Column(name = "STAND_CONTENT")
    private String standContent;

    @Column(name = "STAND_TYPE")
    private String standType;

    @Column(name = "STATUS")
    private Boolean status;

    @OneToMany(mappedBy = "standNote", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AemCourseSubjectStandardNote> listStandNote = new ArrayList<AemCourseSubjectStandardNote>();

    public Long getStandId() {
        return standId;
    }

    public void setStandId(Long standId) {
        this.standId = standId;
    }

    public AemCourseSubject getSubjectStand() {
        return subjectStand;
    }

    public void setSubjectStand(AemCourseSubject subjectStand) {
        this.subjectStand = subjectStand;
    }

    public String getStandCode() {
        return standCode;
    }

    public void setStandCode(String standCode) {
        this.standCode = standCode;
    }

    public String getStandContent() {
        return standContent;
    }

    public void setStandContent(String standContent) {
        this.standContent = standContent;
    }

    public String getStandType() {
        return standType;
    }

    public void setStandType(String standType) {
        this.standType = standType;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<AemCourseSubjectStandardNote> getListStandNote() {
        return listStandNote;
    }

    public void setListStandNote(List<AemCourseSubjectStandardNote> listStandNote) {
        this.listStandNote = listStandNote;
    }

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public Boolean getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(Boolean isApproval) {
		this.isApproval = isApproval;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
