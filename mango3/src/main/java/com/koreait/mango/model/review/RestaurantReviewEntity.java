package com.koreait.mango.model.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantReviewEntity {
	private int reviewPk;
	private int restPk;
	private int writerPk;
	private String ctnt;
	private int eval;
	private String regDt;
}
