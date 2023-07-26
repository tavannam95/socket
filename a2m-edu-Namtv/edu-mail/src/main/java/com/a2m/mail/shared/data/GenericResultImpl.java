package com.a2m.mail.shared.data;

import org.springframework.stereotype.Service;

import com.a2m.mail.shared.domain.GenericResult;

@Service
public class GenericResultImpl implements GenericResult {
	
	private String message = null;
    private boolean success = true;

    public GenericResultImpl() {
    }
    
    public GenericResultImpl(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

	@Override
	public void setMessage(String message) {
		// TODO Auto-generated method stub
		this.message = message;
	}

	@Override
	public void setSuccess(boolean success) {
		// TODO Auto-generated method stub
		this.success = success;
	}

	@Override
	public void setError(String message) {
		// TODO Auto-generated method stub
		setMessage(message);
        setSuccess(false);
	}

}
