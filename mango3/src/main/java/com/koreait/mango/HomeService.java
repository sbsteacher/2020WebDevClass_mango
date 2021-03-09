package com.koreait.mango;

import org.springframework.stereotype.Service;

import com.koreait.mango.model.UserEntity;
import com.koreait.mango.security.IAuthenticationFacade;
import com.koreait.mango.security.UserDetailsServiceImpl;
import com.koreait.mango.security.model.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {
	
	final HomeMapper mapper;
	final UserDetailsServiceImpl userDetailsService;
    final IAuthenticationFacade authenticationFacade;
	
	public void home() {		
		UserPrincipal user = authenticationFacade.getUserPrincipal();
		System.out.println("userPk(2) : " + user.getUserPk());
	}
	
	public int mangoJoin(UserEntity p) {		
		return userDetailsService.join(p);
	}
	
	public UserEntity selUser(UserEntity p) {
		return mapper.selUser(p);
	}
}
