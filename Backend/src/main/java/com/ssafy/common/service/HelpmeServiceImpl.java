package com.ssafy.common.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.dto.HelpmeDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.HelpmeRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HelpmeServiceImpl implements HelpmeSevice {

	private final HelpmeRepositoryCustom helpmeRepositoryCustom;

	// memberNo가 받은 문제풀이 요청 목록
	public List<HelpmeDTO> getReceptHelpmeListByMemberNo(Long memberNo,
			int page) {
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}
		List<HelpmeDTO> ret = helpmeRepositoryCustom
				.getReceptHelpmeListByMemberNo(memberNo, nowLoginMemberNo,PageRequest.of(page, 20))
				.orElse(null);

		return ret;
	}

	// memberNo가 신청한 문제풀이 요청 목록
	// 이건 자기 프로필에서만 보여지는거니까 memberNo, nowLoginMemberNo가 동일인물임
	public List<HelpmeDTO> getSendHelpmeListByMemberNo(int page) {
		Long memberNo = SecurityUtil.getCurrentMemberId();

		List<HelpmeDTO> ret = helpmeRepositoryCustom
				.getSendHelpmeListByMemberNo(memberNo, PageRequest.of(page, 20))
				.orElse(null);

		return ret;
	}

}
