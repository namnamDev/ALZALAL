package com.ssafy.common.service;

import java.util.List;

import com.ssafy.common.dto.HelpmeDTO;

public interface HelpmeSevice {
	// memberNo가 받은 문제풀이 요청 목록
	List<HelpmeDTO> getReceptHelpmeListByMemberNo(Long memberNo, int page);
	
	// memberNo가 신청한 문제풀이 요청 목록
	List<HelpmeDTO> getSendHelpmeListByMemberNo(int page);
}
