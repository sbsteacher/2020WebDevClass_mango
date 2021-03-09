package com.koreait.mango.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.mango.model.BoardEntity;
import com.koreait.mango.model.MapDTO;
import com.koreait.mango.model.RestaurantDomain;

@Mapper
public interface UserMapper {
	List<RestaurantDomain> selRestaurantListForMap(MapDTO p);
	
	int insBoard(BoardEntity p);
}
