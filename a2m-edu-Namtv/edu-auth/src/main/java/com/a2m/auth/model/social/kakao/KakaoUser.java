package com.a2m.auth.model.social.kakao;

import java.util.Date;

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
public class KakaoUser {
	private String id;
	private Date connected_at;
	private KakaoProperties properties;
	private KakaoAccount kakao_account;
}
