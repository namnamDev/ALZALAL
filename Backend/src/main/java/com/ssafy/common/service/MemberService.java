package com.ssafy.common.service;

import java.util.List;
import java.util.Map;

import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.TokenDto;

public interface MemberService {

//	회원가입
	long signup(Member member,List<String> problem_site_list, List<String> use_language_like);
	
	//로그인
	TokenDto login(Member member);
	
	//리프레시 토큰 요청
    TokenDto refresh(TokenDto tokenDto);
    
//    //email을 통한 유저정보 가져오기
//    Member getMemberInfo(String email);
    
    //현재 로그인한 유저의 유저정보 가져오기
    Map<String, Object> getMyInfo();
    
    //현재 로그인한 유저의 비밀번호 확인
    void checkPassword(String password);
    
    //회원정보 수정
    TokenDto setMemberInfo(Member member, List<String> problem_site_list, List<String> use_language_like);
    
    //회원탈퇴
    void deleteMember();
    
  //memberNo를 통한 프로필이미지 이름 출력
    String getProfileImgUri(Long memberNo);
}
