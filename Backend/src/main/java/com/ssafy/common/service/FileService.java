package com.ssafy.common.service;

import java.io.FileNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	//파일저장
	public String saveFile(MultipartFile multipartFile);
	//파일불러오기
	public Resource loadFile(String fileName) throws FileNotFoundException;
}
