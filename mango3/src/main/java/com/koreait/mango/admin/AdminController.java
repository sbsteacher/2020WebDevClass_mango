package com.koreait.mango.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.koreait.mango.Const;
import com.koreait.mango.MyKey;
import com.koreait.mango.model.MenuImgDTO;
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
	public String regMenuInfo(@RequestParam int restPk
			, @RequestParam String[] menuNm
			, @RequestParam String[] menuPrice) {
		service.regMenuInfo(restPk, menuNm, menuPrice);
		
		return "redirect:/admin/detailRestaurant?restPk=" + restPk;
	}
	
	@PostMapping("/regMenuImg")
	public String regMenuImg(MenuImgDTO p) {
		
		System.out.println("restPk : " + p.getRestPk());
		System.out.println("imgs.size() : " + p.getImgs().size());
		service.regMenuImg(p);
		return "redirect:/admin/detailRestaurant?restPk=" + p.getRestPk();
	}
	
	@GetMapping("/regRestaurant")
	public void regRestaurant(@ModelAttribute(value="restaurant") RestaurantEntity restaurant) {
		restaurant.setRestNm("하하하");
	}
	
	@GetMapping("/listRestaurant")
	public void listRestaurant(Model model) {
		model.addAttribute(MyKey.DATA.getVal(), service.selRestaurantList());
	}
	
	@GetMapping("/detailRestaurant")
	public void detailRestaurant(RestaurantEntity p, Model model) {		
		model.addAttribute(MyKey.DATA.getVal(), service.detailRestaurant(p));
	}
}
