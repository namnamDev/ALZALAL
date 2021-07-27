package com.ssafy.common.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.FileUploadProperties;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.MemberRepository;

@Service
@Transactional
public class FileServiceImpl implements FileService {
	private final Path dirLocation;

	@Autowired
	public FileServiceImpl(FileUploadProperties fileUploadProperties) {
		this.dirLocation = Paths.get(fileUploadProperties.getLocation()).toAbsolutePath().normalize();
	}

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(this.dirLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	private MemberRepository memberRepository;

	//유저 pk를 이름으로 이미지 저장
	@Override
	public String saveFile(MultipartFile multipartFile) {

		Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));

		String originalfileName = multipartFile.getOriginalFilename();
		String[] tmpstr=originalfileName.split("\\.");
		
		
		if(tmpstr.length<1)
			throw new IllegalStateException("유효하지 않은 확장자입니다");
		
		String fileName = Long.toString(member.getNo())+"."+tmpstr[tmpstr.length-1];
		
		Path location = this.dirLocation.resolve(fileName);
		try {
			/* 실제 파일이 upload 되는 부분 */
			Files.copy(multipartFile.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileName;
	}

	@Override
	public Resource loadFile(String fileName) throws FileNotFoundException {

		try {
			Path file = this.dirLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new FileNotFoundException("Could not find file");
			}
		} catch (MalformedURLException e) {
			throw new FileNotFoundException("Could not download file");
		}

	}
}
