package com.koreait.mango.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantMenuInfoEntity {
	private int restMenuInfoPk;
	private int restPk;
	private String menuNm;
	private int menuPrice;
}
