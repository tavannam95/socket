package com.a2m.gen.utils;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

public class GenStringUtils extends StringUtils {
	public static String MENU_URL = "MENU_URL";
	public static String DOLLAR_SIGN = "$";
	public static String AT_SIGN = "@";
	public static String YES = "Y";
	public static String NO = "N";
	public static String DOT = ".";
	public static String TRUE = "true";
	public static String FALSE = "false";
	
	public static String genNextId(String maxId, DecimalFormat formatter, String prefix) {
		if(GenStringUtils.isEmpty(maxId)) {
			maxId = "0";
		}
		maxId = maxId.replaceAll(prefix, "");
		String newId = prefix + formatter.format((Integer.parseInt(maxId) + 1));
		return newId;
	}
	
	public static String getForInQuery(String str, String delim){
    	StringTokenizer stz = new StringTokenizer(str, delim);
    	String inStr = "";
    	int i = 0;
    	while(stz.hasMoreTokens()){
    		inStr = inStr+ (i==0?"":",") + "'"+stz.nextToken().trim()+"'";
    		i++;
    	}
    	return inStr;
    }
	
	public static String nullToEmpty(String str) {
		if(str == null) {
			return GenStringUtils.EMPTY;
		}
		return str.trim();
	}
	
	public static String decodeHtml(Object str) {
		String res = null;
		if (str != null && str instanceof String) {
			res = org.springframework.web.util.HtmlUtils.htmlUnescape(str.toString());
			res = res.replaceAll("& #39;", "'");
			res = res.replaceAll("&gt;", ">");
			res = res.replaceAll("&lt;", "<");
		}
		return res;
	}
}
