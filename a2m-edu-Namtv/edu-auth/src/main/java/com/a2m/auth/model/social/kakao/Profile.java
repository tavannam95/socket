package com.a2m.auth.model.social.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
	private String nickname;
	private String thumbnail_image_url;
	private String profile_image_url;
	private String is_default_image; 
}
