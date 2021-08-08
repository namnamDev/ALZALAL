package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.service.AdminService;
@RestController
@RequestMapping("/admin")
public class adminController {


	@Autowired
	private AdminService adminSvc;
	
	@PostMapping("/discussion/{num}")
	public Map<String, Object> insertDiscuss(
			@RequestBody Map<String, Object> req,
			@PathVariable int num) {
		return adminSvc.insertArticle("discussion", num);
	};
	
	@PostMapping("/article/{num}")
	public Map<String, Object> insertArticle(
			@RequestBody Map<String, Object> req,
			@PathVariable int num) {
		return adminSvc.insertArticle("article", num);
	};

	//회원 30명 회원가입 되는 API
	@PostMapping("/signup/{num}")
	public Map<String, String> signup(@RequestBody Map<String, Object> req , @PathVariable int num) {
		Map<String, String> ret=new HashMap<String, String>();
		
		int cnt=adminSvc.signup(req, num);
		
		ret.put("msg",cnt+"개의 회원가입에 성공했습니다");
		return ret;
	}
	@PostMapping("/member/setNotiNo")
	public Map<String,Object> setZero(@RequestBody Map<String, Object> req){
		return adminSvc.setZero();
		
	}
	
}
