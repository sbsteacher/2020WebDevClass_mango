package com.koreait.mango.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.koreait.mango.HomeMapper;
import com.koreait.mango.model.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	final HomeMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity p = new UserEntity();
		p.setUid(username);
		return mapper.selUser(p);
	}

	public int join(UserEntity p) {
		return mapper.insUser(p);
	}
}
