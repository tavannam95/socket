package com.a2m.mail.shared.data;

import org.springframework.stereotype.Service;

import com.a2m.mail.shared.domain.CRUDUserActionProxy;
import com.a2m.mail.shared.domain.JamesUserProxy;

@Service
public class CRUDUserActionImpl implements CRUDUserActionProxy {

	private JamesUserProxy user;
	
	@Override
	public void setUser(JamesUserProxy user) {
		this.user = user;
	}
	
	@Override
	public JamesUserProxy getUser() {
		return user;
	}

}
