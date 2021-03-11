package com.koreait.mango;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileUtils {
	
	final WebApplicationContext webApplicationContext;

	//폴더 만들기
	public void makeFolders(String path) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
	
	//폴더 삭제
	public void delFolder(String path) {
		File folder = new File(path);
		while(folder.exists()) {
			File[] fileList = folder.listFiles();
			if(fileList == null) {
				return;
			}
			for (int j = 0; j < fileList.length; j++) {
				File f = fileList[j];
				
				if(f.isDirectory()) {
					delFolder(f.getPath());
				} else {
					f.delete();
				}
			}
			
			folder.delete();
		}
	}

	//파일 삭제
	public void delFile(String path) {
		File file = new File(path);
		if(file.exists()) {
			file.delete();
		}
	}
	
	//스프링이 돌아가고 있는 절대주소값에 path값을 붙여서 가져오기
	public String getRealPath(String path) {
		return webApplicationContext.getServletContext().getRealPath(path);
	}
	
	public String getRandomFileNm() {
		return UUID.randomUUID().toString();
	}
	
	public String getExt(String fileNm) {
		return fileNm.substring(fileNm.lastIndexOf(".") + 1);
	}
	
	public String getRandomFileNm(String fileNm) {
		return getRandomFileNm() + "." + getExt(fileNm);
	}
	
	public String getRandomFileNm(MultipartFile file) {
		return getRandomFileNm(file.getOriginalFilename());
	}
	
	//파일저장 & 랜덤파일명 구하기
	public String transferTo(MultipartFile mf, String target) throws IllegalStateException, IOException {
		String fileNm = null;
		String basePath = getRealPath(target);
		makeFolders(basePath);
		
		fileNm = getRandomFileNm(mf.getOriginalFilename());
		File file = new File(basePath, fileNm); //파일이 저장되어야 할 위치정보!!!
		mf.transferTo(file);
		
		return fileNm;
	}
}
