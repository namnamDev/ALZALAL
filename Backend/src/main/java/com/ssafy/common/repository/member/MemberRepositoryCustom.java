package com.ssafy.common.repository.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ssafy.common.dto.MemberSearchDTO;

@Repository
public interface MemberRepositoryCustom {
	//회원검색
	Optional<List<MemberSearchDTO>> getMemberSearch(String name,Long nowLoginMember,Pageable page);
}
