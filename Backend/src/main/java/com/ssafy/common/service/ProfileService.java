package com.ssafy.common.service;

public interface ProfileService {
	// 프로필 이미지 설정
	void setProfileImg(String profileImgUri);

	// 프로필 한줄소개 설정
	void setProfileIntoduce(String introduce);

}
