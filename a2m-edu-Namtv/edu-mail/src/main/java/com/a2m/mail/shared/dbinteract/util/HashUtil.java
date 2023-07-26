package com.a2m.mail.shared.dbinteract.util;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import kr.co.a2m.security.kryptos.A2mSHA;

public class HashUtil {

    public static String hashPassword(String password, String alg) {
        String result = "";
        if ("A2M".equals(alg)) {
            A2mSHA sha = new A2mSHA();
            try {
                result = sha.encrypt(password);
            } catch (Exception e) {
                result = "";
            }
        } else {
            result = chooseHashFunction(alg, password);
        }
        return result;
    }

    /*interface PasswordHashFunction extends Function<String, String> {}

    private static PasswordHashFunction chooseHashFunction(String nullableAlgorithm) {
        String algorithm = Optional.ofNullable(nullableAlgorithm).orElse("MD5");
        switch (algorithm) {
            case "NONE":
                return (password) -> "password";
            default:
                return (password) -> chooseHashing(algorithm).hashString(password, StandardCharsets.UTF_8).toString();
        }
    }*/
    
    private static String chooseHashFunction(String nullableAlgorithm, String password) {
    	String algorithm = Optional.ofNullable(nullableAlgorithm).orElse("MD5");
    	if ("NONE".equals(algorithm))
    		return "password";
    	else
    		return chooseHashing(algorithm).hashString(password, StandardCharsets.UTF_8).toString();
    }

    private static HashFunction chooseHashing(String algorithm) {
        if("MD5".equals(algorithm))
            return Hashing.md5();
        else if("SHA-256".equals(algorithm))
            return Hashing.sha256();
        else if("SHA-512".equals(algorithm))
            return Hashing.sha512();
        else
            return Hashing.sha1();
    }

    protected HashUtil() {
    }

    public boolean verifyPassword(String password, String pass, String alg) {
        final boolean result;
        if (pass == null) {
            result = password == null;
        } else {
            if (pass.startsWith("a2m") && pass.endsWith("m2a") && pass.length() > 20) {
                String tmp = pass.substring(3, pass.length() - 3);
                result = password != null && password.equals(tmp);
            } else {
                result = password != null && password.equals(hashPassword(pass, alg));
            }
        }
        return result;
    }

}
