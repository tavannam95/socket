package com.a2m.mail.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.mail.shared.data.DeleteMessageResultImpl;
import com.a2m.mail.shared.domain.DeleteMessageResult;
import com.a2m.mail.shared.domain.ImapFolder;
import javax.mail.Message;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;
import com.sun.mail.imap.IMAPStore;

@Service
public class DeleteMessagesServiceImpl implements DeleteMessageByUidService {

	@Autowired
	IMAPStoreCache imapStore;

	@Override
	public DeleteMessageResult delete(User user, ImapFolder folder, List uids) throws HupaException {
		// TODO Auto-generated method stub
		try {
			IMAPStore store = imapStore.get(user);
			com.sun.mail.imap.IMAPFolder f = (com.sun.mail.imap.IMAPFolder) store.getFolder(folder.getFullName());
			// check if the folder is open, if not open it "rw"
			if (f.isOpen() == false) {
				f.open(com.sun.mail.imap.IMAPFolder.READ_WRITE);
			}
			Message m = (Message) f.getMessageByUID(2);
			Message[] mArray = getMessagesToDelete(user, folder, uids);

			// check if the delete was triggered not in the trash folder
			if (folder.getFullName().equalsIgnoreCase(user.getSettings().getTrashFolderName()) == false) {
				com.sun.mail.imap.IMAPFolder trashFolder = (com.sun.mail.imap.IMAPFolder) store
						.getFolder(user.getSettings().getTrashFolderName());

				boolean trashFound = false;
				// if the trash folder does not exist we create it
				if (trashFolder.exists() == false) {
					trashFound = trashFolder.create(com.sun.mail.imap.IMAPFolder.READ_WRITE);
				} else {
					trashFound = true;
				}

				// Check if we are able to copy the messages to the trash folder
				if (trashFound) {
					// copy the messages to the trashfolder
					f.copyMessages((javax.mail.Message[]) mArray, trashFolder);
				}
			}

			// delete the messages from the folder
			f.setFlags((javax.mail.Message[]) mArray, new Flags(Flags.Flag.DELETED), true);

			try {
				f.expunge((javax.mail.Message[]) mArray);
				f.close(false);
			} catch (MessagingException e) {
				// prolly UID expunge is not supported
				f.close(true);
			}
			return new DeleteMessageResultImpl(user, folder, mArray.length);

		} catch (MessagingException e) {
//            logger.error("Error while deleting messages for user " + user
//                    + " in folder" + action.getFolder(), e);
			System.out.println("Error while deleting messages for user " + user + " in folder" + folder);

			throw new HupaException("Error while deleting messages");
		}
	}

	protected Message[] getMessagesToDelete(User user, ImapFolder folder, List uids) throws HupaException {

		try {
			IMAPStore store = imapStore.get(user);
			com.sun.mail.imap.IMAPFolder f = (com.sun.mail.imap.IMAPFolder) store.getFolder(folder.getFullName());
			// check if the folder is open, if not open it "rw"
			if (f.isOpen() == false) {
				f.open(com.sun.mail.imap.IMAPFolder.READ_WRITE);
			}
			// build up the list of messages to delete
			List<Message> messages = new ArrayList<Message>();
//            for (Long uid : uids) {
//                messages.add((Message) f.getMessageByUID(uid));
//            }
			for (int i = 0; i < uids.size(); i++) {
				int uid = (int) uids.get(i);
				messages.add((Message) f.getMessageByUID(uid));
			}
			Message[] mArray = messages.toArray(new Message[messages.size()]);
			return mArray;
		} catch (MessagingException e) {
//            logger.error("Error while deleting messages with uids "
//                    + action.getMessageUids() + " for user " + user
//                    + " in folder" + action.getFolder(), e);
			System.out.println(
					"Error while deleting messages with uids " + uids + " for user " + user + " in folder" + folder);
			throw new HupaException("Error while deleting messages", e);
		}

	}

	@Override
	public DeleteMessageResult restoreDeleted(User user, ImapFolder folder, List uids, String toFolderName) throws HupaException {
		try {
			IMAPStore store = imapStore.get(user);
			com.sun.mail.imap.IMAPFolder f = (com.sun.mail.imap.IMAPFolder) store.getFolder(folder.getFullName());
			// check if the folder is open, if not open it "rw"
			if (f.isOpen() == false) {
				f.open(com.sun.mail.imap.IMAPFolder.READ_WRITE);
			}
			Message m = (Message) f.getMessageByUID(2);
			Message[] mArray = getMessagesToDelete(user, folder, uids);

			// check if the delete was triggered not in the trash folder
			if (folder.getFullName().equalsIgnoreCase(toFolderName) == false) {
				com.sun.mail.imap.IMAPFolder toFolder = (com.sun.mail.imap.IMAPFolder) store
						.getFolder(toFolderName.equalsIgnoreCase(user.getSettings().getInboxFolderName()) ? user.getSettings().getInboxFolderName() :
							toFolderName.equalsIgnoreCase(user.getSettings().getDraftsFolderName()) ? user.getSettings().getDraftsFolderName() : 
								toFolderName.equalsIgnoreCase(user.getSettings().getSentFolderName()) ? user.getSettings().getSentFolderName() : null);

				boolean trashFound = false;
				// if the trash folder does not exist we create it
				if (toFolder.exists() == false) {
					trashFound = toFolder.create(com.sun.mail.imap.IMAPFolder.READ_WRITE);
				} else {
					trashFound = true;
				}

				// Check if we are able to copy the messages to the trash folder
				if (trashFound) {
					// copy the messages to the trashfolder
					f.copyMessages((javax.mail.Message[]) mArray, toFolder);
				}
			}

			// delete the messages from the folder
			f.setFlags((javax.mail.Message[]) mArray, new Flags(Flags.Flag.DELETED), true);

			try {
				f.expunge((javax.mail.Message[]) mArray);
				f.close(false);
			} catch (MessagingException e) {
				// prolly UID expunge is not supported
				f.close(true);
			}
			return new DeleteMessageResultImpl(user, folder, mArray.length);

		} catch (MessagingException e) {
//            logger.error("Error while deleting messages for user " + user
//                    + " in folder" + action.getFolder(), e);
			System.out.println("Error while deleting messages for user " + user + " in folder" + folder);

			throw new HupaException("Error while deleting messages");
		}
	}

}
