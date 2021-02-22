package com.koreait.mango;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.koreait.mango.model.UserEntity;
import com.koreait.mango.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {
	
	final UserDetailsServiceImpl userDetailsService;
	final PasswordEncoder passEncoder;
	
	//망고 회원가입을 통해서 회원가입을 시키는 것
	public int join(UserEntity p) {
		p.setProvider("mango");
		//비밀번호 암호화
		String encodedPw = passEncoder.encode(p.getPassword());
		p.setUpw(encodedPw);
		return userDetailsService.join(p);
	}
}
