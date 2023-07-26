package com.a2m.mail.shared.domain;

import com.a2m.mail.shared.data.SmtpMessageImpl;

public interface SendMessageAction {

	SmtpMessage getMessage();
    void setMessage(SmtpMessageImpl message);
    String getUsername();
    void setUsername(String username);
    String getReferences();
    String getInReplyTo();
    void setInReplyTo(String inReplyTo);
    void setReferences(String references);
    
}
