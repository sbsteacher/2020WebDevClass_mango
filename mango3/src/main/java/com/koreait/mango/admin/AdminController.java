package com.koreait.mango.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.mango.Const;
import com.koreait.mango.MyKey;
import com.koreait.mango.model.RestaurantEntity;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService service;
	
	@PostMapping("/regRestaurant")
	public String regRestaurantProc(RestaurantEntity p) {
		service.insRestaurant(p);
		return "redirect:/admin/detailRestaurant?restPk=" + p.getRestPk();
	}
	
	@PostMapping("/regMenuInfo")
	public String regMenuInfo(@RequestParam int restPk, @RequestParam String[] menuNm, @RequestParam String[] menuPrice) {
		
		
		System.out.println("restPk : " + restPk);
		for(String nm : menuNm) {
			System.out.println("nm : " + nm);
		}
		
		return "redirect:/admin/detailRestaurant?restPk=" + restPk;
	}
	
	@GetMapping("/regRestaurant")
	public void regRestaurant(@ModelAttribute RestaurantEntity restaurant) {}
	
	@GetMapping("/listRestaurant")
	public void listRestaurant(Model model) {
		model.addAttribute("data", service.selRestaurantList());
	}
	
	@GetMapping("/detailRestaurant")
	public void detailRestaurant(RestaurantEntity p, Model model) {
		model.addAttribute(MyKey.APP_KEY.getVal(), Const.KAKAO_JAVASCRIPT_KEY);
		model.addAttribute(MyKey.DATA.getVal(), service.detailRestaurant(p));
	}
}
