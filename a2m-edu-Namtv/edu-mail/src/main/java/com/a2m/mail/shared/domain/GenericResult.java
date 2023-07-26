package com.a2m.mail.shared.domain;

public interface GenericResult {
	String getMessage();
    boolean isSuccess();
    void setMessage(String message);
    void setSuccess(boolean success);
    void setError(String message);
}
