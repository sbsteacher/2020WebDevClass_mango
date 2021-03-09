package com.koreait.mango.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantEntity {
	private int restPk;
	private String restNm;
	private String addr;
	private double lat;
	private double lng;
	private String tel;
	private String mainImg;
	private String regDt;
	private String modDt;
}
