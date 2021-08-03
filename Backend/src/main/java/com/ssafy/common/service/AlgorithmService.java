package com.ssafy.common.service;

import java.util.List;

import com.ssafy.common.domain.Algorithm;

public interface AlgorithmService {
	// 알고리즘 리스트 가져오는 메소드
	List<String> getAlgorithmList();
}
