package com.koreait.mango.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDetailDomain {
	private RestaurantEntity entity;
	private List<RestaurantMenuImgEntity> menuImgList;
	private List<RestaurantMenuInfoEntity> menuInfoList;
}
