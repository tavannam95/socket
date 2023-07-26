package com.a2m.mail.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.mail.server.service.GetMessageDetailsService;
import com.a2m.mail.server.service.SessionService;
import com.a2m.mail.server.utils.AuthenticationUtil;
import com.a2m.mail.shared.data.MailHeaderImpl;
import com.a2m.mail.shared.data.MessageDetailsImpl;
import com.a2m.mail.shared.domain.GetMessageDetailsAction;
import com.a2m.mail.shared.domain.ImapFolder;
import com.a2m.mail.shared.domain.MailHeader;
import com.a2m.mail.shared.domain.MessageAttachment;
import com.a2m.mail.shared.domain.MessageDetails;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;

@RestController
@RequestMapping("/api/mail")
public class MessageDetailController {
	@Autowired
	SessionService session;

	@Autowired
	GetMessageDetailsService getMessageDetailsService;

	@Autowired
	GetMessageDetailsAction action;

	@Autowired
	ImapFolder imapFolder;

	@GetMapping("/getMessageDetailByUid")
	@ResponseBody
	public Object getMessageDetailByUid(@RequestParam Map request) throws Exception {

		Map mapResponse = new HashMap();

		String userName = AuthenticationUtil.getUsername();
		if (userName == null) {
			return mapResponse;
		}

		User user = session.getCache().get(userName);

		String folderFullName = (String) request.get("FOLDER_NM");

		imapFolder.setFullName(folderFullName);

		action.setFolder(imapFolder);
		action.setUid(Long.parseLong((String) request.get("uid")));

		try {
			MessageDetails result = new MessageDetailsImpl();
			result = (MessageDetails) getMessageDetailsService.get(action, user);
			System.out.println("result: " + result);
			List<MailHeader> mailsHeader = result.getMailHeaders();

			for (MailHeader s : mailsHeader) {
				if (s.getName().equals("Subject") || s.getName().equals("From") || s.getName().equals("To")) {
					s.setValue(MimeUtility.decodeText(s.getValue()));
				}
			}

			return result;
		} catch (HupaException e) {
			// TODO Auto-generated cnullatch block
			e.printStackTrace();
			return null;
		}

	}

}
