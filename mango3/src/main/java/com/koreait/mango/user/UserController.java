package com.koreait.mango.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.mango.MyKey;
import com.koreait.mango.model.MapDTO;
import com.koreait.mango.model.RestaurantDomain;
import com.koreait.mango.model.RestaurantEntity;
import com.koreait.mango.model.board.BoardEntity;
import com.koreait.mango.model.review.RestaurantReviewDTO;

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
		model.addAttribute(MyKey.DATA.getVal(), service.detailRestaurantWithHits(p));
	}
	
	@GetMapping("/review")
	public void review() {}
	
	@PostMapping("/review")
	public String review(RestaurantReviewDTO p) {
		service.review(p);
		return "redirect:/user/detailRestaurant?restPk=" + p.getRestPk();
	}
	
	
	@GetMapping("/board/list")
	public String boardList(Model model) {
		model.addAttribute(MyKey.LIST.getVal(), service.selBoardList());
		return "board/list";
	}
	
	@GetMapping("/board/write")
	public String boardWrite(@ModelAttribute("board") BoardEntity board) {
		return "board/write";
	}
	
	@ResponseBody
	@PostMapping("/board/uploadImg")
	public Map<String, String> uploadImg(MultipartFile ctntImg) {
		Map<String, String> result = new HashMap();
		result.put("default", service.saveBoardImg(ctntImg));		
		return result;
	}
	
	@PostMapping("/board/write")
	public String boardWriteProc(BoardEntity board) {
		service.insBoard(board);
		return "redirect:/user/board/detail?boardPk=" + board.getBoardPk();
	}
	
	@GetMapping("/board/detail")
	public String boardDetail(BoardEntity p, Model model) {
		model.addAttribute(MyKey.DATA.getVal(), service.selBoard(p));
		return "board/detail";
	}
	
	@GetMapping("/favorite")
	public void favorite() {}
}
