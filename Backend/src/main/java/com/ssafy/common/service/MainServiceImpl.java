package com.ssafy.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.Article_AlgorithmDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.Algorithm.Algorithm_FolloweRepository;
import com.ssafy.common.repository.article.Article_AlgorithmRepository;
import com.ssafy.common.repository.member.Member_FollowRepository;
import com.ssafy.common.repository.member.Member_FollowRepositoryCustom;
import com.ssafy.common.repository.problem.Problem_FollowRepository;

@Service
@Transactional
public class MainServiceImpl implements MainService{
	@Autowired
	private Member_FollowRepositoryCustom memfolRepo;
	
	@Autowired
	private Algorithm_FolloweRepository algoFolRepo;
	
	@Autowired
	private Problem_FollowRepository proFolRepo;
	@Autowired
	private Article_AlgorithmRepository ArtiAlgoRepo;
	@Override
	public Map<String, Object> sltMainMulti(int page){
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		List<ArticleDTO> mainArticle= memfolRepo.getFollowingArticle(nowLoginMemberNo, PageRequest.of(page, 20));
		for(int idx = 0; idx < mainArticle.size();idx++) {
    		List<Article_AlgorithmDTO> artiAlgo= ArtiAlgoRepo.sltMultiByArticleDTO(mainArticle.get(idx));
    		List<String> tempAlgo = new ArrayList<String>();
    		for(Article_AlgorithmDTO AlgoOne:artiAlgo) {
    			tempAlgo.add(AlgoOne.getUsedAlgorithm());
    		}
    		mainArticle.get(idx).setAlgo(tempAlgo);
    	}
		res.put("article", mainArticle);
		return res;
		//팔로우 목록 조회
		
	}
}
