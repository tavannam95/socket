package com.a2m.auth.service.social.naver;

import java.io.IOException;

import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.a2m.auth.constants.CommonConstants;
import com.a2m.auth.model.social.kakao.KakaoUser;
import com.a2m.auth.security.jwt.JwtUtils;
import com.a2m.auth.service.UserService;
import com.a2m.auth.util.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NaverService {
	
	private static Logger logger = LoggerFactory.getLogger(NaverService.class);
	
	@Autowired
	private Environment env;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserService tsstUserService;
	
	public String getAccessToken(String accessToken) {
		String reqURL = env.getProperty("naver.link.get.user_info");
        try {
        	String result = Request.Post(reqURL)
        			.addHeader("Authorization","Bearer " + accessToken)
        			.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        			.execute()
        			.returnContent()
        			.asString();
            ObjectMapper mapper = new ObjectMapper();
		    KakaoUser kakaoUser = mapper.readValue(result, KakaoUser.class);
		    String userId = CommonUtils.getUserIdForSocical(kakaoUser.getId(), CommonConstants.KAKAO_LOGIN);
//		    TsstUserRequest tsstUser = convertToTsstUser(kakaoUser);
//		    if (!tsstUserService.existsByUserId(userId)) {
//		    	tsstUserService.saveTsstUser(tsstUser);
//		    }
		    String token = jwtUtils.generateJwtTokenForSocial(userId);
		    return token;
        } catch (IOException e) {
        	logger.warn("Login to kakao failed !!");
            e.printStackTrace();
            return null;
        }
	}
}	
