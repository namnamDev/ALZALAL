package com.ssafy.common.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.common.domain.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	// name을 통한 select
	Optional<Member> findByName(String name);

	// email을 통한 select
	Optional<Member> findByEmail(String email);

	// no을 통한 select
	Optional<Member> findByNo(Long no);

}
