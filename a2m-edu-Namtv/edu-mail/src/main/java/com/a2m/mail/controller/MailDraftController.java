package com.a2m.mail.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.mail.server.service.MailDraftService;

@RestController
@RequestMapping(value = "/api/draft")
public class MailDraftController {
	@Autowired
	MailDraftService mailDraftService;
	
	@PostMapping(value = "saveMailSggt")
	public int saveMailSggt(@RequestBody Map<String , Object> arg) throws SQLException {
		return mailDraftService.saveMailSggt(arg);
	}
	
	@GetMapping(value = "getMailSggtList")
	public List<Map<String, Object>> getMailSggtList(@RequestParam Map<String, Object> arg) throws SQLException{
		return mailDraftService.getMailSggtList(arg);
	}
}
