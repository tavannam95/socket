package com.a2m.mail.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.mail.server.service.UserService;
import com.a2m.mail.shared.data.JamesUser;
import com.a2m.mail.shared.domain.CRUDUserActionProxy;
import com.a2m.mail.shared.domain.JamesUserProxy;

@RestController
@RequestMapping("/api/mail")
public class UserController {

	@Autowired
	CRUDUserActionProxy action;

	@Autowired
	UserService userService;

	// update password
	@PostMapping("doUpdateUser")
	@ResponseBody
	public Object doUpdateUser(@RequestBody @Valid Map request) {

		Map mapResponse = new HashMap();

		try {
			int returnUpdateId = 0;
			returnUpdateId = userService.updateUser(request);

			if (returnUpdateId > 0) {
				mapResponse.put("STATUS", "SUCCESS");
			} else {
				mapResponse.put("STATUS", "FAIL");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mapResponse.put("STATUS", "FAIL");
			e.printStackTrace();
		}

		return mapResponse;
	}

	@GetMapping("getAllUser")
	@ResponseBody
	public List getAllUser() {

		Map mapResponse = new HashMap();
		List list = new ArrayList();

		try {
			list = userService.getAllUser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@PostMapping("doInsertUser")
	@ResponseBody
	public Object doInsertUser(@RequestBody @Valid Map request) {

		Map mapResponse = new HashMap();

		try {
			int returnUpdateId = 0;
			returnUpdateId = userService.insertUser(request);

			if (returnUpdateId > 0) {
				mapResponse.put("STATUS", "SUCCESS");
			} else {
				mapResponse.put("STATUS", "FAIL");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mapResponse.put("STATUS", "FAIL");
			e.printStackTrace();
		}

		return mapResponse;
	}

}