package com.ssafy.common.service;

import java.util.Map;

public interface FollowService {

	//멤버 팔로우, 팔로우취소
	Map<String,Object> memberFollow(Long memberNo);
	//알고리즘 팔로우, 팔로우취소
	Map<String,Object> algorithmFollow(String algorithm);
	//문제 팔로우, 팔로우취소
	Map<String,Object> problemFollow(String problemSite, int problemNo);
}
