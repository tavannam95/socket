package com.a2m.mail.server.service;

/**
 * @author tiennd
 *
 * @created 2022
 */
import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.mail.server.constant.MailConstant;
import com.a2m.mail.shared.domain.ImapFolder;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;
import com.sun.mail.imap.IMAPStore;

@Service
public class ReadAndUnReadServiceImpl implements IReadAndUnReadService {

	@Autowired
	IMAPStoreCache imapStore;

	@Override
	public void markReadOrUnRead(User user, ImapFolder folder, List uids, String type) throws HupaException {
		// TODO Auto-generated method stub
		try {
			IMAPStore store = imapStore.get(user);
			com.sun.mail.imap.IMAPFolder f = (com.sun.mail.imap.IMAPFolder) store.getFolder(folder.getFullName());
			// check if the folder is open, if not open it "rw"
			if (f.isOpen() == false) {
				f.open(com.sun.mail.imap.IMAPFolder.READ_WRITE);
			}
			Message m = (Message) f.getMessageByUID(2);
			Message[] mArray = getMessages(user, folder, uids);

			if (MailConstant.MAIL_MARK_AS_READ.equals(type)) {
				f.setFlags((javax.mail.Message[]) mArray, new Flags(Flags.Flag.SEEN), true);
			} else if (MailConstant.MAIL_MARK_AS_UNREAD.equals(type)) {
				f.setFlags((javax.mail.Message[]) mArray, new Flags(Flags.Flag.SEEN), false);
			}

			try {
				f.expunge((javax.mail.Message[]) mArray);
				f.close(false);
			} catch (MessagingException e) {
				f.close(true);
			}
		} catch (MessagingException e) {
			System.out.println("Error while deleting messages for user " + user + " in folder" + folder);

			throw new HupaException("Error while deleting messages");
		}
	}

	protected Message[] getMessages(User user, ImapFolder folder, List uids) throws HupaException {
		try {
			IMAPStore store = imapStore.get(user);
			com.sun.mail.imap.IMAPFolder f = (com.sun.mail.imap.IMAPFolder) store.getFolder(folder.getFullName());
			if (f.isOpen() == false) {
				f.open(com.sun.mail.imap.IMAPFolder.READ_WRITE);
			}
			List<Message> messages = new ArrayList<Message>();
			for (int i = 0; i < uids.size(); i++) {
				int uid = (int) uids.get(i);
				messages.add((Message) f.getMessageByUID(uid));
			}
			Message[] mArray = messages.toArray(new Message[messages.size()]);
			return mArray;
		} catch (MessagingException e) {
			System.out.println(
					"Error while deleting messages with uids " + uids + " for user " + user + " in folder" + folder);
			throw new HupaException("Error while deleting messages", e);
		}
	}

}
