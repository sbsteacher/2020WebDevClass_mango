package com.koreait.mango.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardEntity {
	private int boardPk;
	private String title;
	private String ctnt;
	private int writerPk;
	private String regDt;
}
