package com.koreait.mango;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.koreait.mango.model.MenuEntity;

import lombok.Getter;


@Component
@Getter
public class MenuInfo {
	private List<MenuEntity> userMenus;
	private List<MenuEntity> adminMenus;
	
	@Autowired
	public MenuInfo(HomeMapper mapper) {
		init(mapper);
	}
	
	private void init(HomeMapper mapper) {
		MenuEntity p = new MenuEntity();
		p.setAuth("ROLE_USER");
		this.userMenus = mapper.selMenus(p);
		p.setAuth("ROLE_ADMIN");
		this.adminMenus = mapper.selMenus(p);
	}
}
