package com.a2m.auth.service.social.google;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.a2m.auth.constants.CommonConstants;
import com.a2m.auth.constants.RoleConstants;
import com.a2m.auth.model.request.TsstUserRequest;
import com.a2m.auth.model.response.UserResponse;
import com.a2m.auth.model.social.google.GoogleUser;
import com.a2m.auth.security.jwt.JwtUtils;
import com.a2m.auth.service.ComSeqService;
import com.a2m.auth.service.UserService;
import com.a2m.auth.util.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GoogleService {
	@Autowired
	private Environment env; 
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private ComSeqService comSeqService;
	@Autowired
	private UserService tsstUserService;
	
	public String getAccessToken(final String accessToken) throws SQLException{
		
	    String link = env.getProperty("google.link.get.user_info") + accessToken;
		try {
			String response = Request.Get(link)
					.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
					.execute().returnContent()
					.asString();
			ObjectMapper mapper = new ObjectMapper();
		    GoogleUser googleUser = mapper.readValue(response, GoogleUser.class);
		    TsstUserRequest userReq = convertToTsstUser(googleUser);
		    UserResponse userRes = tsstUserService.getTsstUserByEmail(userReq.getEmail());
		    String token = "";
		    if (userRes == null) {
		    	tsstUserService.saveTsstUser(userReq);
		    	token = jwtUtils.generateJwtTokenForSocial(userReq.getUserUid());
		    }else {
		    	token = jwtUtils.generateJwtTokenForSocial(userRes.getUserUid());
		    }
		    
		    return token;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TsstUserRequest convertToTsstUser(GoogleUser googleUser) throws SQLException {
		List<String> roles = new ArrayList<String>();
    	roles.add(RoleConstants.NORMAL_USER);
        return TsstUserRequest.builder()
                .userUid(comSeqService.getSeq("SEQ_USER_UID"))
                .userId(CommonUtils.getUserIdForSocical(googleUser.getSub(), CommonConstants.GOOGLE_LOGIN))
                .password(CommonUtils.generatePassword(8))
                .fullName(googleUser.getName())
                .email(googleUser.getEmail())
                .status(true)
                .roles(roles)
                .twoFactorAuthEnable(false)
                .build();
	}
	
}
