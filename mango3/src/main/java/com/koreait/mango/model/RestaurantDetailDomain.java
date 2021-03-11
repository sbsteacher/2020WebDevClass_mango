package com.koreait.mango.model;

import java.util.List;

import com.koreait.mango.model.review.RestaurantReviewDomain;
import com.koreait.mango.model.review.RestaurantReviewImgDomain;
import com.koreait.mango.model.review.RestaurantReviewImgEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDetailDomain {
	private RestaurantEntity entity;
	private List<RestaurantReviewImgDomain> imgList; //이미지 리스트 (메인 상단에 나타날 리뷰 이미지)
	private List<RestaurantReviewDomain> reviewList; //리뷰 리스트 (with 각 리뷰의 이미지)
	private List<RestaurantMenuImgEntity> menuImgList; //메뉴 이미지 리스트
	private List<RestaurantMenuInfoEntity> menuInfoList; //메뉴 정보 리스트
}
 