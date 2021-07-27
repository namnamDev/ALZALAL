package com.ssafy.common.service;

public interface ProfileService {
	// 프로필 이미지 수정
	void setProfileImg(String profileImgUri);

	// 프로필 한줄소개 수정
	void setProfileIntoduce(String introduce);

}
