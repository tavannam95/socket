package com.a2m.auth.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.auth.model.response.UserResponse;
import com.a2m.auth.service.MailService;
import com.a2m.auth.service.UserService;
import com.a2m.auth.util.AjaxResult;

@RestController
@RequestMapping(value = "api/auth/mail")
public class MailController {
	private static Logger logger = LoggerFactory.getLogger(MailController.class);
	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "sentVerifyCode")
	public ResponseEntity<?> sentVerifyCode(@RequestParam Map<String, Object> arg) throws FileNotFoundException{
		AjaxResult result = new AjaxResult();
		UserResponse user = userService.getTsstUserByUserUid(arg.get("userUid").toString());
		if (user != null) {
			if (mailService.sentVerificationCode(user.getEmail(),user.getEmailVerifyKey())) {
				result.setStatus("TRUE");
				result.setMessage("Sent email successfully !!!");
				logger.info("Sent email successfully !!!");
			}else {
				result.setStatus("FALSE");
				result.setMessage("Sent email faild !!!");
				logger.info("Sent email faild !!!");
			}
		}else {
			result.setStatus("FALSE");
			result.setMessage("Sent email faild !!!");
			logger.info("Sent email faild !!!");
		}
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping(value = "emailVerification")
	public ResponseEntity<?> emailVerification(@RequestParam Map<String, Object> arg) throws SQLException{
		AjaxResult result = new AjaxResult();
		UserResponse user = userService.getTsstUserByUserId(arg.get("userId").toString());
		if (mailService.verifyEmail(arg.get("code").toString(),user.getEmailVerifyKey())) {
			arg.put("status", true);
//			tsstUserService.updateByEmail(arg);
			result.setStatus("TRUE");
			result.setMessage("Verifi email successfully !!!");
			logger.info("Email verification successfully !!!");
		}else {
			result.setStatus("FALSE");
			result.setMessage("Email verification failed !!!");
			logger.info("Email verification failed");
		}
		return ResponseEntity.ok(result);
	}
	
}
