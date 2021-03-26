package com.koreait.mango;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.koreait.mango.model.security.UserPrincipal;
import com.koreait.mango.security.IAuthenticationFacade;
import com.koreait.mango.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
	
	final HomeMapper mapper;
	final MenuInfo menuInfo;
	
	@ModelAttribute
    public void handleRequest(HttpServletRequest request, Locale locale, Model model) {
		String servletPath = request.getServletPath();
	
		// view 페이지 호출할 때 메뉴 조립
        if(!servletPath.isEmpty() && !servletPath.contains("/api")){
        	if(request.isUserInRole("ROLE_USER")) {
        		model.addAttribute(MyKey.MENUS.getVal(), menuInfo.getUserMenus());
        	} else if(request.isUserInRole("ROLE_ADMIN")) {
        		model.addAttribute(MyKey.MENUS.getVal(), menuInfo.getAdminMenus());
        	}      	
        }
	}
}
