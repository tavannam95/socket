package com.a2m.auth.util;

import java.util.Random;

import com.a2m.auth.constants.CommonConstants;

public class CommonUtils {
	
	public static String getUserIdForSocical(String id, String loginType) {
		String userUid = "";
		switch (loginType) {
		case CommonConstants.FACEBOOK_LOGIN:
			userUid = CommonConstants.FACEBOOK_LOGIN + "_" + id;
			break;
		case CommonConstants.GOOGLE_LOGIN:
			userUid = CommonConstants.GOOGLE_LOGIN + "_" + id;
			break;
		case CommonConstants.KAKAO_LOGIN:
			userUid = CommonConstants.KAKAO_LOGIN + "_" + id;
			break;
		case CommonConstants.NAVER_LOGIN:
			userUid = CommonConstants.NAVER_LOGIN + "_" + id;
			break;
		default:
			break;
		}
		return userUid;
	}
	
    public static String generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(password);
    }
	
}
