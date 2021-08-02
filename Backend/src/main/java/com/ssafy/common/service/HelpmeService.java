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
	public long createHelpme(Helpme helpme);
	
	//게시글 상세보기
	public HelpmeDTO getHelpme(Long helpmeNo);
}
