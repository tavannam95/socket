package com.a2m.auth.util;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class HashUtils {

	public static String encrypt(String content, String publicKeyStr ) throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		PublicKey publicKey = decodePublicKey(publicKeyStr);
		byte[] contentBytes = content.getBytes();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] cipherContent = cipher.doFinal(contentBytes);
		String encoded = Base64.getEncoder().encodeToString(cipherContent);
		return encoded;
	}

	public static String decrypt(String cipherContent, String privateKeyStr)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, GeneralSecurityException {
		PrivateKey privateKey = decodePrivateKey(privateKeyStr);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] cipherContentBytes = Base64.getDecoder().decode(cipherContent.getBytes());
		byte[] decryptedContent = cipher.doFinal(cipherContentBytes);
		String decoded = new String(decryptedContent);
		return decoded;
	}

	public static String encodeKey(Key key) {
		byte[] keyBytes = key.getEncoded();
		String encodedKeyStr = Base64.getUrlEncoder().encodeToString(keyBytes);
		return encodedKeyStr;
	}

	public static PublicKey decodePublicKey(String keyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes = Base64.getUrlDecoder().decode(keyStr);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey key = keyFactory.generatePublic(spec);
		return key;
	}

	public static PrivateKey decodePrivateKey(String keyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes = Base64.getUrlDecoder().decode(keyStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey key = keyFactory.generatePrivate(keySpec);
		return key;
	}

	public static Map<String, Object> genPublicKeyAndPrivateKey() throws NoSuchAlgorithmException {
		SecureRandom sr = new SecureRandom();
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048, sr);
		KeyPair keyPair = kpg.generateKeyPair();
		Key publicKey = keyPair.getPublic();
		Key privateKey = keyPair.getPrivate();
		Map<String, Object> result = new HashMap<>();
		result.put("publicKey", encodeKey(publicKey));
		result.put("privateKey", encodeKey(privateKey));
		return result;
	}
	
}

