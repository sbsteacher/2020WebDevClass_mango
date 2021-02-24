package com.koreait.mango.security.model;

import java.util.Map;

import com.koreait.mango.security.OAuth2AuthenticationProcessingException;
import com.koreait.mango.security.SocialType;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(SocialType.FACEBOOK.getValue())) {
			return new FacebookOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialType.GOOGLE.getValue())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialType.NAVER.getValue())) {
			return new NaverOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialType.KAKAO.getValue())) {
			return new KakaoOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException(
					"Sorry! Login with " + registrationId + " is not supported yet.");
		}
	}
}
