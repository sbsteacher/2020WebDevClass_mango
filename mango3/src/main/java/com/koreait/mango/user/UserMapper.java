package com.koreait.mango.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.koreait.mango.model.*;
import com.koreait.mango.model.board.*;
import com.koreait.mango.model.review.*;


@Mapper
public interface UserMapper {	
	//리뷰
	int insRestaurantReview(RestaurantReviewEntity p);
	int insRestaurantReviewImg(List<RestaurantReviewImgEntity> p);
		
	//게시판
	int insBoard(BoardEntity p);
	int insBoardImg(BoardImgEntity p);
	BoardDomain selBoard(BoardEntity p);
	List<BoardDomain> selBoardList();
	int updBoard(BoardEntity p);
	
	//가게
	int updRestaurantMainImg(RestaurantEntity p);
	List<RestaurantDomain> selRestaurantListForMap(MapDTO p); //맵
	RestaurantEntity selRestaurant(RestaurantEntity p);	
	List<RestaurantReviewImgDomain> selRestaurantImgList(RestaurantEntity p);
	List<RestaurantReviewDomain> selRestaurantReviewList(RestaurantEntity p);
	List<RestaurantReviewImgEntity> selRestaurantReviewImgList(RestaurantReviewEntity p);
	List<RestaurantMenuInfoEntity> selRestaurantMenuInfoList(RestaurantEntity p);
	List<RestaurantMenuImgEntity> selRestaurantMenuImgList(RestaurantEntity p);
}
