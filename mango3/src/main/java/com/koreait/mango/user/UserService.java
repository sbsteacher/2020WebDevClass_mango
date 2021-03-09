package com.koreait.mango.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.koreait.mango.admin.AdminService;
import com.koreait.mango.model.BoardEntity;
import com.koreait.mango.model.MapDTO;
import com.koreait.mango.model.RestaurantDetailDomain;
import com.koreait.mango.model.RestaurantDomain;
import com.koreait.mango.model.RestaurantEntity;
import com.koreait.mango.security.IAuthenticationFacade;
import com.koreait.mango.security.model.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	final UserMapper mapper;
	final AdminService adminService;
	final IAuthenticationFacade authenticationFacade;
	
	public List<RestaurantDomain> selRestaurantListForMap(MapDTO p) {
		return mapper.selRestaurantListForMap(p);
	}
	
	public RestaurantDetailDomain detailRestaurant(RestaurantEntity p) {
		//조회수 올리기
		
		return adminService.detailRestaurant(p);
	}
	
	public int insBoard(BoardEntity p) {
		UserPrincipal user = authenticationFacade.getUserPrincipal();
		p.setWriterPk(user.getUserPk());
		return mapper.insBoard(p);
	}
}
