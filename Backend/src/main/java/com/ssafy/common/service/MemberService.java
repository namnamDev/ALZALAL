package com.ssafy.common.service;

import java.util.List;

import com.ssafy.common.domain.Member;
import com.ssafy.common.dto.TokenDto;

public interface MemberService {

//	회원가입
	long signup(Member member,List<String> problem_site_list, List<String> use_language_like);
	
	//로그인
	TokenDto login(Member member);
	
	//리프레시 토큰 요청
    TokenDto refresh(TokenDto tokenDto);
    
    //email을 통한 유저정보 가져오기
    public Member getMemberInfo(String email);
    
    //현재 로그인한 유저의 유저정보 가져오기
    public Member getMyInfo();
}
