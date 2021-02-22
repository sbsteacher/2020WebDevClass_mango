package com.koreait.mango;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.koreait.mango.model.UserEntity;
import com.koreait.mango.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	final HomeService service;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String join(UserEntity p) {
		service.join(p);
		return "redirect:/login";
	}
}
