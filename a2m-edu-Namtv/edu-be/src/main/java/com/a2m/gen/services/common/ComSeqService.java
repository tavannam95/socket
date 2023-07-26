package com.a2m.gen.services.common;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.repository.common.ComSeqRepository;

@Service
public class ComSeqService {
	
	@Autowired
	private ComSeqRepository repo;
	
	public String getSeq(String seqName) throws SQLException{
		repo.setSeq(seqName);
		return repo.getSeq();
	}
}
