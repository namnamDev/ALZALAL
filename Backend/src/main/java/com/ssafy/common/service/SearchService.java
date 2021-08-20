package com.ssafy.common.service;

import java.util.List;
import java.util.Map;

import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.MemberSearchDTO;

public interface SearchService {
	//해당 멤버가 작성한 게시글 리스트 ArticleDTO로 가져옴
	List<ArticleDTO> getArticleListByMemberNo(Long memberNo,int page);

	// 문제검색
	Map<String, Object>  getProblemSearch(String problem, String language,
				List<String> and, List<String> not, int page, String sort);

	// 알고리즘검색
	Map<String, Object> getAlgorithmSearch(String language,
			List<String> and, List<String> not, int page, String sort);
	
	//회원검색
	Map<String, Object> getMemberSearch(String name,int page);
}
