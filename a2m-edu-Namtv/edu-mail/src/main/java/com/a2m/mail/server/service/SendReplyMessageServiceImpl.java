package com.a2m.mail.server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.a2m.mail.server.utils.MessageUtils;
import com.a2m.mail.shared.domain.SendMessageAction;
import com.a2m.mail.shared.domain.SendReplyMessageAction;
import com.a2m.mail.shared.exception.HupaException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

//@Service
//@Primary
public class SendReplyMessageServiceImpl extends SendMessageBaseServiceImpl implements SendReplyMessageService {

	protected List getAttachments(SendMessageAction action) throws MessagingException, HupaException {
		SendReplyMessageAction replyAction = (SendReplyMessageAction) action;
		List items = new ArrayList();
		IMAPStore store = imapStore.get(session.getCache().get(action.getUsername()));

		IMAPFolder folder = (IMAPFolder) store.getFolder(replyAction.getFolder().getFullName());
		if (folder.isOpen() == false) {
			folder.open(Folder.READ_ONLY);
		}

		// Only original inline images have to be added to the list
		Message msg = folder.getMessageByUID(replyAction.getUid());
		try {
			items = MessageUtils.extractInlineImages(logger, msg.getContent());
			if (items.size() > 0)
				logger.debug("Replying a message, extracted: " + items.size() + " inline image from");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Put into the list the attachments uploaded by the user
		items.addAll(super.getAttachments(replyAction));

		return items;
	}
}
