/**
 * 
 */
package com.a2m.mail.server.utils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author tiennd
 *
 * @created Oct 20, 2022
 */

@Component
public class AuthenticationUtil {

	@Autowired
	private Environment environment;

	@PostConstruct
	public void init() {
		AuthenticationUtil.url = environment.getProperty("a2m.imware.getinfo");
	}

	private static String url;

	private static RestTemplate restTemplate = new RestTemplate();
	private static final String AUTHORIZATION = "Authorization";
	private static final String TOKEN_TYPE_KEY = "Bearer ";

	public static String getUsername() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String accessToken = Utils.getTokenFromRequest(request);

		if ("".equals(accessToken) || accessToken == null) {
			return null;
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set(AUTHORIZATION, TOKEN_TYPE_KEY + accessToken);

		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		JSONObject data = new JSONObject(result.getBody());
		if (data.has("EMAIL")) {
			return data.getString("EMAIL");
		}
		return null;
	}
}
