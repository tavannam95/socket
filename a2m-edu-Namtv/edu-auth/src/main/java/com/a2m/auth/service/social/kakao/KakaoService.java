package com.a2m.auth.service.social.kakao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.a2m.auth.constants.CommonConstants;
import com.a2m.auth.constants.RoleConstants;
import com.a2m.auth.model.User;
import com.a2m.auth.model.request.TsstUserRequest;
import com.a2m.auth.model.response.UserResponse;
import com.a2m.auth.model.social.kakao.KakaoUser;
import com.a2m.auth.security.jwt.JwtUtils;
import com.a2m.auth.service.ComSeqService;
import com.a2m.auth.service.UserService;
import com.a2m.auth.util.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KakaoService {
	private static Logger logger = LoggerFactory.getLogger(KakaoService.class);
	
	@Autowired
	private Environment env;
	@Autowired
	private ComSeqService comSeqService;
	@Autowired
	private UserService tsstUserService; 
	@Autowired
	private JwtUtils jwtUtils;
	
	public String getAccessToken(String accessToken) throws SQLException {

        String reqURL = env.getProperty("kakao.link.get.user_info");
        try {
        	String result = Request.Post(reqURL)
        			.addHeader("Authorization","Bearer " + accessToken)
        			.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        			.execute()
        			.returnContent()
        			.asString();
            ObjectMapper mapper = new ObjectMapper();
		    Map<String, Object> kakaoUser = mapper.readValue(result, Map.class);
			/*
			 * TsstUserRequest tsstUserRequest = convertToTsstUser(kakaoUser); UserResponse
			 * tsstUser = tsstUserService.getTsstUserByEmail(tsstUserRequest.getEmail());
			 */
		    String token = "";
			/*
			 * if (tsstUser == null) { tsstUserService.saveTsstUser(tsstUserRequest); token
			 * = jwtUtils.generateJwtTokenForSocial(tsstUserRequest.getUserUid()); }else {
			 * token = jwtUtils.generateJwtTokenForSocial(tsstUser.getUserUid()); }
			 */
		    return token;
        } catch (IOException e) {
        	logger.warn("Login to kakao failed !!");
            e.printStackTrace();
            return null;
        }

    }
	
	public TsstUserRequest convertToTsstUser(KakaoUser kakaoUser) throws SQLException {
		List<String> roles = new ArrayList<String>();
    	roles.add(RoleConstants.NORMAL_USER);
		return TsstUserRequest.builder()
				.userUid(comSeqService.getSeq("SEQ_USER_UID"))
				.userId(CommonUtils.getUserIdForSocical(kakaoUser.getId(),CommonConstants.KAKAO_LOGIN))
				.password(CommonUtils.generatePassword(8))
                .fullName(kakaoUser.getProperties().getNickname())
                .status(true)
                .roles(roles)
                .twoFactorAuthEnable(false)
                .build();
	}
	
}
