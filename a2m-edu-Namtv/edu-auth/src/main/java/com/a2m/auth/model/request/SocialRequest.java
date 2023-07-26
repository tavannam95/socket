package com.a2m.auth.model.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SocialRequest {
	@NotNull(message = "AccessToken cannot null")
	private String accessToken;
}
