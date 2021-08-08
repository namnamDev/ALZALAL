package com.ssafy.common.service;

import java.util.Map;

public interface AdminService {
	//관리자용 게시글 자동 입력
	public Map<String, Object> likeArticle(String articleClass, long articlePk,
			Map<String, Object> req);
	Map<String, Object> sltMultiDiscussByHost(Long HostPK, int page);
	// 회원가입
	public int signup(Map<String, Object> req, int num);
	Map<String, Object> insertArticle(String articleClass, int num);
	Map<String, Object> setZero();
	
	
}
