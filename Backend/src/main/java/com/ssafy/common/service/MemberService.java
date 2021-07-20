package com.ssafy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.common.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository mr;
	
	
	
	
}
