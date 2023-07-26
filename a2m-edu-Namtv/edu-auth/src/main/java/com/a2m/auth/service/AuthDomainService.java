package com.a2m.auth.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.auth.dao.AuthDomainDao;

@Service
public class AuthDomainService {
	@Autowired
	private AuthDomainDao domainDao;
	
	public boolean existsByDomain(String domainName) throws SQLException {
		if (domainDao.existsByDomain(domainName)> 0) {
			return true;
		}
		return false;
	}
}
