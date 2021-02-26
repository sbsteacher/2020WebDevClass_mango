package com.koreait.mango.security;

import org.springframework.security.core.Authentication;

import com.koreait.mango.security.model.UserPrincipal;

public interface IAuthenticationFacade {	
	UserPrincipal getUserPrincipal();
}
