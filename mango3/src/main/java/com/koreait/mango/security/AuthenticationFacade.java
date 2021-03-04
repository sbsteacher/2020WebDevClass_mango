package com.koreait.mango.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.koreait.mango.security.model.UserPrincipal;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
	@Override
	public UserPrincipal getUserPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (UserPrincipal) authentication.getPrincipal();
	}
}
