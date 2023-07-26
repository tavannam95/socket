package com.a2m.mail.shared.domain;

public interface JamesUserProxy {

	String getUserName();

	void setUserName(String userName);

	String getPasswordHashAlgorithm();

	void setPasswordHashAlgorithm(String passwordHashAlgorithm);

	String getPassword();

	void setPassword(String password);

	int getVersion();

	void setVersion(int version);

}
