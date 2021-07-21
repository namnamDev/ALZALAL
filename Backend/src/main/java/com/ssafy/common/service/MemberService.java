package com.ssafy.common.service;

import com.ssafy.common.domain.Member;

public interface MemberService {

//	회원가입
	long signup(Member member,String[] problem_site_list, String[] use_language_like);
	
}
