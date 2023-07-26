package com.a2m.mail.server.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.mail.shared.dbinteract.util.HashUtil;

@Service
public class UserService {
	@Autowired
	UserDao dao;

	@Value("${mail.password.hash}")
	private String hash;

	@Value("${mail.password.version}")
	private int version;

	public List getAllUser() throws Exception {
		return dao.getAllUser();
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateUser(Map<Object, Object> map) throws Exception {
		int returnId = 0;
		Map request = new HashMap();
		request.put("version", version);
		request.put("userName", map.get("mail").toString());
		request.put("passwordHashAlgorithm", hash);

		String newPwd = map.get("newPwd").toString();
		String hashNewPwd = HashUtil.hashPassword(newPwd, hash);
		request.put("password", hashNewPwd);

		returnId = dao.updateUser(request);

		return returnId;
	}

	@Transactional(rollbackFor = Exception.class)
	public int insertUser(Map<Object, Object> map) {

		int returnId = 0;
		String newPwd = map.get("pwd").toString();
		try {
			executeCommandInsert(map.get("mail").toString(), newPwd);
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnId;
	}

	private void executeCommandInsert(String email, String pwd) throws IOException {
		String cmd = "cd $JAMES_HOME/bin && ./james-cli.sh -h localhost -p 9999 adduser " + email + " " + pwd;
		Runtime rt = Runtime.getRuntime();
		rt.exec(new String[] { "sh", "-c", cmd });
	}

}
