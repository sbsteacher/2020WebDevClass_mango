package com.koreait.mango.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuImgDTO {
	private int restPk;
	private List<MultipartFile> imgs;
}
