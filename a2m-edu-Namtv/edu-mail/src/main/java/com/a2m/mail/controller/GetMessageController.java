package com.a2m.mail.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.mail.server.service.FetchMessagesService;
import com.a2m.mail.server.service.SessionService;
import com.a2m.mail.server.utils.AuthenticationUtil;
import com.a2m.mail.shared.data.FetchMessagesResultImpl;
import com.a2m.mail.shared.domain.FetchMessagesAction;
import com.a2m.mail.shared.domain.FetchMessagesResult;
import com.a2m.mail.shared.domain.ImapFolder;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;

@RestController
@RequestMapping("/api/mail")
public class GetMessageController {

	@Autowired
	SessionService session;

	@Autowired
	FetchMessagesService fetchMessageService;

	@Autowired
	FetchMessagesAction action;

	@Autowired
	ImapFolder imapFolder;

	@GetMapping("getMessagesByFolder")
	@ResponseBody
	public Object getMessages(@RequestParam Map request) {
		
		Map mapResponse = new HashMap();
		
		String userName = AuthenticationUtil.getUsername();
		if(userName == null) {
			return mapResponse;
		}

		User user = session.getCache().get(userName);

		String folderFullName = (String) request.get("FOLDER_NM");
		String search = (String) request.get("SEARCH");
		int start = Integer.parseInt((String) request.get("START"));
		int offset = Integer.parseInt((String) request.get("OFFSET"));

		imapFolder.setFullName(folderFullName);

		action.setFolder(imapFolder);
		action.setOffset(offset);
		action.setSearchString(search);
		action.setStart(start);

		try {
			FetchMessagesResult result = new FetchMessagesResultImpl();
			result = fetchMessageService.fetch(action, user);
			System.out.println("result: " + result);

			return result;
		} catch (HupaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return mapResponse;
		}

	}

}
