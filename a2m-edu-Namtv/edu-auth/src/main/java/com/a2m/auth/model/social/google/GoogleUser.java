package com.a2m.auth.model.social.google;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleUser {
	private String sub;
	private String email;
	private boolean verified_email;
	private String name;
	private String given_name;
	private String family_name;
	private String locale;
	private String picture;
	private boolean email_verified;
}
