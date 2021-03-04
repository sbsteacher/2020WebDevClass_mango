package com.koreait.mango.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.mango.model.*;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper mapper;
	
	public int insRestaurant(RestaurantEntity p) {
		return mapper.insRestaurant(p);
	}
	
	public List<RestaurantDomain> selRestaurantList() {
		return mapper.selRestaurantList();
	}
	
	public RestaurantDetailDomain detailRestaurant(RestaurantEntity p) {
		RestaurantDetailDomain result = new RestaurantDetailDomain();
		result.setEntity(mapper.selRestaurant(p));	
		result.setMenuImgList(mapper.selRestaurantMenuImg(p));
		result.setMenuInfoList(mapper.selRestaurantMenuInfo(p));
		return result;
	}
}
