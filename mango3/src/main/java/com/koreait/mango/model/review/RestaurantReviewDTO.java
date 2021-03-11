package com.koreait.mango.model.review;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantReviewDTO extends RestaurantReviewEntity {	
	private List<MultipartFile> imgs;
}
