package com.ssafy.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.dto.FollowMemberDto;

public interface ProfileService {
	// 프로필 이미지 설정
	void setProfileImg(String profileImgUri);

	// 프로필 한줄소개 설정
	void setProfileIntoduce(String introduce);

	// 프로필 내용 가져오기
	Map<String, Object> getProfileContent(Long memberNo);
	
	// 팔로워 리스트 가져오기
	List<FollowMemberDto> getFollowers(Long memberNo,int page);
	
	// 사람 팔로잉 리스트 가져오기
	List<FollowMemberDto> getMemberFollowings(Long memberNo,int page);
	
	// 알고리즘 팔로잉 리스트 가져오기
	List<String> getAlgorithmFollowings(Long memberNo,int page);
	
	// 문제 팔로잉 리스트 가져오기
	public List<Map<String, Object>> getProblemFollowings(Long memberNo,int page);
}
