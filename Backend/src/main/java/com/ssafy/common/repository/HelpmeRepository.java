package com.ssafy.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.helpme.Helpme;
import com.ssafy.common.domain.helpme.Helpme_Class;
import com.ssafy.common.domain.member.Member;

public interface HelpmeRepository extends JpaRepository<Helpme, Long>,HelpmeRepositoryCustom {

	//memberNo가 요청받은 게시물의 갯수
	Long countByHelpmeReceptorNo(Member member);
	
	//memberNo가 요청받은 게시글중 주어진 상태의 갯수
	Long countByHelpmeReceptorNoAndHelpmeStatus(Member member, Helpme_Class helpmeStatus);	
}
