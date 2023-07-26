package com.a2m.auth.controller;

import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.auth.exception.EmailAlreadyExistsException;
import com.a2m.auth.model.request.LoginRequest;
import com.a2m.auth.model.request.TsstUserRequest;
import com.a2m.auth.model.response.JwtResponse;
import com.a2m.auth.model.response.UserResponse;
import com.a2m.auth.security.UserDetailsImpl;
import com.a2m.auth.security.jwt.JwtUtils;
import com.a2m.auth.service.MailService;
import com.a2m.auth.service.UserService;
import com.a2m.auth.service.TwoFactorAuthService;
import com.a2m.auth.util.AjaxResult;
import com.a2m.auth.util.HashUtils;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {
	
	private static Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService tsstUserService;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private TwoFactorAuthService twoFactorAuthService;
	@Autowired
	private MailService mailService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		AjaxResult ajaxResult = new AjaxResult();
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();	
			
			if (userDetails.getUser().getTwoFactorAuthEnable()) {
				ajaxResult.setMessage("Two Factor Authentication");
				ajaxResult.setStatus("NEED_OTP");
			}else {
//				List<String> roles = userDetails.getAuthorities().stream()
//						.map(item -> item.getAuthority())
//						.collect(Collectors.toList());
				ajaxResult.setMessage("Login successfully !!!");
				ajaxResult.setStatus("TRUE");
				ajaxResult.setResponseData(new JwtResponse(jwt));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Login failed !!!");
			ajaxResult.setMessage("Login failed");
			ajaxResult.setStatus("FALSE");
		}
		return ResponseEntity.ok(ajaxResult);
	}
	
	@PostMapping(value = "signUp")
	public ResponseEntity<?> signUp(@Valid @RequestBody TsstUserRequest tsstUser) throws FileNotFoundException{
		AjaxResult ajaxResult = new AjaxResult();
		tsstUser.setStatus(false);
		
		if(tsstUserService.existsByUserId(tsstUser.getUserId())) {
            throw new EmailAlreadyExistsException(
                    String.format("email %s already exists", tsstUser.getEmail()));
        }

        if(tsstUserService.existsByEmail(tsstUser.getEmail())) {
    		throw new EmailAlreadyExistsException(
                  String.format("email %s already exists", tsstUser.getEmail()));
        }
		try {
			tsstUserService.saveTsstUser(tsstUser);
			mailService.sentVerificationCode(tsstUser.getEmail(),tsstUser.getEmailVerifyKey());
			ajaxResult.setStatus("NEED_EMAIL_VERIFY");
			ajaxResult.setMessage("Sign Up Success");
			logger.info("Sign Up Success !!!");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Sign Up Failed");
			ajaxResult.setMessage("Sign Up Failed");
			ajaxResult.setStatus("FALSE");
		}
		return ResponseEntity.ok(ajaxResult);
	}
	
	@GetMapping(value = "verifyOtp")
	public ResponseEntity<?> verifyOtp(@RequestParam Map<String, Object> arg) {
		AjaxResult ajaxResult = new AjaxResult();
		UserResponse user = tsstUserService.getTsstUserByUserId(arg.get("userId").toString());
		if (user == null) {
			ajaxResult.setMessage("Two Factor Authentication Failed");
			ajaxResult.setStatus("FALSE");
			logger.info("Two Factor Authentication Failed !!!");
			return ResponseEntity.ok(ajaxResult);
		}
		if (twoFactorAuthService.verifyCode(arg.get("TOTP").toString(), user.getTwoFactorAuthKey())) {
			ajaxResult.setMessage("Two Factor Authentication Success");
			ajaxResult.setStatus("TRUE");
			ajaxResult.setResponseData(new JwtResponse(jwtUtils.generateJwtTokenForSocial(user.getUserUid())));
			logger.info("Two Factor Authentication Success !!!");
		}else {
			ajaxResult.setMessage("Two Factor Authentication Failed");
			ajaxResult.setStatus("FALSE");
			logger.info("Two Factor Authentication Failed !!!");
		}
		return ResponseEntity.ok(ajaxResult);
	}
	
	@GetMapping(value = "generateSecretKey")
	public ResponseEntity<?> generateSecretKey() {
		Map<String, Object> result = new HashMap<>();
		result.put("secretKey", twoFactorAuthService.generateSecretKey());
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value = "refreshToken")
	public ResponseEntity<?> refreshToken(@RequestBody Map<String, Object> arg) {
		return null;
	}
	
	@GetMapping(value = "hashAccessToken")
	public Map<String, Object> hashAccessToken(@RequestParam Map<String, Object> args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		String hash = HashUtils.encrypt(args.get("accessToken").toString(), args.get("publicKey").toString());
		Map<String, Object> result = new HashMap<>();
		result.put("token", hash);
		if (hash == null) {
			return null;
		}
		return result;
	}
	
	@GetMapping(value = "genKeyOfRsa")
	public ResponseEntity<?> genKeyOfRsa() throws NoSuchAlgorithmException{
		return ResponseEntity.ok(HashUtils.genPublicKeyAndPrivateKey());
	}
	
}
