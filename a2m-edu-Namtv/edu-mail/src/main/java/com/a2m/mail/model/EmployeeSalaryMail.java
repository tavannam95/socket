package com.a2m.mail.model;

import java.util.List;

import com.a2m.mail.shared.data.MessageAttachmentImpl;

public class EmployeeSalaryMail {

	private String from;
	
	private List<String> to;
	
	private String subject;
	
	private String content;
	
	private List<MessageAttachmentImpl> attachments;
	
	private String privateFileListName;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<MessageAttachmentImpl> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<MessageAttachmentImpl> attachments) {
		this.attachments = attachments;
	}

	public String getPrivateFileListName() {
		return privateFileListName;
	}

	public void setPrivateFileListName(String privateFileListName) {
		this.privateFileListName = privateFileListName;
	}

}
