package com.a2m.mail.shared.domain;

import java.util.List;

import com.a2m.mail.shared.data.MailHeaderImpl;
import com.a2m.mail.shared.data.MessageAttachmentImpl;

public interface SmtpMessage {

	String getFrom();

    List<String> getTo();

    List<String> getCc();

    List<String> getBcc();

    String getSubject();

    String getText();

    List<MailHeaderImpl> getMailHeaders();

    List<MessageAttachmentImpl> getMessageAttachments();

    void setFrom(String name);

    void setTo(List<String> arrayList);

    void setCc(List<String> arrayList);

    void setBcc(List<String> arrayList);

    void setSubject(String string);

    void setText(String string);

    void setMailHeaders(List<MailHeaderImpl> mailHeader);

    void setMessageAttachments(List<MessageAttachmentImpl> attachments);
}
