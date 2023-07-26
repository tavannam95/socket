package com.a2m.gen.models.iqTest;


import com.a2m.gen.entities.iqTest.AemNonUser;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class NonUserModel extends ParamBaseModel{
	
	private String fullName;
    private String phone;
    private String email;
    private Boolean status;
   
	
	
	public NonUserModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NonUserModel(AemNonUser db) {
		this.key = db.getNonUserId();
        this.fullName = db.getFullName();
        this.phone = db.getPhone();
        this.email = db.getEmail();
        this.status = db.getStatus();
        
        
	}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
	
}
