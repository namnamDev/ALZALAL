package com.ssafy.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
	private final SearchService searchService;

	// 유저 프로필에서 자신이 쓴 게시글 목록을 볼 수 있게 검색
	@GetMapping("/article/{memberNo}")
	public Map<String, List<ArticleDTO>> getArticleListByMemberNo(
			@PathVariable Long memberNo,
			@RequestParam(defaultValue = "0") int page) {

		Map<String, List<ArticleDTO>> ret = new HashMap<>();

		List<ArticleDTO> articleList = searchService
				.getArticleListByMemberNo(memberNo, page);

		ret.put("articleList", articleList);

		return ret;
	}

	// 문제검색
	@GetMapping("/problem")
	public Map<String, List<ArticleDTO>> getProblemSearch(@RequestParam String problem,
			@RequestParam(value = "language", required = false) String language , @RequestParam(required = false) List<String> and,
			@RequestParam(required = false) List<String> not,@RequestParam(defaultValue = "") String sort){
		Map<String, List<ArticleDTO>> ret = new HashMap<>();

		if(and==null || and.get(0).equals(""))
			and=new ArrayList<String>();
		if(not==null || not.get(0).equals(""))
			not=new ArrayList<String>();
		
		List<ArticleDTO> list= searchService.getProblemSearch(problem, language, and, not, 0, sort);
		
		ret.put("result", list);
		
		return ret;
	}

}
