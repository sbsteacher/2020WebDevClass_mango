package com.koreait.mango.model.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDomain extends BoardEntity {
	private String writerNm;
	private String writerProfileImg;
}
