package com.ssafy.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.domain.Member;
import com.ssafy.common.domain.Problem_Site_Like;
import com.ssafy.common.domain.Problem_Site_List;
import com.ssafy.common.domain.Use_Language;
import com.ssafy.common.domain.Use_Language_Like;
import com.ssafy.common.repository.MemberRepository;
import com.ssafy.common.repository.Problem_Site_ListRepository;
import com.ssafy.common.repository.Use_LanguageRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository mr;
	@Autowired
	private Problem_Site_ListRepository pr;
	@Autowired
	private Use_LanguageRepository ur;
	
	
	@Override
	public long signup(Member member,String[] problem_site_list, String[] use_language_like) {
		//name, email 중복확인
		if(mr.findByName(member.getMember_name())!=null)
			throw new IllegalStateException("이미 존재하는 이름입니다");
		if(mr.findByEmail(member.getMember_email())!=null)
			throw new IllegalStateException("이미 존재하는 이메일입니다");
		
		//선호하는 문제 사이트 추가
		List<Problem_Site_Like> pslikeList =new ArrayList<>();
		for(String s: problem_site_list) {
			Problem_Site_List pslist = pr.findOne(s);
			//선택된 문제 사이트가 존재하지 않는 경우
			if(pslist==null) {
				throw new IllegalStateException("존재하지 않는 문제 사이트 입니다");
			}
			Problem_Site_Like tmp= new Problem_Site_Like();
			tmp.setProblemSiteName(pslist);
			tmp.setMember(member);
			
			pslikeList.add(tmp);
		}
		
		//선호하는 언어 추가
		List<Use_Language_Like> ullikeList =new ArrayList<>();
		for(String s: use_language_like) {
			Use_Language ul=ur.findOne(s);
			//선택된 언어가 존재하지 않는 경우
			if(ul==null) {
				throw new IllegalStateException("존재하지 않는 언어 입니다");
			}
			Use_Language_Like tmp =new Use_Language_Like();
			tmp.setUse_language(ul);
			tmp.setMember_no(member);
			
			ullikeList.add(tmp);
		}
		
		//선호하는 사이트, 언어 추가
		member.createMember(pslikeList, ullikeList);
		
		mr.save(member);
		
		
		
		return member.getMember_no();
	}
}
