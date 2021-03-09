package com.koreait.mango.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.mango.MyKey;
import com.koreait.mango.model.BoardEntity;
import com.koreait.mango.model.MapDTO;
import com.koreait.mango.model.RestaurantDomain;
import com.koreait.mango.model.RestaurantEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	final UserService service;
	
	@GetMapping("/map")
	public void map(Model model) {
		
	}
	
	@ResponseBody
	@GetMapping("/getMapData")
	public List<RestaurantDomain> getMapData(MapDTO p) {
		return service.selRestaurantListForMap(p); 
	}
	
	@GetMapping("/detailRestaurant")
	public void detailRestaurant(RestaurantEntity p, Model model) {
		model.addAttribute(MyKey.DATA.getVal(), service.detailRestaurant(p));
	}
	
	@GetMapping("/board/list")
	public String boardList() {
		return "/board/list";
	}
	
	@GetMapping("/board/write")
	public String boardWrite(@ModelAttribute("board") BoardEntity board) {
		return "/board/write";
	}
	
	@PostMapping("/board/write")
	public String boardWriteProc(BoardEntity board) {
		service.insBoard(board);
		return "redirect:/user/board/detail?boardPk=" + board.getBoardPk();
	}
	
	@GetMapping("/board/detail")
	public void boardDetail(BoardEntity board) {
		
	}
	
	@GetMapping("/favorite")
	public void favorite() {}
}
