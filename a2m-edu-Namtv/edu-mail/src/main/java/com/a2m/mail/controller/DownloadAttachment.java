package com.a2m.mail.controller;

import java.io.InputStream;
import java.io.OutputStream;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.mail.server.service.IMAPStoreCache;
import com.a2m.mail.server.service.SessionService;
import com.a2m.mail.server.utils.MessageUtils;
import com.a2m.mail.shared.domain.User;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

@RestController
@RequestMapping("/mail")
public class DownloadAttachment {
	@Autowired
	SessionService session;

	@Autowired
	IMAPStoreCache imapStore;

	@GetMapping("/downloadAttachment")
	public void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = session.getCache().get(request.getParameter("USERNAME"));

		String message_uuid = (String) request.getParameter("uid");
		String attachmentName = (String) request.getParameter("name");
		String folderName = (String) request.getParameter("folder");
		String mode = (String) request.getParameter("mode");

		boolean inline = "inline".equals(mode);

		if (!inline) {
			response.setHeader("Content-disposition", "attachment; filename=" + attachmentName + "");
		}

		InputStream in = null;
		OutputStream out = response.getOutputStream();

		IMAPFolder folder = null;
		IMAPStore store = null;
		try {
			store = imapStore.get(user);
			folder = (com.sun.mail.imap.IMAPFolder) store.getFolder(folderName);
			// folder = (com.sun.mail.imap.IMAPFolder)
			// store.getFolder(folder.getFullName());
			if (folder.isOpen() == false) {
				folder.open(Folder.READ_ONLY);
			}
			Message m = folder.getMessageByUID(Long.parseLong(message_uuid));

			Object content = m.getContent();
			Part part = MessageUtils.handleMultiPart(null, content, attachmentName);
			if (part.getContentType() != null) {
				response.setContentType(part.getContentType());
				// header.setContentType(MediaType.parseMediaType(part.getContentType()));
			} else {
				response.setContentType("application/download");
				// header.setContentType(MediaType.parseMediaType("application/download"));
			}

			handleAttachmentData(m, attachmentName, part.getInputStream(), out);
			return;
		} catch (Exception e) {

		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
			if (folder != null) {
				try {
					folder.close(false);
				} catch (MessagingException e) {
				}
			}
		}
	}

	protected void handleAttachmentData(Message message, String attachmentName, InputStream is, OutputStream os)
			throws Exception {
		IOUtils.copy(is, os);
		IOUtils.closeQuietly(os);
	}
}
