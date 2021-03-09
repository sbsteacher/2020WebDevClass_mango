package com.koreait.mango;

import org.springframework.stereotype.Component;

@Component
public class NumberUtils {
	public int parseStrToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {}
		return 0;
	}
	
	public int parseStrToInt(String str, int def) {
		int result = parseStrToInt(str);
		return result == 0 ? def : result;
	}
}
