package com.koreait.mango.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {
	
	@Autowired
	private APIService service;
	
	@GetMapping("/getAddrLatLng")
	public Map<String, Double> getAddrLatLng(@RequestParam String addr) {
		return service.getAddrLatLng(addr);
	}
}
