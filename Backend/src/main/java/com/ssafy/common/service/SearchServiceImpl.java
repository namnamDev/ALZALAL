package com.ssafy.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.ProblemSiteDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.article.ArticleRepository;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ArticleRepository articleRepository;

	// 해당 멤버가 작성한 게시글 리스트 ArticleDTO로 가져옴
	@Override
	public List<ArticleDTO> getArticleListByMemberNo(Long memberNo, int page) {
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}

		List<ArticleDTO> ret = articleRepository.getListByMemberNO(memberNo,
				nowLoginMemberNo, PageRequest.of(page, 20)).orElse(null);

		return ret;
	}

	// 문제검색
	public List<ArticleDTO> getProblemSearch(String problem, String language,
			List<String> and, List<String> not, int page, String sort) {
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}
		
		Problem_Site problem_Site=new Problem_Site();
		
		char[] tmp = problem.toCharArray();
		int divIdx=0;
		for(int i=0;i<tmp.length;i++) {
			if(tmp[i]>='0' &&tmp[i]<='9') {
				divIdx=i;
				break;
			}
		}
	
		problem_Site.setProblemSiteName(new Problem_Site_List(problem.substring(0, divIdx)));
		problem_Site.setProblemNo(Integer.parseInt(problem.substring(divIdx, problem.length())));
		
		
		
		List<ArticleDTO> ret = articleRepository.getProblemSearch(nowLoginMemberNo,
				problem_Site,language,and,not, PageRequest.of(page, 20),sort).orElse(null);

		return ret;
	}

}
