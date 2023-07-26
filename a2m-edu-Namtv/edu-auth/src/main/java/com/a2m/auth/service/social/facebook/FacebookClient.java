package com.a2m.auth.service.social.facebook;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.a2m.auth.model.social.facebook.FacebookUser;

@Service
public class FacebookClient {
	@Autowired private RestTemplate restTemplate;

    private final String FACEBOOK_GRAPH_API_BASE = "https://graph.facebook.com";

    public FacebookUser getUser(String accessToken) {
        String path = "/me?fields={fields}&redirect={redirect}&access_token={access_token}";
        String fields = "email,first_name,last_name,id,picture.width(720).height(720)";
        final Map<String, String> variables = new HashMap<>();
        variables.put("fields", fields);
        variables.put("redirect", "false");
        variables.put("access_token", accessToken);
        return restTemplate
                .getForObject(FACEBOOK_GRAPH_API_BASE + path, FacebookUser.class, variables);
    }
}
