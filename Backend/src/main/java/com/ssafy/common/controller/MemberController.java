package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.domain.Member;
import com.ssafy.common.service.MemberServiceImpl;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/account")
public class MemberController {

	@Autowired
	private MemberServiceImpl ms;
	
	
	@PostMapping("/signup")
	public Map<String,String> signup(Member member) {
		Map<String,String> ret=new HashMap<>();
		try {
			ms.signup(member);
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
