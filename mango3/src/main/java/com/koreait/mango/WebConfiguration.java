package com.koreait.mango;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Value("${spring.servlet.multipart.location}") 
	private String uploadImagePath;

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {	
		/*
		Path imgUploadDir = Paths.get("image");
		String imgUploadPath = imgUploadDir.toFile().getAbsolutePath();		
		registry.addResourceHandler("/image/**").addResourceLocations("file:/" + imgUploadPath + "/");
		*/
		registry.addResourceHandler("/image/**")
				.addResourceLocations("file:///" + uploadImagePath + "/image/")
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
	}
	
	
}
