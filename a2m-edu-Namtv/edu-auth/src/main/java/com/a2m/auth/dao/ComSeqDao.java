package com.a2m.auth.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComSeqDao {
	
	Object setSeq(String seqName) throws SQLException;
	
	String getSeq() throws SQLException;
}
