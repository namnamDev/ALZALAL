package com.ssafy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.domain.Member;
import com.ssafy.common.repository.MemberRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository mr;

	@Override
	public long signup(Member member) {
		//name, email 중복확인
		if(mr.findByName(member.getMember_name())!=null)
			throw new IllegalStateException("이미 존재하는 이름입니다");
		if(mr.findByEmail(member.getMember_email())!=null)
			throw new IllegalStateException("이미 존재하는 이메일입니다");
		
		mr.save(member);
		return member.getMember_no();
	}
	
	
	
	
}
