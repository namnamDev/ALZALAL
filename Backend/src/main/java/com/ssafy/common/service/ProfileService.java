package com.ssafy.common.service;

import java.util.Map;

public interface ProfileService {
	// 프로필 이미지 설정
	void setProfileImg(String profileImgUri);

	// 프로필 한줄소개 설정
	void setProfileIntoduce(String introduce);

	// 프로필 내용 가져오기
	Map<String, Object> getProfileContent(Long memberNo);
}
