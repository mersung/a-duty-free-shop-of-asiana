package com.example.project.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@Service @Log
public class FileService {
	
	@Value("${imgLocation}")
	private String imgLocation; // .properties 에서 가져옴
	
	public String getFullPath(String filename) {
	    return imgLocation + "/" + filename;
	}
	
	public String uploadFile(MultipartFile multipartFile) throws Exception {
	
	    if (multipartFile.isEmpty()) {
	        return null;
	    }
	
	    String originalFilename = multipartFile.getOriginalFilename(); // image.png
	
	    // 서버에 저장하는 파일명
	    String savedFileName = createStoreFileName(originalFilename);
	    multipartFile.transferTo(new File(getFullPath(savedFileName))); // 저장
	    return savedFileName;
	}
	
	private String  createStoreFileName(String originalFilename) { // 서버에 저장되는 파일명
	    String ext = extractExt(originalFilename);
	    String uuid = UUID.randomUUID().toString();
	    return uuid + "." + ext;
	}
	
	private String extractExt(String originalFilename) { // 확장자 추출
	    int pos = originalFilename.lastIndexOf(".");
	    return originalFilename.substring(pos + 1);
	}
	
	public void deleteFile(String filePath){ // 이미지 수정했을 때 기존 파일 삭제
	    File deleteFile = new File(getFullPath(filePath));
	    if (deleteFile.exists()) {
	        deleteFile.delete();
	        log.info("파일을 삭제하였습니다.");
	    } else {
	        log.info("파일이 존재하지 않습니다.");
	    }
	}
}