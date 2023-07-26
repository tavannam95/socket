package com.a2m.mail.server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.UIDFolder;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.search.BodyTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.mail.server.handler.JavamailUtil;
import com.a2m.mail.server.preferences.UserPreferencesStorage;
import com.a2m.mail.server.utils.MessageUtils;
import com.a2m.mail.shared.data.FetchMessagesResultImpl;
import com.a2m.mail.shared.data.MessageImpl.IMAPFlag;
import com.a2m.mail.shared.data.TagImpl;
import com.a2m.mail.shared.domain.FetchMessagesAction;
import com.a2m.mail.shared.domain.FetchMessagesResult;
import com.a2m.mail.shared.domain.Tag;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

@Service
public class FetchMessagesServiceImpl extends AbstractService implements FetchMessagesService {

	@Autowired
	IMAPStoreCache imapStore;
	
    public UserPreferencesStorage userPreferences;

    public FetchMessagesResult fetch(FetchMessagesAction action, User user) throws HupaException {
//        User user = getUser();
        // ImapFolder folder = action.getFolder();
        if (action.getFolder() == null || action.getFolder().getFullName() == null) {
            // folder = new
            // ImapFolderImpl(user.getSettings().getInboxFolderName());
            throw new IllegalArgumentException("why you want to ask us for messages in a null folder");
        }
        com.sun.mail.imap.IMAPFolder f = null;
        int start = action.getStart();
        int offset = action.getOffset();
        try {
//            IMAPStore store = cache.get(user);
        	IMAPStore store = imapStore.get(user);

            f = (com.sun.mail.imap.IMAPFolder) store.getFolder(action.getFolder().getFullName());

            // check if the folder is open, if not open it read only
            if (f.isOpen() == false) {
                f.open(com.sun.mail.imap.IMAPFolder.READ_ONLY);
            }

            // if the folder is empty we have no need to process
            int exists = f.getMessageCount();
            if (exists == 0) {
                return new FetchMessagesResultImpl(new ArrayList<com.a2m.mail.shared.domain.Message>(), start,
                        offset, 0, 0);
            }

            MessageConvertArray convArray = getMessagesToConvert(f, action);
            return new FetchMessagesResultImpl(convert(offset, f, convArray.getMesssages()), start, offset,
                    convArray.getRealCount(), f.getUnreadMessageCount());
        } catch (MessagingException e) {
//            logger.info("Error fetching messages in folder: " + action.getFolder().getFullName() + " " + e.getMessage());
            System.out.println("Error fetching messages in folder: " + action.getFolder().getFullName() + " " + e.getMessage());
            // Folder can not contain messages
            return new FetchMessagesResultImpl(new ArrayList<com.a2m.mail.shared.domain.Message>(), start, offset,
                    0, 0);
        } finally {
            if (f != null && f.isOpen()) {
                try {
                    f.close(false);
                } catch (MessagingException e) {
                    // we don't care to much about an exception on close here...
                }
            }
        }
    }

    protected MessageConvertArray getMessagesToConvert(IMAPFolder f, FetchMessagesAction action)
            throws MessagingException, HupaException {

        String searchString = action.getSearchString();
        int start = action.getStart();
        int offset = action.getOffset();
        int end = start + offset;
        Message[] messages;
        int exists;
        // check if a searchString was given, and if so use it
        if (searchString == null) {
            exists = f.getMessageCount();

            if (end > exists) {
                end = exists;
            }

            int firstIndex = exists - end + 1;
            if (firstIndex < 1) {
                firstIndex = 1;
            }
            int lastIndex = exists - start;

            messages = f.getMessages(firstIndex, lastIndex);
        } else {
            SearchTerm subjectTerm = new SubjectTerm(searchString);
            SearchTerm fromTerm = new FromStringTerm(searchString);
            SearchTerm bodyTerm = new BodyTerm(searchString);
            SearchTerm orTerm = new OrTerm(new SearchTerm[] { subjectTerm, fromTerm, bodyTerm });
            Message[] tmpMessages = f.search(orTerm);
             //test
            exists = tmpMessages.length;
            if (end > exists) {
                end = exists;
            }

            int firstIndex = exists - end + 1;
            if (firstIndex < 1) {
                firstIndex = 1;
            }
            int lastIndex = exists - start;
            
            if (tmpMessages.length >= firstIndex) {
            	List<Message> mList = new ArrayList<Message>();
            	for(int i = firstIndex-1; i< lastIndex; i++) {
            		mList.add(tmpMessages[i]);
            	}
            	messages = mList.toArray(new Message[mList.size()]);
            } else {
              messages = new Message[0];
            }
            
       ///////////////////////////////////////////////
//            if (end > tmpMessages.length) {
//                end = tmpMessages.length;
//            }
//            exists = tmpMessages.length;

//            int firstIndex = exists - end;
//            int firstIndex = 0;

//            if (tmpMessages.length > firstIndex) {
//                List<Message> mList = new ArrayList<Message>();
//                for (int i = firstIndex; i < tmpMessages.length; i++) {
//                    if (i == end)
//                        break;
//                    mList.add(tmpMessages[i]);
//                }
//                messages = mList.toArray(new Message[mList.size()]);
//            } else {
//                messages = new Message[0];
//            }
//            
//            if(tmpMessages.length == 0) {
//            	messages = new Message[0];
//            }

        }
//        logger.debug("Fetching messages for user: " + getUser() + " returns: " + messages.length + " messages in "
//                + f.getFullName());
        System.out.println("Fetching messages for user: " + " returns: " + messages.length + " messages in "
                + f.getFullName());

        return new MessageConvertArray(exists, messages);
    }


    public List<com.a2m.mail.shared.domain.Message> convert(int offset, com.sun.mail.imap.IMAPFolder folder,
            Message[] messages) throws MessagingException {
        List<com.a2m.mail.shared.domain.Message> mList = new ArrayList<com.a2m.mail.shared.domain.Message>();

        // Setup fetchprofile to limit the stuff which is fetched
        FetchProfile fp = getFetchProfile();
        folder.fetch(messages, fp);

        // loop over the fetched messages
        for (int i = 0; i < messages.length && i < offset; i++) {
        	com.a2m.mail.shared.domain.Message msg = new com.a2m.mail.shared.data.MessageImpl();
            Message message = messages[i];

            String from = null;
            if (message.getFrom() != null && message.getFrom().length > 0) {
                from = MessageUtils.decodeText(message.getFrom()[0].toString());
            }
            msg.setFrom(from);

            String replyto = null;
            if (message.getReplyTo() != null && message.getReplyTo().length > 0) {
                replyto = MessageUtils.decodeText(message.getReplyTo()[0].toString());
            }
            msg.setReplyto(replyto);

            ArrayList<String> to = new ArrayList<String>();
            // Add to addresses
            Address[] toArray = message.getRecipients(RecipientType.TO);
            if (toArray != null) {
                for (Address addr : toArray) {
                    String mailTo = MessageUtils.decodeText(addr.toString());
                    to.add(mailTo);
                }
            }
            msg.setTo(to);

            // Check if a subject exist and if so decode it
            String subject = message.getSubject();
            if (subject != null) {
                subject = MessageUtils.decodeText(subject);
            }
            msg.setSubject(subject);

            // Add cc addresses
            Address[] ccArray = message.getRecipients(RecipientType.CC);
            ArrayList<String> cc = new ArrayList<String>();
            if (ccArray != null) {
                for (Address addr : ccArray) {
                    String mailCc = MessageUtils.decodeText(addr.toString());
                    cc.add(mailCc);
                }
            }
            msg.setCc(cc);

//            userPreferences.addContact(from);
//            userPreferences.addContact(to);
//            userPreferences.addContact(replyto);
//            userPreferences.addContact(cc);

            // Using sentDate since received date is not useful in the view when
            // using fetchmail
            msg.setReceivedDate(message.getSentDate());

            // Add flags
            ArrayList<IMAPFlag> iFlags = JavamailUtil.convert(message.getFlags());

            ArrayList<Tag> tags = new ArrayList<Tag>();
            for (String flag : message.getFlags().getUserFlags()) {
                if (flag.startsWith(TagImpl.PREFIX)) {
                    tags.add(new TagImpl(flag.substring(TagImpl.PREFIX.length())));
                }
            }

            msg.setUid(folder.getUID(message));
            msg.setFlags(iFlags);
            msg.setTags(tags);
            try {
                msg.setHasAttachments(hasAttachment(message));
            } catch (MessagingException e) {
//                logger.debug("Unable to identify attachments in message UID:" + msg.getUid() + " subject:"
//                        + msg.getSubject() + " cause:" + e.getMessage());
//                logger.info("");
                System.out.println("Unable to identify attachments in message UID:" + msg.getUid() + " subject:"
                        + msg.getSubject() + " cause:" + e.getMessage());
            }
            mList.add(0, msg);

        }
        return mList;
    }

    protected FetchProfile getFetchProfile() {
        FetchProfile fp = new FetchProfile();
        fp.add(FetchProfile.Item.ENVELOPE);
        fp.add(FetchProfile.Item.FLAGS);
        fp.add(FetchProfile.Item.CONTENT_INFO);
        fp.add(UIDFolder.FetchProfileItem.UID);
        return fp;
    }

    private boolean hasAttachment(Message message) throws MessagingException {
        if (message.getContentType().startsWith("multipart/")) {
            try {
                Object content;

                content = message.getContent();

                if (content instanceof Multipart) {
                    Multipart mp = (Multipart) content;
                    if (mp.getCount() > 1) {
                        for (int i = 0; i < mp.getCount(); i++) {
                            String disp = mp.getBodyPart(i).getDisposition();
                            if (disp != null && disp.equalsIgnoreCase(Part.ATTACHMENT)) {
                                return true;
                            }
                        }
                    }

                }
            } catch (IOException e) {
                logger.error("Error while get content of message " + message.getMessageNumber());
            }

        }
        return false;
    }

    protected final class MessageConvertArray {
        private Message[] messages;
        private int realCount;

        public MessageConvertArray(int realCount, Message[] messages) {
            this.messages = messages;
            this.realCount = realCount;
        }

        public int getRealCount() {
            return realCount;
        }

        public Message[] getMesssages() {
            return messages;
        }
    }
}
