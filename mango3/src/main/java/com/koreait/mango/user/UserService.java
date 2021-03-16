package com.koreait.mango.user;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.mango.Const;
import com.koreait.mango.MyFileUtils;
import com.koreait.mango.model.MapDTO;
import com.koreait.mango.model.RestaurantDetailDomain;
import com.koreait.mango.model.RestaurantDomain;
import com.koreait.mango.model.RestaurantEntity;
import com.koreait.mango.model.board.BoardDomain;
import com.koreait.mango.model.board.BoardEntity;
import com.koreait.mango.model.board.BoardImgEntity;
import com.koreait.mango.model.review.RestaurantReviewDTO;
import com.koreait.mango.model.review.RestaurantReviewDomain;
import com.koreait.mango.model.review.RestaurantReviewImgEntity;
import com.koreait.mango.model.security.UserPrincipal;
import com.koreait.mango.security.IAuthenticationFacade;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	final UserMapper mapper;	
	final IAuthenticationFacade authenticationFacade;
	final MyFileUtils myFileUtils;
	
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
				String fileNm = myFileUtils.transferTo(file, path);
				
				rrie = new RestaurantReviewImgEntity();
				rrie.setReviewPk(p.getReviewPk());
				rrie.setImg(fileNm);
				
				insList.add(rrie);
			}
			
			result = mapper.insRestaurantReviewImg(insList);
		} catch(Exception e) {
			e.printStackTrace();
			if(result == 0 || insList.size() > 0) { //기존에 업로드된 이미지 삭제
				String fullPath = myFileUtils.getRealPath(path);
				for(RestaurantReviewImgEntity rrie : insList) {
					myFileUtils.delFile(path + "/" + rrie.getImg());
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
	
	public String saveBoardImg(MultipartFile img) {
		UserPrincipal user = authenticationFacade.getUserPrincipal();
		String path = Const.IMG_PATH_TEMP + user.getUserPk();
		
		try {
			String fileNm = myFileUtils.transferTo(img, path);
			return path + "/" + fileNm;
		} catch(Exception e) {
			return null;
		}
	}

	//이 메소드는 t_board 테이블의 auto_increment 기능을 사용하지 않고 오라클의 Sequence같은 것으로 대체하면 좀 더 나은 로직이 될 것 같음
	public int insBoard(BoardEntity p) {
		UserPrincipal user = authenticationFacade.getUserPrincipal();
		p.setWriterPk(user.getUserPk());
		mapper.insBoard(p);
		
		String ctnt = p.getCtnt();
		Document doc = Jsoup.parseBodyFragment(ctnt);
		Elements imgs = doc.getElementsByTag("img");
		
		BoardImgEntity bImgEntity = new BoardImgEntity();
		bImgEntity.setBoardPk(p.getBoardPk());
		
		for(Element ele : imgs) {
			String originSrc = ele.attr("src");
			String moveSrc = originSrc.replace("/temp/" + user.getUserPk(), "/board/" + p.getBoardPk());
			
			myFileUtils.moveFile(originSrc, moveSrc);					
			
			ctnt = ctnt.replace(originSrc, moveSrc);
			
			//img insert
			String saveImg = moveSrc.substring(moveSrc.lastIndexOf("/") + 1);
			bImgEntity.setImg(saveImg);
			mapper.insBoardImg(bImgEntity);
		}
		
		p.setCtnt(ctnt);
		return mapper.updBoard(p);
	}
	
	public BoardDomain selBoard(BoardEntity p) {
		return mapper.selBoard(p);
	}
	
	public List<BoardDomain> selBoardList() {
		return mapper.selBoardList();
	}
}
