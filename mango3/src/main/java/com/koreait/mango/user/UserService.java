package com.koreait.mango.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.mango.Const;
import com.koreait.mango.FileUtils;
import com.koreait.mango.admin.AdminService;
import com.koreait.mango.model.BoardEntity;
import com.koreait.mango.model.MapDTO;
import com.koreait.mango.model.RestaurantDetailDomain;
import com.koreait.mango.model.RestaurantDomain;
import com.koreait.mango.model.RestaurantEntity;
import com.koreait.mango.model.review.RestaurantReviewDTO;
import com.koreait.mango.model.review.RestaurantReviewDomain;
import com.koreait.mango.model.review.RestaurantReviewEntity;
import com.koreait.mango.model.review.RestaurantReviewImgEntity;
import com.koreait.mango.model.security.UserPrincipal;
import com.koreait.mango.security.IAuthenticationFacade;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	final UserMapper mapper;	
	final IAuthenticationFacade authenticationFacade;
	final FileUtils fileUtils;
	
	public List<RestaurantDomain> selRestaurantListForMap(MapDTO p) {
		return mapper.selRestaurantListForMap(p);
	}

	
	public RestaurantDetailDomain detailRestaurantWithHits(RestaurantEntity p) {
		//TODO : 조회수 올리기
		
		RestaurantDetailDomain rdd = detailRestaurant(p);
		
		//이미지 리스트 가져오기		
		rdd.setImgList(mapper.selRestaurantImgList(p));
		
		//리뷰 리스트 가져오기
		rdd.setReviewList(mapper.selRestaurantReviewList(p));
		for(RestaurantReviewDomain reviewDomain : rdd.getReviewList()) {
			reviewDomain.setImgs(mapper.selRestaurantReviewImgList(reviewDomain));
		}
		
		return rdd;
	}
	public RestaurantDetailDomain detailRestaurant(RestaurantEntity p) {
		RestaurantDetailDomain result = new RestaurantDetailDomain();
		result.setEntity(mapper.selRestaurant(p));	
		result.setMenuImgList(mapper.selRestaurantMenuImgList(p));
		result.setMenuInfoList(mapper.selRestaurantMenuInfoList(p));
		return result;
	}
	
	public void review(RestaurantReviewDTO p) {
		if(p == null || "".equals(p.getCtnt()) || p.getRestPk() == 0) { return; }
		
		UserPrincipal user = authenticationFacade.getUserPrincipal();
		p.setWriterPk(user.getUserPk());
			
		//리뷰 insert
		int result = mapper.insRestaurantReview(p);
		
		if(result == 0 || p.getImgs().size() == 0) { return; }
		
		String path = Const.IMG_PATH_REST + p.getRestPk() + "/review/";
		
		List<RestaurantReviewImgEntity> insList = new ArrayList();
		result = 0;
		
		try {
			RestaurantReviewImgEntity rrie = null;
			for(MultipartFile file : p.getImgs()) {
				String fileNm = fileUtils.transferTo(file, path);
				
				rrie = new RestaurantReviewImgEntity();
				rrie.setReviewPk(p.getReviewPk());
				rrie.setImg(fileNm);
				
				insList.add(rrie);
			}
			
			result = mapper.insRestaurantReviewImg(insList);
		} catch(Exception e) {
			e.printStackTrace();
			if(result == 0 || insList.size() > 0) { //기존에 업로드된 이미지 삭제
				String fullPath = fileUtils.getRealPath(path);
				for(RestaurantReviewImgEntity rrie : insList) {
					fileUtils.delFile(path + "/" + rrie.getImg());
				}
			}
			return;
		}
		
		//mainImg 값을 update한다.
		RestaurantEntity p2 = new RestaurantEntity();
		p2.setRestPk(p.getRestPk());
		p2.setMainImg(insList.get(0).getImg());
		
		mapper.updRestaurantMainImg(p2);
	}
	
	public int insBoard(BoardEntity p) {
		UserPrincipal user = authenticationFacade.getUserPrincipal();
		p.setWriterPk(user.getUserPk());
		return mapper.insBoard(p);
	}
}
