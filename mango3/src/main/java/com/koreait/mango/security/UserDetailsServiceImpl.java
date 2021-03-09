package com.koreait.mango.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.koreait.mango.HomeMapper;
import com.koreait.mango.model.UserEntity;
import com.koreait.mango.security.model.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private HomeMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	//form 로그인
	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		return loadUserByUsername("mango", uid);
	}

	//form 로그인 & 소셜 로그인 사용
	public UserDetails loadUserByUsername(String provider, String uid) throws UsernameNotFoundException {
		UserEntity p = new UserEntity();
		p.setProvider(provider);
		p.setUid(uid);	
		UserEntity ue = mapper.selUser(p);	
		if(ue == null) {
			return null;
		}
		return UserPrincipal.create(ue);
	}
	
	//소셜로그인, 폼로그인 함께 씁니다.
	public int join(UserEntity p) {
		if(p.getUpw() != null && !"".equals(p.getUpw())) {
			p.setUpw(encoder.encode(p.getUpw())); 
		}
		return mapper.insUser(p);
	}
}







