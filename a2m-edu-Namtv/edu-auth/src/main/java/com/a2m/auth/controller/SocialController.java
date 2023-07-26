package com.a2m.auth.controller;

import java.sql.SQLException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.auth.model.request.SocialRequest;
import com.a2m.auth.model.response.JwtResponse;
import com.a2m.auth.service.social.facebook.FacebookService;
import com.a2m.auth.service.social.google.GoogleService;
import com.a2m.auth.service.social.kakao.KakaoService;
import com.a2m.auth.service.social.naver.NaverService;
import com.a2m.auth.util.AjaxResult;

@RestController
@RequestMapping(value = "api/auth")
public class SocialController {
	private static Logger logger = LoggerFactory.getLogger(SocialController.class);
	@Autowired
	private FacebookService facebookService;
	@Autowired
	private GoogleService googleService;
	@Autowired
	private KakaoService kakaoService;
	@Autowired
	private NaverService naverService;
	
	@PostMapping("/facebook/login")
    public  ResponseEntity<?> facebookLogin(@Valid @RequestBody SocialRequest facebookLoginRequest) throws SQLException {
        logger.info("facebook login {}", facebookLoginRequest);
        AjaxResult ajaxResult = new AjaxResult();
        String token = facebookService.loginUser(facebookLoginRequest.getAccessToken());
        if ( token == null) {
        	ajaxResult.setStatus("FALSE");
        	ajaxResult.setMessage("Login failed");
        }else {
        	ajaxResult.setStatus("TRUE");
        	ajaxResult.setMessage("Login successfully");
        	ajaxResult.setResponseData(new JwtResponse(token));
        }
        return ResponseEntity.ok(ajaxResult);
    }
	
	@PostMapping("/google/login")
    public  ResponseEntity<?> googleLogin(@Valid @RequestBody SocialRequest googleLoginRequest) throws SQLException {
        logger.info("facebook login {}", googleLoginRequest);
        AjaxResult ajaxResult = new AjaxResult();
        String token = googleService.getAccessToken(googleLoginRequest.getAccessToken());
        if ( token == null) {
        	ajaxResult.setStatus("FALSE");
        	ajaxResult.setMessage("Login failed");
        }else {
        	ajaxResult.setStatus("TRUE");
        	ajaxResult.setMessage("Login successfully");
        	ajaxResult.setResponseData(new JwtResponse(token));
        }
        return ResponseEntity.ok(ajaxResult);
    }
	
	@PostMapping("/kakao/login")
    public  ResponseEntity<?> kakaoLogin(@Valid @RequestBody SocialRequest request) throws SQLException {
        logger.info("kakao login {}", request);
        AjaxResult ajaxResult = new AjaxResult();
        String token = kakaoService.getAccessToken(request.getAccessToken());
        if ( token == null) {
        	ajaxResult.setStatus("FALSE");
        	ajaxResult.setMessage("Login failed");
        }else {
        	ajaxResult.setStatus("TRUE");
        	ajaxResult.setMessage("Login successfully");
        	ajaxResult.setResponseData(new JwtResponse(token));
        }
        return ResponseEntity.ok(ajaxResult);
    }
	
	@PostMapping("/naver/login")
    public  ResponseEntity<?> naverLogin(@Valid @RequestBody SocialRequest request) throws SQLException {
        logger.info("facebook login {}", request);
        AjaxResult ajaxResult = new AjaxResult();
        String token = naverService.getAccessToken(request.getAccessToken());
        if ( token == null) {
        	ajaxResult.setStatus("FALSE");
        	ajaxResult.setMessage("Login failed");
        }else {
        	ajaxResult.setStatus("TRUE");
        	ajaxResult.setMessage("Login successfully");
        	ajaxResult.setResponseData(new JwtResponse(token));
        }
        return ResponseEntity.ok(ajaxResult);
    }
}
