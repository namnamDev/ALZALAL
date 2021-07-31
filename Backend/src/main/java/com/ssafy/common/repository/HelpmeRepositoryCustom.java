package com.ssafy.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ssafy.common.dto.HelpmeDTO;

public interface HelpmeRepositoryCustom {
	// memberNo가 받은 문제풀이 요청 목록
	Optional<List<HelpmeDTO>> getReceptHelpmeListByMemberNo(Long memberNo,Long nowLoginMemberNo, Pageable page);

	// memberNo가 신청한 문제풀이 요청 목록
	Optional<List<HelpmeDTO>> getSendHelpmeListByMemberNo(Long memberNo, Pageable page);
}
