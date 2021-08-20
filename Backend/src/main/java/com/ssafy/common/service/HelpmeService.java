package com.ssafy.common.service;

import java.util.List;

import com.ssafy.common.domain.helpme.Helpme;
import com.ssafy.common.dto.HelpmeDTO;

public interface HelpmeService {
	// memberNo가 받은 문제풀이 요청 목록
	List<HelpmeDTO> getReceptHelpmeListByMemberNo(Long memberNo, int page);
	
	// memberNo가 신청한 문제풀이 요청 목록
	List<HelpmeDTO> getSendHelpmeListByMemberNo(int page);
	
	// 게시글 작성
	Long createHelpme(Helpme helpme);
	
	//게시글 상세보기
	HelpmeDTO getHelpme(Long helpmeNo);

	//게시글 수정  (문제, 요청 받는사람No, 내용 넘어옴)
	Long updateHelpme(Long helpmeNo, Helpme helpme);
	
	//게시글 삭제
	void deleteHelpme(Long helpmeNo);
	
	//풀이요청 응답
	long changeState(Long helpmeNo, String answer);
	
	//풀이요청 답변
	//helpme게시판에 해당 게시글 답변게시글로 등록
	void answerArticleMapping(Long answerArticleNo, Long helpmeNo);
}
