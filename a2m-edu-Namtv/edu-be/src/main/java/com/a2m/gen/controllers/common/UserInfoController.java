package com.a2m.gen.controllers.common;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.dto.TsstUserDto;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.UserInfoService;

@RestController
@Validated
@RequestMapping(value = "/api/userInfo")
public class UserInfoController {

	@Autowired
	private UserInfoService service;
	@Autowired
	private CommonService commonService;

	private static final String AUTHORIZATION = "authorization";

	@GetMapping(value = "/getUserInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getUserInfo(HttpServletRequest request) {

		// get User UID from access token in header of httpRequest
		String userUid = "";
		String token = getTokenFromRequest(request);
		try {
			userUid = commonService.getUserUidFromToken(token);
		} catch (Exception ee) {
			userUid = "";
		}

		// get user info by User UID
//		TsstUserDto res = new TsstUserDto();
		Map<String, Object> res = new HashMap<>();
		try {
			res = service.getUserInfo(userUid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
		String headerValue = "";
		while (headers.hasMoreElements()) {
			headerValue = headers.nextElement();
		}
		if (headerValue != null && !"".equals(headerValue)) {
			String els[] = headerValue.split(" ");
			if (els.length > 1) {
				return els[1];
			}
		}
		return "";
	}

}
