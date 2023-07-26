package com.a2m.auth.service.social.facebook;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.auth.constants.CommonConstants;
import com.a2m.auth.constants.RoleConstants;
import com.a2m.auth.model.request.TsstUserRequest;
import com.a2m.auth.model.response.UserResponse;
import com.a2m.auth.model.social.facebook.FacebookUser;
import com.a2m.auth.security.jwt.JwtUtils;
import com.a2m.auth.service.ComSeqService;
import com.a2m.auth.service.UserService;
import com.a2m.auth.util.CommonUtils;

@Service
public class FacebookService {
	private static Logger logger =LoggerFactory.getLogger(FacebookService.class);
	@Autowired 
	private FacebookClient facebookClient;
    @Autowired 
    private UserService tsstUserService;
    @Autowired 
    private JwtUtils jwtUtils;
    @Autowired
    private ComSeqService comSeqService;
    
    
    @Transactional(rollbackFor = Exception.class)
    public String loginUser(String fbAccessToken) throws SQLException {
    	FacebookUser facebookUser = new FacebookUser();
    	try {
    		facebookUser = facebookClient.getUser(fbAccessToken);
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("Login facebook failed !!!");
			e.printStackTrace();
			return null;
		}
    	TsstUserRequest userReq = convertTo(facebookUser);
    	UserResponse userRes = tsstUserService.getTsstUserByEmail(userReq.getEmail());
    	String token = "";
    	if (userRes == null) {
    		tsstUserService.saveTsstUser(userReq);
    		token = jwtUtils.generateJwtTokenForSocial(userReq.getUserUid());
    	}else {
    		token = jwtUtils.generateJwtTokenForSocial(userRes.getUserUid());
    	}
        logger.info("Login successfully");
        return token;
    } 

    private TsstUserRequest convertTo(FacebookUser facebookUser) throws SQLException {
    	List<String> roles = new ArrayList<String>();
    	roles.add(RoleConstants.NORMAL_USER);
        return TsstUserRequest.builder()
                .userUid(comSeqService.getSeq("SEQ_USER_UID"))
                .userId(CommonUtils.getUserIdForSocical(facebookUser.getId(), CommonConstants.FACEBOOK_LOGIN))
                .password(CommonUtils.generatePassword(8))
                .fullName(facebookUser.getFirstName() + facebookUser.getLastName())
                .email(facebookUser.getEmail())
                .status(true)
                .roles(roles)
                .twoFactorAuthEnable(false)
                .build();
    }
}
