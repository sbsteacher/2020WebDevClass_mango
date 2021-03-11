package com.koreait.mango.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.koreait.mango.model.*;

@Mapper
public interface AdminMapper {
	int insRestaurant(RestaurantEntity p);
	int insRestaurantMenuInfo(List<RestaurantMenuInfoEntity> p);
	int insRestaurantMenuImg(List<RestaurantMenuImgEntity> p);
	List<RestaurantDomain> selRestaurantList();
}
