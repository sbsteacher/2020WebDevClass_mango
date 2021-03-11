package com.koreait.mango.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.mango.Const;
import com.koreait.mango.FileUtils;
import com.koreait.mango.NumberUtils;
import com.koreait.mango.model.MenuImgDTO;
import com.koreait.mango.model.RestaurantDetailDomain;
import com.koreait.mango.model.RestaurantDomain;
import com.koreait.mango.model.RestaurantEntity;
import com.koreait.mango.model.RestaurantMenuImgEntity;
import com.koreait.mango.model.RestaurantMenuInfoEntity;
import com.koreait.mango.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {	
	
	final AdminMapper mapper;
	final UserService userService;
	final FileUtils fileUtils;
	final NumberUtils numberUtils;
	
	public int insRestaurant(RestaurantEntity p) {
		return mapper.insRestaurant(p);
	}
	
	public int regMenuInfo(int restPk, String[] menuNm, String[] menuPrice) {		
		if(restPk == 0 || menuNm == null || menuPrice == null || menuNm.length == 0 || menuPrice.length == 0) {
			return 0;
		}
		List<RestaurantMenuInfoEntity> insList = new ArrayList();
		RestaurantMenuInfoEntity rmie = null;
		for(int i=0; i<menuNm.length; i++) {
			rmie = new RestaurantMenuInfoEntity();
			rmie.setRestPk(restPk);
			rmie.setMenuNm(menuNm[i]);
			rmie.setMenuPrice(numberUtils.parseStrToInt(menuPrice[i]));
			insList.add(rmie);
		}
		
		return mapper.insRestaurantMenuInfo(insList);
	}
	
	public void regMenuImg(MenuImgDTO p) {
		String path = Const.IMG_PATH_REST + p.getRestPk();
		
		List<RestaurantMenuImgEntity> insList = new ArrayList();
		int result = 0;
		try {
			RestaurantMenuImgEntity rme = null;
			for(MultipartFile file : p.getImgs()) {				
				String fileNm = fileUtils.transferTo(file, path);
				
				rme = new RestaurantMenuImgEntity();					
				rme.setRestPk(p.getRestPk());
				rme.setImg(fileNm);
				
				insList.add(rme);
			}
			
			result = mapper.insRestaurantMenuImg(insList);
		} catch(Exception e) {
			e.printStackTrace();
			
			if(result == 0 || insList.size() > 0) { //기존에 업로드된 이미지 삭제
				path = fileUtils.getRealPath(path);
				
				for(RestaurantMenuImgEntity rme : insList) {
					fileUtils.delFile(path + "/" + rme.getImg());
				}
			}
		}
		
		System.out.println("path : " + path);
	}
	
	public List<RestaurantDomain> selRestaurantList() {
		return mapper.selRestaurantList();
	}
	
	public RestaurantDetailDomain detailRestaurant(RestaurantEntity p) {
		return userService.detailRestaurant(p);
	}
}
