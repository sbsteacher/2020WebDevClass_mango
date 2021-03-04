package com.koreait.mango.security;

import com.koreait.mango.security.model.UserPrincipal;

public interface IAuthenticationFacade {	
	UserPrincipal getUserPrincipal();
}
