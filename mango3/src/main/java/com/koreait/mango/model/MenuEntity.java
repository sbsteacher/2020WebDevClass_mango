package com.koreait.mango.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuEntity {
	private int menuPk;
	private String auth;
	private String link;
	private String title;
}
