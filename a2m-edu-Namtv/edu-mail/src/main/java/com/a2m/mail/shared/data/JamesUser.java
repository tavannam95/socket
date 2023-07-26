package com.a2m.mail.shared.data;

import org.springframework.stereotype.Service;

import com.a2m.mail.shared.domain.JamesUserProxy;

@Service
public class JamesUser implements JamesUserProxy {
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String passwordHashAlgorithm;
	private String password;
	private int version;
	
	public JamesUser() {
		super();
	}

	public JamesUser(String userName, String passwordHashAlgorithm, String password, int version) {
		super();
		this.userName = userName;
		this.passwordHashAlgorithm = passwordHashAlgorithm;
		this.password = password;
		this.version = version;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPasswordHashAlgorithm() {
		return passwordHashAlgorithm;
	}

	@Override
	public void setPasswordHashAlgorithm(String passwordHashAlgorithm) {
		this.passwordHashAlgorithm = passwordHashAlgorithm;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "JamesUser [userName=" + userName + ", passwordHashAlgorithm=" + passwordHashAlgorithm + ", password="
				+ password + ", version=" + version + "]";
	}
	
}
