package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.dto.HelpmeDTO;
import com.ssafy.common.service.HelpmeSevice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/helpme")
@RequiredArgsConstructor
public class HelpmeContoller {

	private final HelpmeSevice helpmeSevice;
	
	// memberNo가 받은 문제풀이 요청 목록
	@GetMapping("/receptlist/{memberNo}")
	public Map<String,List<HelpmeDTO>> getReceptHelpmeListByMemberNo(@PathVariable Long memberNo, @RequestParam(defaultValue = "0") int page) {
		Map<String, List<HelpmeDTO>> ret=new HashMap<>();
		List<HelpmeDTO> list =helpmeSevice.getReceptHelpmeListByMemberNo(memberNo, page);
		
		ret.put("helpmeReceptList", list);
		
		return ret;
	}
	
	
	// memberNo가 신청한 문제풀이 요청 목록(로그인 유저와 memberNo가 일치해야만 보여줌)
	@GetMapping("/sendlist")
	public Map<String,List<HelpmeDTO>> getSendHelpmeListByMemberNo(@RequestParam(defaultValue = "0") int page) {
		Map<String, List<HelpmeDTO>> ret=new HashMap<>();
		List<HelpmeDTO> list =helpmeSevice.getSendHelpmeListByMemberNo(page);
		
		ret.put("helpmeSendList", list);
		
		return ret;
	}
}
