package com.koreait.mango.security;

import com.koreait.mango.model.security.UserPrincipal;

public interface IAuthenticationFacade {	
	UserPrincipal getUserPrincipal();
}
