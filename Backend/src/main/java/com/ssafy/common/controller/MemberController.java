package com.ssafy.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	private MemberService ms;
	
	
	
	
}
