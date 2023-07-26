package com.a2m.auth.controller;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.auth.model.response.UserResponse;
import com.a2m.auth.security.jwt.JwtUtils;
import com.a2m.auth.service.UserService;

@RestController
@RequestMapping(value = "api/userInfo")
public class UserInfoController {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserService tsstUserService;
	
	private static final String AUTHORIZATION = "authorization";
	
	@GetMapping(value = "getUserInfo")
	public ResponseEntity<?> getUserInfo(HttpServletRequest http){
		String token = getTokenFromRequest(http);
		//validation token
		if (jwtUtils.validateJwtToken(token)) {
			//Get userId from token request
			String username = jwtUtils.getUserNameFromJwtToken(token);
			UserResponse user = tsstUserService.getTsstUserByUserUid(username);
			return ResponseEntity.ok(user);
		}else {
			return ResponseEntity.ok(null);
		}
	}
	
	private String getTokenFromRequest(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
		String headerValue = "";
		while (headers.hasMoreElements()) {
			headerValue = headers.nextElement();
		}
		if (headerValue != null && ! headers.equals("")) {
			String els[] = headerValue.split(" ");
			if (els.length > 1) {
				return els[1];
			}
		}
		return "";
	}
	
}
