package com.koreait.mango.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.koreait.mango.model.*;

@Mapper
public interface AdminMapper {
	int insRestaurant(RestaurantEntity p);
	int insRestaurantMenuInfo(RestaurantMenuInfoEntity p);
	int insRestaurantMenuImg(RestaurantMenuImgEntity p);
	List<RestaurantDomain> selRestaurantList();
	RestaurantEntity selRestaurant(RestaurantEntity p);
	List<RestaurantMenuInfoEntity> selRestaurantMenuInfo(RestaurantEntity p);
	List<RestaurantMenuImgEntity> selRestaurantMenuImg(RestaurantEntity p);
}
