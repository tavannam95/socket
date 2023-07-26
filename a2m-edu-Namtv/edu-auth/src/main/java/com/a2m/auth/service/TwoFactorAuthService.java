package com.a2m.auth.service;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;

import com.a2m.auth.util.TOTPUtils;

@Service
public class TwoFactorAuthService {
	
	public boolean verifyCode(String totpCode, String secret) {
        String totpCodeBySecret = getTOTPCode(secret);

        return totpCodeBySecret.equals(totpCode);
    }

	public String getTOTPCode(String secretKey) {
	    return TOTPUtils.getOTP(secretKey);
	}
    
	public String generateSecretKey() {
	    SecureRandom random = new SecureRandom();
	    byte[] bytes = new byte[20];
	    random.nextBytes(bytes);
	    Base32 base32 = new Base32();
	    return base32.encodeToString(bytes);
	}
}
