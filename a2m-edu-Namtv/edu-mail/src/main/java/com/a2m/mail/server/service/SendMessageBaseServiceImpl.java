package com.a2m.mail.server.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.a2m.mail.server.FileItemRegistry;
import com.a2m.mail.server.constant.MailConstant;
import com.a2m.mail.server.utils.MessageUtils;
import com.a2m.mail.server.utils.RegexPatterns;
import com.a2m.mail.server.utils.SessionUtils;
import com.a2m.mail.shared.SConsts;
import com.a2m.mail.shared.data.GenericResultImpl;
import com.a2m.mail.shared.data.MessageAttachmentImpl;
import com.a2m.mail.shared.domain.GenericResult;
import com.a2m.mail.shared.domain.MessageAttachment;
import com.a2m.mail.shared.domain.SendForwardMessageAction;
import com.a2m.mail.shared.domain.SendMessageAction;
import com.a2m.mail.shared.domain.SmtpMessage;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

@Service
public class SendMessageBaseServiceImpl extends AbstractService implements SendMessageService {

	@Autowired
	IMAPStoreCache imapStore;

	@Autowired
	SessionService session;

	@Autowired
	private SessionUtils sessionUtils;

	@Autowired
	private Environment env;

	@Override
	public GenericResult send(SendMessageAction action, boolean isResetAttachments, boolean isDraftMail) {
		GenericResult result = new GenericResultImpl();
		try {
			User user = session.getCache().get(action.getUsername());
			action.getMessage().setFrom(action.getUsername());
			Message message = createMessage(action);
			Message mess = fillBody(message, action);
			try {
				if (!isDraftMail) {
					String resultSend = sendMessage(user, mess);
					if (resultSend.equals(MailConstant.SEND_SUCCESS)) {
						result.setSuccess(true);
					} else {
						result.setError((String) resultSend + " : " + message.getAllRecipients()[0].toString());
					}
				} else {
					saveDraftMessage(user, message);
				}

			} catch (SendFailedException e) {
				result.setError("Error while parsing recipient: " + e.getMessage());
			}
			if (isResetAttachments)
				resetAttachments(action);
		} catch (AddressException e) {
			result.setError("Error while parsing recipient: " + e.getMessage());
		} catch (AuthenticationFailedException e) {
			result.setError("Error while sending message: SMTP Authentication error.");
		} catch (MessagingException e) {
			result.setError("Error while sending message: " + e.getMessage());
		} catch (Exception e) {
			result.setError("Unexpected exception while sendig message: " + e.getMessage());
		}
		return result;
	}

	/**
	 * Create basic Message which contains all headers. No body is filled
	 *
	 * @param session the Session
	 * @param action  the action
	 * @return message
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public Message createMessage(SendMessageAction action) throws AddressException, MessagingException {
		Properties properties = System.getProperties();

		User user = session.getCache().get(action.getUsername());
		// Setup mail server
//		properties.setProperty("mail.smtp.from", "smtp.atwom.com.vn");
		properties.setProperty("mail.smtp.host", user.getSettings().getSmtpServer());
		properties.setProperty("mail.smtp.auth", user.getSettings().getSmtpAuth() ? "true" : "false");
		properties.setProperty("mail.smtp.starttls.enable", user.getSettings().getSmtpSecure() ? "true" : "false");

		// Get the default Session object.
//		Session session = Session.getDefaultInstance(properties);
		Session mailSession = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user.getName(), user.getPassword());
			}

		});
		MimeMessage message = new MimeMessage(mailSession);
		SmtpMessage m = action.getMessage();
		message.setFrom(new InternetAddress(m.getFrom()));

//        userPreferences.addContact(m.getTo());
//        userPreferences.addContact(m.getCc());
//        userPreferences.addContact(m.getBcc());

		message.setRecipients(RecipientType.TO, MessageUtils.getRecipients(m.getTo()));
		message.setRecipients(RecipientType.CC, MessageUtils.getRecipients(m.getCc()));
		message.setRecipients(RecipientType.BCC, MessageUtils.getRecipients(m.getBcc()));
		message.setSentDate(new Date());
		message.addHeader("User-Agent:", "HUPA, The Apache JAMES webmail client.");
		message.addHeader("X-Originating-IP", getClientIpAddr());
		message.setSubject(m.getSubject(), "utf-8");
		message.setText(m.getText());
		updateHeaders(message, action);
		message.saveChanges();
		return message;
	}

	public static String getClientIpAddr() {
		HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
		String ip = "unknown";
		if (request != null) {
			ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		return ip;
	}

	protected void updateHeaders(MimeMessage message, SendMessageAction action) {
		if (action.getInReplyTo() != null) {
			try {
				message.addHeader(SConsts.HEADER_IN_REPLY_TO, action.getInReplyTo());
			} catch (MessagingException e) {
				logger.error("Error while setting header:" + e.getMessage(), e);
			}
		}
		if (action.getReferences() != null) {
			try {
				message.addHeader(SConsts.HEADER_REFERENCES, action.getReferences());
			} catch (MessagingException e) {
				logger.error("Error while setting header:" + e.getMessage(), e);
			}
		}
	}

	/**
	 * Fill the body of the given message with data which the given action contain
	 *
	 * @param message the message
	 * @param action  the action
	 * @return filledMessage
	 * @throws MessagingException
	 * @throws IOException
	 * @throws HupaException
	 */
	public Message fillBody(Message message, SendMessageAction action)
			throws MessagingException, IOException, HupaException {

		String html = restoreInlineLinks(action.getMessage().getText());

		// TODO: client sends the message as a html document right now,
		// the idea is that it should be sent in both formats because
		// it is easier to handle html in the browser.
		String text = htmlToText(html);

		List items = getAttachments(action);

		return composeMessage(message, text, html, items);
	}

	protected String restoreInlineLinks(String s) {
		return RegexPatterns.replaceAll(s, RegexPatterns.regex_revertInlineImg, RegexPatterns.repl_revertInlineImg);
	}

	protected String htmlToText(String s) {
		s = s.replaceAll("\n", " ");
		s = s.replaceAll("(?si)<br\\s*?/?>", "\n");
		s = s.replaceAll("(?si)</div\\s*?>", "\n");
		s = s.replaceAll("(\\w)<.*?>(\\w)", "$1 $2");
		s = s.replaceAll("<.*?>", "");
		s = s.replaceAll("[ \t]+", " ");
		return s;
	}

	/**
	 * Get the attachments stored in the registry.
	 *
	 * @param action
	 * @return A list of stored attachments
	 * @throws HupaException
	 */
	protected List getAttachments(SendMessageAction action) throws MessagingException, HupaException {

		FileItemRegistry registry = sessionUtils.getSessionRegistry();
		List<MessageAttachmentImpl> attachments = action.getMessage().getMessageAttachments();

		List<File> items = new ArrayList<File>();
		if (attachments != null && attachments.size() > 0) {
			for (MessageAttachment attachment : attachments) {
				File fItem = (File) registry.get(attachment.getName());
				if (fItem != null)
					items.add(fItem);
			}
		}
		return items;
	}

//    protected List getForwardAttachments(SendMessageAction action) throws MessagingException, HupaException {
//        SendForwardMessageAction forwardAction = (SendForwardMessageAction)action;
//        List<?> items = new ArrayList();
//        User user = session.getCache().get(action.getUsername());
//        IMAPStore store = cache.get(user);
//        //IMAPStore store = cache.get(getUser());
//
//        IMAPFolder folder = (IMAPFolder) store.getFolder(forwardAction.getFolder().getFullName());
//        if (folder.isOpen() == false) {
//            folder.open(Folder.READ_ONLY);
//        }
//        // Put the original attachments in the list
//        Message msg = folder.getMessageByUID(forwardAction.getUid());
//        try {
//            items = MessageUtils.extractMessageAttachments(logger, msg.getContent());
//            logger.debug("Forwarding a message, extracted: " + items.size() + " from original.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // Put in the list the attachments uploaded by the user
//        items.addAll(getAttachments(forwardAction));
//        return items;
//    }

	/**
	 * Fill the body of a message already created. The result message depends on the
	 * information given.
	 *
	 * @param message
	 * @param text
	 * @param html
	 * @param parts
	 * @return The composed message
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static Message composeMessage(Message message, String text, String html, List<File> parts)
			throws MessagingException, IOException {

		MimeBodyPart txtPart = null;
		MimeBodyPart htmlPart = null;
		MimeMultipart mimeMultipart = null;

		if (text == null && html == null) {
			text = "";
		}
		if (text != null) {
			txtPart = new MimeBodyPart();
			txtPart.setContent(text, "text/plain; charset=UTF-8");
		}
		if (html != null) {
			htmlPart = new MimeBodyPart();
			htmlPart.setContent(html, "text/html; charset=UTF-8");
		}
		if (html != null && text != null) {
			mimeMultipart = new MimeMultipart();
			mimeMultipart.setSubType("alternative");
			mimeMultipart.addBodyPart(txtPart);
			mimeMultipart.addBodyPart(htmlPart);
		}

		if (parts == null || parts.isEmpty()) {
			if (mimeMultipart != null) {
				message.setContent(mimeMultipart);
			} else if (html != null) {
				message.setText(html);
				message.setHeader("Content-type", "text/html");
			} else if (text != null) {
				message.setText(text);
			}
		} else {
			MimeBodyPart bodyPart = new MimeBodyPart();
			if (mimeMultipart != null) {
				bodyPart.setContent(mimeMultipart);
			} else if (html != null) {
				bodyPart.setText(html);
				bodyPart.setHeader("Content-type", "text/html");
			} else if (text != null) {
				bodyPart.setText(text);
			}
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			for (File attachment : parts) {
				if (attachment instanceof File) {
					multipart.addBodyPart(MessageUtils.fileitemToBodypart(attachment));
				} else {
//					multipart.addBodyPart((BodyPart) attachment);
				}
			}
			message.setContent(multipart);
		}

		message.saveChanges();
		return message;

	}

	/**
	 * Send the message using SMTP, if the configuration uses authenticated SMTP, it
	 * uses the user stored in session to get the given login and password.
	 *
	 * @param user
	 * @param session
	 * @param message
	 * @throws MessagingException
	 */
	protected String sendMessage(User user, Message message) {
		return imapStore.sendMessage(message);
	}

	/**
	 * Save the message in the sent folder
	 *
	 * @param user
	 * @param message
	 * @throws MessagingException
	 * @throws IOException
	 */
	protected void saveSentMessage(User user, Message message) throws MessagingException, IOException {
		IMAPStore iStore = cache.get(user);
		IMAPFolder folder = (IMAPFolder) iStore.getFolder(user.getSettings().getSentFolderName());

		if (folder.exists() || folder.create(IMAPFolder.READ_WRITE)) {
			if (folder.isOpen() == false) {
				folder.open(Folder.READ_WRITE);
			}

			// It is necessary to copy the message, before putting it
			// in the sent folder. If not, it is not guaranteed that it is
			// stored in ascii and is not possible to get the attachments
			// size. message.saveChanges() doesn't fix the problem.
			// There are tests which demonstrate this.
			message = new MimeMessage((MimeMessage) message);

			message.setFlag(Flag.SEEN, true);
			folder.appendMessages(new Message[] { message });

			try {
				folder.close(false);
			} catch (MessagingException e) {
				// we don't care on close
			}
		}
	}

	/**
	 * Remove attachments from the registry
	 *
	 * @param action
	 * @throws MessagingException
	 * @throws ActionException
	 */
	protected void resetAttachments(SendMessageAction action) throws MessagingException {
		File uploadRootDir = null;
		final String rootPath = env.getProperty("dir.upload.temp");
		SmtpMessage msg = action.getMessage();
		List<MessageAttachmentImpl> attachments = msg.getMessageAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			for (MessageAttachment attach : attachments) {
				uploadRootDir = sessionUtils.getSessionRegistry().get(attach.getName());
				sessionUtils.getSessionRegistry().remove(attach.getName());
				if (uploadRootDir.exists()) {
					uploadRootDir.delete();
				}
			}
		}
	}

	protected void saveDraftMessage(User user, Message message) throws MessagingException, IOException {
		IMAPStore iStore = imapStore.get(user);
		IMAPFolder folder = (IMAPFolder) iStore.getFolder(user.getSettings().getDraftsFolderName());

		if (folder.exists() || folder.create(IMAPFolder.READ_WRITE)) {
			if (folder.isOpen() == false) {
				folder.open(Folder.READ_WRITE);
			}

			// It is necessary to copy the message, before putting it
			// in the sent folder. If not, it is not guaranteed that it is
			// stored in ascii and is not possible to get the attachments
			// size. message.saveChanges() doesn't fix the problem.
			// There are tests which demonstrate this.
			message = new MimeMessage((MimeMessage) message);

			message.setFlag(Flag.DRAFT, true);
			folder.appendMessages(new Message[] { message });

			try {
				folder.close(false);
			} catch (MessagingException e) {
				// we don't care on close
			}
		}
	}

}
