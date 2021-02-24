package com.koreait.mango;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.koreait.mango.model.UserEntity;

import com.koreait.mango.security.CurrentUser;
import com.koreait.mango.security.UserDetailsServiceImpl;
import com.koreait.mango.security.model.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	final HomeService service;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
		
	@GetMapping("/home")
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	public void home(@CurrentUser UserPrincipal userPrincipal) {
		System.out.println("userPk : " + userPrincipal.getUserPk());
		service.home();
	}
	
	@GetMapping("/denied")
	public void denied() {}
	
	@GetMapping("/login")
	public void login() {}
	
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String join(UserEntity p) {
		int result = service.mangoJoin(p);
		System.out.println("result : " + result);		
		return "redirect:/login";
	}
}
