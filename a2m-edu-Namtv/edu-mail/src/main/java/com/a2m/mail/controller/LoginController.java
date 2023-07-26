package com.a2m.mail.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.mail.server.service.LoginUserService;
import com.a2m.mail.server.service.LogoutUserService;
import com.a2m.mail.server.utils.AuthenticationUtil;
import com.a2m.mail.shared.data.SettingsImpl;
import com.a2m.mail.shared.data.UserImpl;
import com.a2m.mail.shared.domain.Settings;
import com.a2m.mail.shared.domain.User;

@RestController
@RequestMapping("/api/mail")
public class LoginController {
	
	@Value("${imap.server}")
	private String imapServer;
	
	@Value("${imap.port}")
	private int imapPort;
	
	@Value("${imap.secure}")
	private Boolean imapSecure;
	
	@Value("${smtp.server}")
	private String smtpServer;
	
	@Value("${smtp.port}")
	private int smtpPort;
	
	@Value("${smtp.secure}")
	private Boolean smtpSecure;
	
	@Value("${smtp.auth}")
	private Boolean smtpAuth;
	
	@Autowired
	private LoginUserService loginService;
	
	@Autowired
	private LogoutUserService logoutService;
	
	@PostMapping("doLoginMailServer")
	@ResponseBody
	public Object doLogin(@RequestBody @Valid Map request) {
		//LoginUserService loginService=  new LoginUserServiceImpl();
		Map mapResponse = new HashMap();
		String username = (String) request.get("USERNAME");
		String password = (String) request.get("PASSWORD");
		
		User user = new UserImpl();
		
		//config setting for mail server
		Settings setting = new SettingsImpl();
		setting.setImapServer(imapServer);
		setting.setImapPort(imapPort);
		setting.setSmtpServer(smtpServer);
		setting.setSmtpPort(smtpPort);
		setting.setSmtpSecure(smtpSecure);
		setting.setImapSecure(imapSecure);
		setting.setSmtpAuth(smtpAuth);
		
		user = loginService.login(username, password, setting);
		
		if(user != null) {
			mapResponse.put("STATUS", "SUCCESS");
		}else {
			mapResponse.put("STATUS", "FAIL");
		}
		
		return mapResponse;
	}
	
	@PostMapping("/doLogoutMailServer")
	@ResponseBody
	public void doLogout(@RequestBody @Valid Map request) {
		
		String userName = AuthenticationUtil.getUsername();
		if (userName == null) {
			return;
		}
		
		System.out.println("Do logout user: " + userName);
		
//		String username = (String) request.get("USERNAME");
		logoutService.logout(userName);
	}
}