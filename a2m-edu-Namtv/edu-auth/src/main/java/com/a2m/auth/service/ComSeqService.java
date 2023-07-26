package com.a2m.auth.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.auth.dao.ComSeqDao;

@Service
public class ComSeqService {
	@Autowired
	private ComSeqDao dao;
	
	public String getSeq(String seqName) throws SQLException{
		dao.setSeq(seqName);
		return dao.getSeq();
	}
}
