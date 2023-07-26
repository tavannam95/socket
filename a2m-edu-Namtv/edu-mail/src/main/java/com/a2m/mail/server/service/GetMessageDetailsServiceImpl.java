
package com.a2m.mail.server.service;

import static com.a2m.mail.server.utils.RegexPatterns.regex_badAttrs;
import static com.a2m.mail.server.utils.RegexPatterns.regex_badTags;
import static com.a2m.mail.server.utils.RegexPatterns.regex_email;
import static com.a2m.mail.server.utils.RegexPatterns.regex_existingEmailLinks;
import static com.a2m.mail.server.utils.RegexPatterns.regex_existingHttpLinks;
import static com.a2m.mail.server.utils.RegexPatterns.regex_gt;
import static com.a2m.mail.server.utils.RegexPatterns.regex_htmllink;
import static com.a2m.mail.server.utils.RegexPatterns.regex_inlineImg;
import static com.a2m.mail.server.utils.RegexPatterns.regex_lt;
import static com.a2m.mail.server.utils.RegexPatterns.regex_nl;
import static com.a2m.mail.server.utils.RegexPatterns.regex_unneededTags;
import static com.a2m.mail.server.utils.RegexPatterns.repl_badAttrs;
import static com.a2m.mail.server.utils.RegexPatterns.repl_badTags;
import static com.a2m.mail.server.utils.RegexPatterns.repl_email;
import static com.a2m.mail.server.utils.RegexPatterns.repl_existingHttpLinks;
import static com.a2m.mail.server.utils.RegexPatterns.repl_existngEmailLinks;
import static com.a2m.mail.server.utils.RegexPatterns.repl_gt;
import static com.a2m.mail.server.utils.RegexPatterns.repl_htmllink;
import static com.a2m.mail.server.utils.RegexPatterns.repl_inlineImg;
import static com.a2m.mail.server.utils.RegexPatterns.repl_lt;
import static com.a2m.mail.server.utils.RegexPatterns.repl_nl;
import static com.a2m.mail.server.utils.RegexPatterns.repl_unneededTags;
import static com.a2m.mail.server.utils.RegexPatterns.replaceAll;
import static com.a2m.mail.server.utils.RegexPatterns.replaceAllRecursive;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.mail.server.utils.MessageUtils;
import com.a2m.mail.shared.data.MailHeaderImpl;
import com.a2m.mail.shared.data.MessageDetailsImpl;
import com.a2m.mail.shared.domain.GetMessageDetailsAction;
import com.a2m.mail.shared.domain.ImapFolder;
import com.a2m.mail.shared.domain.MessageAttachment;
import com.a2m.mail.shared.domain.MessageDetails;
import com.a2m.mail.shared.domain.User;
import com.sun.mail.imap.IMAPStore;

@Service
public class GetMessageDetailsServiceImpl implements GetMessageDetailsService {

	@Autowired
	private SessionService session;

	@Autowired
	IMAPStoreCache imapStore;

	public MessageDetails get(GetMessageDetailsAction action, User user) throws Exception {
		// return new
		// GetMessageDetailsResultImpl(exposeMessage(session.getCache().get(user.getName()),
		// action.getFolder(), action.getUid()));
		// User user = session.getCache().get(user.getName());
		return (MessageDetails) exposeMessage(user, action.getFolder(), action.getUid());
	}

	protected MessageDetails exposeMessage(User user, ImapFolder folder, long uid) throws Exception {
		IMAPStore store = null;
		com.sun.mail.imap.IMAPFolder f = null;
		try {
			store = imapStore.get(user);

			f = (com.sun.mail.imap.IMAPFolder) store.getFolder(folder.getFullName());

			if (f.isOpen() == false) {
				f.open(com.sun.mail.imap.IMAPFolder.READ_WRITE);
			}

			MimeMessage message = (MimeMessage) f.getMessageByUID(uid);

			MessageDetails mDetails = mimeToDetails(message, f.getFullName(), uid, user);

			mDetails.setUid(uid);

			f.setFlags(new Message[] { message }, new Flags(Flag.SEEN), true);

			return mDetails;
		} catch (Exception e) {
			/*
			 * logger.error("Unable to expose msg for user " + user + " in folder " + folder
			 * + " with uid " + uid, e); throw new
			 * Exception("Unable to expose msg for user " + user + " in folder " + folder +
			 * " with uid " + uid);
			 */

		} finally {
			if (f != null && f.isOpen()) {
				try {
					f.close(false);
				} catch (MessagingException e) {
					// ignore on close
				}
			}
		}
		return null;
	}

	protected MessageDetails mimeToDetails(MimeMessage message, String folderName, long uid, User user)
			throws IOException, MessagingException, UnsupportedEncodingException {
		MessageDetails mDetails = new MessageDetailsImpl();

		Object content = message.getContent();

		StringBuffer sbPlain = new StringBuffer();
		
		Address[] ccArray = message.getRecipients(RecipientType.CC);
		ArrayList<String> cc = new ArrayList<String>();
		if (ccArray != null) {
			for (Address addr : ccArray) {
				String mailCc = MessageUtils.decodeText(addr.toString());
				cc.add(mailCc);
			}
		}
		mDetails.setCc(cc);

		ArrayList<MessageAttachment> attachmentList = new ArrayList<MessageAttachment>();

		boolean isHTML = MessageUtils.handleParts(message, content, sbPlain, attachmentList);

		if (isHTML) {
			mDetails.setText(filterHtmlDocument(sbPlain.toString(), folderName, uid));
		} else {
			mDetails.setText(txtDocumentToHtml(sbPlain.toString(), folderName, uid));
		}

		mDetails.setMessageAttachments(attachmentList);

		for (@SuppressWarnings("unchecked")
		Enumeration<Header> en = message.getAllHeaders(); en.hasMoreElements();) {
			Header header = en.nextElement();
			if (header.getName().toLowerCase().matches("^(x-.*|date|from|cc|to|subject)$")) {
				mDetails.getMailHeaders().add(new MailHeaderImpl(header.getName(), header.getValue()));
				if (header.getName().equalsIgnoreCase("cc")) {
					mDetails.getMailHeaders().add(new MailHeaderImpl(header.getName(), MessageUtils.decodeText(header.getValue())));
				}
			}
		}

		return mDetails;
	}

	protected String txtDocumentToHtml(String txt, String folderName, long uuid) {

		if (txt == null || txt.length() == 0)
			return txt;

		// escape html tags symbols
		txt = replaceAll(txt, regex_lt, repl_lt);
		txt = replaceAll(txt, regex_gt, repl_gt);

		// enclose between <a> http links and emails
		txt = replaceAll(txt, regex_htmllink, repl_htmllink);
		txt = replaceAll(txt, regex_email, repl_email);

		// put break lines
		txt = replaceAll(txt, regex_nl, repl_nl);

		txt = filterHtmlDocument(txt, folderName, uuid);

		return txt;
	}

	protected String filterHtmlDocument(String html, String folderName, long uuid) {

		if (html == null || html.length() == 0)
			return html;

		// Replace in-line images links to use Hupa's download servlet
		html = replaceAll(html, regex_inlineImg, repl_inlineImg).replaceAll("%%FOLDER%%", folderName)
				.replaceAll("%%UID%%", String.valueOf(uuid));
		// Remove head, script and style tags to avoid interferences with Hupa
		html = replaceAll(html, regex_badTags, repl_badTags);
		// Remove body and html tags
		html = replaceAll(html, regex_unneededTags, repl_unneededTags);
		// Remove all onClick attributes
		html = replaceAllRecursive(html, regex_badAttrs, repl_badAttrs);
		html = replaceAll(html, regex_existingHttpLinks, repl_existingHttpLinks);

		// FIXME: These have serious performance problems (see
		// testMessageDetails_Base64Image_Performance)
		// Add <a> tags to links which are not already into <a>
		// html = replaceAll(html, regex_orphandHttpLinks,
		// repl_orphandHttpLinks);
		// Add javascript method to <a> in order to open links in a different
		// window
		// Add <a> tags to emails which are not already into <a>
		// html = replaceAll(html, regex_orphandEmailLinks,
		// repl_orphandEmailLinks);
		// Add a js method to mailto links in order to compose new mails inside
		// hupa
		html = replaceAll(html, regex_existingEmailLinks, repl_existngEmailLinks);

		return html;
	}
}
