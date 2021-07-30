package com.ssafy.common.service;

import java.util.List;

import com.ssafy.common.dto.ArticleDTO;

public interface SearchService {
	//해당 멤버가 작성한 게시글 리스트 ArticleDTO로 가져옴
	List<ArticleDTO> getArticleListByMemberNo(Long memberNo,int page);
}
