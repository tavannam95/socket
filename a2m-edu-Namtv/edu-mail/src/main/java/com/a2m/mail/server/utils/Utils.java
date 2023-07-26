/**
 * 
 */
package com.a2m.mail.server.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tiennd
 *
 * @created Oct 20, 2022
 */
public class Utils {

	private static final String AUTHORIZATION = "authorization";

	public static String getTokenFromRequest(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
		String headerValue = "";
		while (headers.hasMoreElements()) {
			headerValue = headers.nextElement();
		}
		if (headerValue != null && !"".equals(headerValue)) {
			String els[] = headerValue.split(" ");
			if (els.length > 1) {
				return els[1];
			}
		}
		return "";
	}
}
