package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	SearchService searchService;
	
	//유저 프로필에서 자신이 쓴 게시글 목록을 볼 수 있게 검색
	@GetMapping("/article/{memberNo}")
	public Map<String,List<ArticleDTO>> getArticleListByMemberNo(@PathVariable Long memberNo, @RequestParam(defaultValue = "0") int page) {
		
		Map<String, List<ArticleDTO>> ret=new HashMap<>();
		
		List<ArticleDTO> articleList= searchService.getArticleListByMemberNo(memberNo, page);
		
		ret.put("articleList", articleList);
				
		return ret;		
	}
	
}
