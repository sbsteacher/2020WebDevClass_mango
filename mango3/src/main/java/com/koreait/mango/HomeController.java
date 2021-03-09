package com.koreait.mango;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.koreait.mango.model.UserEntity;
import com.koreait.mango.security.CurrentUser;
import com.koreait.mango.security.model.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	final HomeService service;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
		
	@GetMapping("/home")
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	public void home() {		
		service.home();
	}
	
	@GetMapping("/denied")
	public void denied() {}

	@GetMapping("/login")
	public void login(@ModelAttribute("userEntity") UserEntity userEntity) {
		userEntity.setUid("mic");		
	}

	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String join(UserEntity p) {
		int result = service.mangoJoin(p);
		System.out.println("result : " + result);		
		return "redirect:/login";
	}
}
