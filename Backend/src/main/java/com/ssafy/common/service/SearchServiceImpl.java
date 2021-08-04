package com.ssafy.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.MemberSearchDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.Algorithm.Algorithm_FolloweRepositoryCustom;
import com.ssafy.common.repository.article.ArticleRepository;
import com.ssafy.common.repository.member.MemberRepositoryCustom;
import com.ssafy.common.repository.problem.Problem_FollowRepository;
import com.ssafy.common.repository.problem.Problem_FollowRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {


	private final ArticleRepository articleRepository;

	private final Algorithm_FolloweRepositoryCustom algorithm_FolloweRepositoryCustom;
	
	private final Problem_FollowRepositoryCustom problem_FollowRepositoryCustom;
	
	private final MemberRepositoryCustom memberRepositoryCustom;
	
	
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
	@Override
	public Map<String, Object> getProblemSearch(String problem, String language,
			List<String> and, List<String> not, int page, String sort) {
		Map<String, Object> ret=new HashMap<>();
		
		if(problem.trim().length()==0)
			throw new IllegalStateException("문제를 입력해 주세요");
		
		for(String tmp1 : and) {
			for(String tmp2: not) {
				if(tmp1.equals(tmp2))
					throw new IllegalStateException("포함, 제외에 동일한 해시태그를 넣을 수없습니다");
			}
		}
		
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}
		
		Problem_Site problem_Site=new Problem_Site();
		
		//boj1234이걸 boj, 1234로 나눠줌
		char[] tmp = problem.toCharArray();
		int divIdx=0;
		for(int i=0;i<tmp.length;i++) {
			if(tmp[i]>='0' &&tmp[i]<='9') {
				divIdx=i;
				break;
			}
		}
		String problemName= problem.substring(0, divIdx);
		int problemNo=Integer.parseInt(problem.substring(divIdx, problem.length()));
		
		problem_Site.setProblemSiteName(new Problem_Site_List(problemName));
		problem_Site.setProblemNo(problemNo);
		
		
		
		List<ArticleDTO> list = articleRepository.getProblemSearch(nowLoginMemberNo,
				problem_Site,language,and,not, PageRequest.of(page, 20),sort).orElse(null);

		ret.put("aricleList", list);
		
		Problem_Site_List ptmp=new Problem_Site_List(problemName);
		Problem_Site pstmp=new Problem_Site();
		pstmp.setProblemSiteName(ptmp);
		pstmp.setProblemNo(problemNo);
		
		
		//팔로잉 정보
		Map<String,Object> followInfo=new HashMap<>();
		followInfo.put("followingNumber", problem_FollowRepositoryCustom.countProblemFollowings(problemName,(long)problemNo));
		followInfo.put("articleNumber", articleRepository.countbyProblem(pstmp));
		
		ret.put("followInfo", followInfo);
		
		
		return ret;
	}

	// 알고리즘검색
	@Override
	public Map<String, Object> getAlgorithmSearch(String language,
			List<String> and, List<String> not, int page, String sort) {
		if(and.size()==0 && not.size()==0)	
			throw new IllegalStateException("최소 1개 이상의 태그를 검색해 주세요");
		
		for(String tmp1 : and) {
			for(String tmp2: not) {
				if(tmp1.equals(tmp2))
					throw new IllegalStateException("포함, 제외에 동일한 해시태그를 넣을 수없습니다");
			}
		}
		Map<String, Object> ret=new HashMap<>();
		
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}
		
		List<ArticleDTO> list = articleRepository.getAlgorithmSearch(nowLoginMemberNo,
				language,and,not, PageRequest.of(page, 20),sort).orElse(null);

		ret.put("aricleList", list);
		
		//팔로잉 떠야된다고 알려줘야함
		if(and.size()==1 && not.size()==0) {
			Map<String,Object> followInfo=new HashMap<>();
			followInfo.put("followingNumber", algorithm_FolloweRepositoryCustom.countAlgorithmFollowings(and.get(0)));
			followInfo.put("articleNumber", articleRepository.countbyAlgorithm(and.get(0)));
			
			ret.put("followInfo", followInfo);
		}
		
		return ret;
	}
	
	//회원검색
	public List<MemberSearchDTO> getMemberSearch(String name,int page) {
		if(name.trim().length()==0)
			throw new IllegalStateException("검색어를 입력해 주세요");
		
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}
		
		List<MemberSearchDTO> list= memberRepositoryCustom.getMemberSearch(name, nowLoginMemberNo, PageRequest.of(page, 20)).orElse(null);
		
		return list;
	}

	
}
