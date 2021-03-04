package com.koreait.mango.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koreait.mango.Const;

@Service
public class APIService {
	
	public Map<String, Double> getAddrLatLng(String addr) {		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "KakaoAK " + Const.KAKAO_REST_API_KEY);
        
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		/*
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Const.KAKAO_SEARCH_ADDR_URL)
		        .queryParam("query", addr);
*/
		final String URL = Const.KAKAO_SEARCH_ADDR_URL + "?query=" + addr;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> respEntity = restTemplate.exchange(URL
																	, HttpMethod.GET
																	, entity
																	, String.class);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
		    JsonNode jsonNode = objectMapper.readTree(respEntity.getBody());
		    int totalCount = objectMapper.treeToValue(jsonNode.path("meta").path("total_count"), Integer.class);
		    if(totalCount > 0) {
		    	double lat = objectMapper.treeToValue(jsonNode.path("documents").get(0).path("y"), Double.class);
			    double lng = objectMapper.treeToValue(jsonNode.path("documents").get(0).path("x"), Double.class);
			    
			    Map<String, Double> result = new HashMap();
			    result.put("lat", lat);
			    result.put("lng", lng);
			    return result;
		    }		    
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
