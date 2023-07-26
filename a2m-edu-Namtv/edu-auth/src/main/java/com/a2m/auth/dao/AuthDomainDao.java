package com.a2m.auth.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthDomainDao {
	long existsByDomain(String domainName) throws SQLException;
}
