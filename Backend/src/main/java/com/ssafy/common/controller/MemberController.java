package com.ssafy.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.domain.Use_Language_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site_Like;
import com.ssafy.common.dto.TokenDto;
import com.ssafy.common.service.MemberService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@PostMapping("/signup")
	public Map<String, String> signup(@RequestBody Map<String, Object> req) {

		Member member = new Member();
		member.setEmail((String) req.get("email"));
		member.setPassword((String) req.get("password"));
		member.setName((String) req.get("name"));
		List<String> problem_site = (List<String>) (req.get("problem_site"));
		List<String> use_language = (List<String>) (req.get("use_language"));

		Map<String, String> ret = new HashMap<>();
		try {
			memberService.signup(member, problem_site, use_language);
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		ret.put("success", "True");
		ret.put("msg", "회원가입 성공");

		return ret;
	}

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Member member) {
		Map<String, Object> ret = new HashMap<>();
		TokenDto token;
		try {
			token = memberService.login(member);
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}

		ret.put("success", "True");
		ret.put("msg", "로그인 성공");
		ret.put("token", token);

		return ret;
	}

	@PostMapping("/refresh") // accessToken, refreshToken 두개 파라미터로 넘어와야함
	public Map<String, Object> refresh(@RequestBody TokenDto tokenRequestDto) {
		Map<String, Object> ret = new HashMap<>();

		try {
			ret.put("success", "True");
			ret.put("token", memberService.refresh(tokenRequestDto));
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());

		}

		return ret;
	}

	// 비밀번호 확인
	@PostMapping("/checkpw")
	public Map<String, String> checkPassword(@RequestBody Map<String, String> req) {
		Map<String, String> ret = new HashMap<>();
		String password = req.get("password");

		try {
			memberService.checkPassword(password);
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		ret.put("success", "True");
		
		return ret;
	}

	// 회원 정보 가져오기
	@GetMapping("/modify")
	public Map<String, Object> getMemberInfo() {
		Map<String, Object> ret = new HashMap<>();

		Member member = null;

		try {
			member = memberService.getMyInfo();
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		
		Map<String, Object> mem = new HashMap<>();
		mem.put("no", member.getNo());
		mem.put("email", member.getEmail());
		mem.put("name", member.getName());

		// 선호하는 사이트 리스트 가져옴
		List<String> problemSiteList = new ArrayList<String>();
		for (Problem_Site_Like tmp : member.getProblemSiteList()) {
			problemSiteList.add(tmp.getProblemSiteName().getProblemSiteName());
		}
		mem.put("problemSiteList", problemSiteList);

		// 선호하는 언어 리스트 가져옴
		List<String> useLanguageLike = new ArrayList<String>();
		for (Use_Language_Like tmp : member.getUseLanguageLike()) {
			useLanguageLike.add(tmp.getUseLanguage().getUseLanguage());
		}
		mem.put("useLanguageLike", useLanguageLike);

		ret.put("success", "True");
		ret.put("member", mem);
		
		return ret;
	}

	// 회원 정보 수정
	@PostMapping("/modify")
	public Map<String, Object> setMemberInfo(@RequestBody Map<String, Object> req) {
		Map<String, Object> ret = new HashMap<>();

		Member member = new Member();
		
		if(req.containsKey("password"))
			member.setPassword((String)req.get("password"));
		else
			member.setPassword("");
				
		member.setName((String) req.get("name"));
		List<String> problem_site = (List<String>) (req.get("problem_site"));
		List<String> use_language = (List<String>) (req.get("use_language"));
		
		TokenDto token;
		try {
			token=memberService.setMemberInfo(member, problem_site, use_language);
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		
		ret.put("success", "True");
		ret.put("token", token);
		
		return ret;
	}

}
