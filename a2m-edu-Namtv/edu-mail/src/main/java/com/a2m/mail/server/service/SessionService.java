package com.a2m.mail.server.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.a2m.mail.shared.domain.User;

@Service
public class SessionService {

	private final Map<String, User> cache = new HashMap<>();
	
	public Map<String, User> getCache() {
		return cache;
	}
	
}
