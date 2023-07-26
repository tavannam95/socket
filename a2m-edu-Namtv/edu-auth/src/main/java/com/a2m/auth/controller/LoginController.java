package com.a2m.auth.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	private static String redirectUrl = "http://atwom.com.vn";
	
	@GetMapping(value = "login")
	public String login(HttpServletRequest request,Model model) {
		Enumeration<?> enumeration = request.getParameterNames();
        Map<String, Object> params = new HashMap<>();
        while(enumeration.hasMoreElements()){
            String parameterName = (String) enumeration.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }
        
        if (params.containsKey("domain") && params.containsKey("key") && params.get("domain") != null && params.get("key") != null) {
        	model.addAttribute("REDIRECT_DOMAIN", params.get("domain"));
            model.addAttribute("PUBLIC_KEY", params.get("key"));
    		return "html/login";
        }else {
    		return "redirect:" + redirectUrl;
        }
        
	}
	
	@GetMapping(value = "/")
	public String redirect(HttpServletRequest request,Model model) {
		return "redirect:" + redirectUrl;
	}
}
