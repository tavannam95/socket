package com.a2m.mail.controller;

/**
 * @author tiennd
 *
 * @created 2022
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.mail.server.service.IReadAndUnReadService;
import com.a2m.mail.server.service.SessionService;
import com.a2m.mail.server.utils.AuthenticationUtil;
import com.a2m.mail.shared.domain.ImapFolder;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;

@RestController
@RequestMapping("/api/mail")
public class ReadAndUnReadController {

	@Autowired
	IReadAndUnReadService iReadAndUnReadService;

	@Autowired
	SessionService session;

	@Autowired
	ImapFolder imapFolder;

	@PostMapping("markAsReadOrUnReadController")
	@ResponseBody
	public Object markAsReadOrUnReadController(@RequestBody @Valid Map request) {
		Map mapResponse = new HashMap();
		
		String userName = AuthenticationUtil.getUsername();
		if (userName == null) {
			return mapResponse;
		}

		User user = session.getCache().get(userName);
		String folderFullName = (String) request.get("FOLDER_NM");
		List uids = (List) request.get("UIDS");
		String type = (String) request.get("TYPE");

		imapFolder.setFullName(folderFullName);

		try {
			iReadAndUnReadService.markReadOrUnRead(user, imapFolder, uids, type);
			mapResponse.put("STATUS", "SUCCESS");
		} catch (HupaException e) {
			// TODO Auto-generated catch block
			mapResponse.put("STATUS", "FAIL");
			e.printStackTrace();
		}

		return mapResponse;
	}

}
