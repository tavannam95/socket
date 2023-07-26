package com.a2m.mail.server.service;

import com.a2m.mail.shared.domain.GenericResult;
import com.a2m.mail.shared.domain.SendMessageAction;

public interface SendReplyMessageService {
	GenericResult send(SendMessageAction action, boolean isResettAttachments, boolean isDraftMail) throws Exception;
}
