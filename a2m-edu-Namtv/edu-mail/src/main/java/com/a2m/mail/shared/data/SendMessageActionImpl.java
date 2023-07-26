package com.a2m.mail.shared.data;

import com.a2m.mail.shared.domain.SendMessageAction;
import com.a2m.mail.shared.domain.SmtpMessage;

public class SendMessageActionImpl implements SendMessageAction {
	
	private SmtpMessage message;
	private String username;

    public SendMessageActionImpl(SmtpMessage msg) {
        this.message = msg;
    }

    public SendMessageActionImpl() {

    }

    public SmtpMessage getMessage() {
        return message;
    }

    public void setMessage(SmtpMessageImpl message) {
        this.message = message;
    }

    public String getInReplyTo() {
        return null;
    }

    public String getReferences() {
        return null;
    }

	@Override
    public void setInReplyTo(String inReplyTo) {
    }

    @Override
    public void setReferences(String references) {
    }

	@Override
	public String toString() {
		return "SendMessageActionImpl [message=" + message + "]";
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}
    
    
}
