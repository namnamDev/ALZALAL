package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.domain.Member;
import com.ssafy.common.service.MemberService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService ms;
	
	
	@PostMapping("/signup")
	public Map<String,String> signup(String[] problem_site_list2, String[] use_language_like2,  Member member) {
//		//선호 문제사이트 member에 넣어줌
//		for(String s: problem_site_list)
//			member.getProblem_site_list().add(new Problem_Site_Like());
//		//선호 언어 member에 넣어줌
		
		Map<String,String> ret=new HashMap<>();
		try {			
			ms.signup(member,problem_site_list2, use_language_like2);
		}catch(IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		ret.put("success", "True");
		ret.put("msg", "회원가입 성공");
		
		return ret;
	}
	
	
}
