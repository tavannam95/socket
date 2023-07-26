package com.a2m.auth.model.social.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoAccount {
	private boolean profile_needs_agreement;
	private Profile profile;
}
