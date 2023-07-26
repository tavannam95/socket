package com.a2m.auth.model.social.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoProperties {
	private String nickname;
	private String profile_image;
	private String thumbnail_image;
}
