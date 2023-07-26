package com.a2m.mail.server.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailDraftService {
	@Autowired
	MailDraftDao mailDraftDao;
	
	public int saveMailSggt(Map<String , Object> arg) throws SQLException {
		return mailDraftDao.saveMailSggt(arg);
	}
	public List<Map<String, Object>> getMailSggtList(Map<String, Object> arg) throws SQLException{
		return mailDraftDao.getMailSggtList(arg);
	}
}
