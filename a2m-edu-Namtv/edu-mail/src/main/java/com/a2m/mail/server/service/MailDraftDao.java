package com.a2m.mail.server.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailDraftDao {
	public int saveMailSggt(Map<String , Object> arg) throws SQLException;
	public List<Map<String, Object>> getMailSggtList(Map<String, Object> arg) throws SQLException;
}
