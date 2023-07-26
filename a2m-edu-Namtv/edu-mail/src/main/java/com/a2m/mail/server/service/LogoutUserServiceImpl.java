package com.a2m.mail.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.mail.shared.exception.HupaException;

@Service
public class LogoutUserServiceImpl extends AbstractService implements LogoutUserService {

	@Autowired
	private SessionService session;
	
	@Autowired
	IMAPStoreCache imapStore;
	
	@Override
	public void logout(String username) {
		
		try {
			imapStore.delete(username);
			
			session.getCache().remove(username);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	
   
}
