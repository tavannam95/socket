package com.a2m.gen.utils;

import java.io.Serializable;

public class AjaxResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean status;
	private String message;
	private Object responseData;
	public AjaxResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AjaxResult(boolean status, String message, Object responseData) {
		super();
		this.status = status;
		this.message = message;
		this.responseData = responseData;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResponseData() {
		return responseData;
	}
	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}
	
	
}
