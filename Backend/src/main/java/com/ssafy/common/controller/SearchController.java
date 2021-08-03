package com.ssafy.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	@GetMapping("/article/problem")
	public Map<String, Object> getProblemSearch(
			@RequestParam String problem,
			@RequestParam(value = "language", required = false) String language,
			@RequestParam(required = false) List<String> and,
			@RequestParam(required = false) List<String> not,
			@RequestParam(defaultValue = "") String sort,
			@RequestParam(defaultValue = "0") int page) {
		Map<String, Object> ret = new HashMap<>();

		if (and == null || and.get(0).equals(""))
			and = new ArrayList<String>();
		if (not == null || not.get(0).equals(""))
			not = new ArrayList<String>();

		try {
		ret = searchService.getProblemSearch(problem,
				language, and, not, page, sort);
		}catch (IllegalStateException e) {
			ret.put("success","False");
			ret.put("msg", e.getMessage());
		}
		ret.put("success", "True");


		return ret;
	}

	// 알고리즘검색
	@GetMapping("/article/algorithm")
	public Map<String, Object> getAlgorithmSearch(
			@RequestParam(value = "language", required = false) String language,
			@RequestParam(required = false) List<String> and,
			@RequestParam(required = false) List<String> not,
			@RequestParam(defaultValue = "") String sort,
			@RequestParam(defaultValue = "0") int page) {
		Map<String, Object> ret = new HashMap<>();

		if (and == null || and.get(0).equals(""))
			and = new ArrayList<String>();
		if (not == null || not.get(0).equals(""))
			not = new ArrayList<String>();

		
		try{
			ret = searchService.getAlgorithmSearch(language, and,
				not, page, sort);
		}catch (IllegalStateException e) {
			ret.put("success","False");
			ret.put("msg", e.getMessage());
		}
		ret.put("success", "True");

		return ret;
	}
	
	// 유저검색
	@GetMapping("/article/algorithm")
	public Map<String, Object> getMemberSearch(
			@RequestParam(value = "language", required = false) String language,
			@RequestParam(required = false) List<String> and,
			@RequestParam(required = false) List<String> not,
			@RequestParam(defaultValue = "") String sort,
			@RequestParam(defaultValue = "0") int page) {
		Map<String, Object> ret = new HashMap<>();

		if (and == null || and.get(0).equals(""))
			and = new ArrayList<String>();
		if (not == null || not.get(0).equals(""))
			not = new ArrayList<String>();

		
		try{
			ret = searchService.getAlgorithmSearch(language, and,
				not, page, sort);
		}catch (IllegalStateException e) {
			ret.put("success","False");
			ret.put("msg", e.getMessage());
		}
		ret.put("success", "True");

		return ret;
	}
	
}
