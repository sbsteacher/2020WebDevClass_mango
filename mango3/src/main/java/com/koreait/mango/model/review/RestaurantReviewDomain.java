package com.koreait.mango.model.review;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantReviewDomain extends RestaurantReviewEntity {
	private String writerNm;
	private List<RestaurantReviewImgEntity> imgs;
}
