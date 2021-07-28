package com.ssafy.common.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.service.FileService;
import com.ssafy.common.service.MemberService;
import com.ssafy.common.service.ProfileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private MemberService memberService;
	
	
	// 프로필 한줄소개 설정
	@PostMapping("/introduce")
	public Map<String, Object> setProfileIntroduce(@RequestBody Map<String, Object> req) {
		Map<String, Object> ret = new HashMap<>();
		String introduce = (String) req.get("introduce");

		try {
			// 한줄 소개 디비에 저장
			profileService.setProfileIntoduce(introduce);
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		ret.put("success", "True");
		return ret;
	}
	
	
	// 프로필 이미지 설정
	@PostMapping("/img")
	public Map<String, Object> setProfileImg(MultipartFile profileImg) {
		Map<String, Object> ret = new HashMap<>();
		String filename="";
		if (!profileImg.isEmpty()) {
			// 이미지 파일 업로드
			try {
				filename = fileService.saveFile(profileImg);
			}catch (IllegalStateException e) {
				ret.put("success", "False");
				ret.put("msg", e.getMessage());
				
			}
		}
		//이미지 파일 이름 member테이븗에 저장
		profileService.setProfileImg(filename);
		
		ret.put("success", "True");
		return ret;
	}

	//프로필 이미지 가져오기
	@GetMapping(value = "/img/{memberNo}")
	public ResponseEntity<?> getProfileImg(@PathVariable String memberNo, HttpServletRequest request) {
		Resource resource=null;
		
		String fileName=memberService.getProfileImgUri(Long.parseLong(memberNo));
	
		try {
			resource = fileService.loadFile(fileName);
		}catch (FileNotFoundException e) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			log.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
