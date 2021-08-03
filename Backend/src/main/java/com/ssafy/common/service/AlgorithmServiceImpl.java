package com.ssafy.common.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.repository.Algorithm.AlgorithmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlgorithmServiceImpl implements AlgorithmService{

	private final AlgorithmRepository algorithmRepository;
	
	//알고리즘 리스트 가져오는 메소드
	@Override
	public List<String> getAlgorithmList() {
		
		List<String> list=algorithmRepository.findAll().stream().map((algorithm)->algorithm.getAlgorithmName()).collect(Collectors.toList());
		
		return list;		
	}
	
}
