package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.service.AlgorithmService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AlgorithmController {

	private final AlgorithmService algorithmService;
	
	@GetMapping("/algorithmList")
	public Map<String, List<String>> getAlgorithmList() {
		Map<String, List<String>> ret=new HashMap<>();
		
		List<String> list=algorithmService.getAlgorithmList();
		ret.put("algorithmList", list);
		return ret;
	}	
}
