package com.ssafy.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.ssafy.common.domain.Use_Language_Like;
import com.ssafy.common.domain.helpme.Helpme_Class;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site_Like;
import com.ssafy.common.dto.FollowMemberDto;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.HelpmeRepository;
import com.ssafy.common.repository.Algorithm.Algorithm_FolloweRepositoryCustom;
import com.ssafy.common.repository.article.ArticleRepositoryImpl;
import com.ssafy.common.repository.member.MemberRepository;
import com.ssafy.common.repository.member.Member_FollowRepository;
import com.ssafy.common.repository.member.Member_FollowRepositoryCustom;
import com.ssafy.common.repository.problem.Problem_FollowRepositoryCustom;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private Member_FollowRepository memberFollowRepository;
	
	@Autowired
	private Member_FollowRepositoryCustom member_FollowRepositoryCustom;
	
	@Autowired
	private Algorithm_FolloweRepositoryCustom algorithm_FolloweRepositoryCustom;
	
	@Autowired
	private Problem_FollowRepositoryCustom followRepositoryCustom;

	@Autowired
	private ArticleRepositoryImpl articleRepositoryImpl;

	@Autowired
	private HelpmeRepository helpmeRepository;

	// 프로필 이미지 설정
	@Override
	public void setProfileImg(String profileImgUri) {
		Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));

		member.setProfileImg(profileImgUri);
		return;
	}

	// 프로필 한줄소개 설정
	@Override
	public void setProfileIntoduce(String introduce) {
		Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));

		member.setIntroduce(introduce);
		return;
	}

	// 프로필 내용 가져오기
	// - 번호, 닉네임, 선호하는 언어, 선호하는 알고리즘사이트, 한줄소개, 팔로우 명수(사람만), 팔로잉 명수, 작성게시글 갯수, helpme전체갯수,
	// helpme 응답갯수
	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getProfileContent(Long memberNo) {
		// 닉네임, 한줄소개 가져옴
		Member member = memberRepository.findById(memberNo)
				.orElseThrow(() -> new IllegalStateException("유저를 찾을 수 없습니다"));

		// 선호하는 사이트 리스트 가져옴
		List<String> problemSiteList = new ArrayList<String>();
		for (Problem_Site_Like tmp : member.getProblemSiteList()) {
			problemSiteList.add(tmp.getProblemSiteName().getProblemSiteName());
		}

		// 선호하는 언어 리스트 가져옴
		List<String> useLanguageLike = new ArrayList<String>();
		for (Use_Language_Like tmp : member.getUseLanguageLike()) {
			useLanguageLike.add(tmp.getUseLanguage().getUseLanguage());
		}
		
		// 팔로잉 명수 가져옴
		long following = memberFollowRepository.countByFollowNo(member);
		// 팔로워 명수 가져옴
		long follower = memberFollowRepository.countByMemberNo(member);

		// 작성게시글 갯수
		long articleCount = articleRepositoryImpl.countByMember(member.getNo());

		// helpme 전체갯수
		long helpmeCount = helpmeRepository.countByHelpmeReceptorNo(member);

		// helpme 응답완료 갯수
		long helpmeSuccessCount = helpmeRepository.countByHelpmeReceptorNoAndHelpmeStatus(member,Helpme_Class.H03);
		
		// 로그인 한 유저의 해당 유저에대한 팔로우 여부
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}
		Member nowLoginMember=memberRepository.findById(nowLoginMemberNo).orElse(null);
		long tmpcount= memberFollowRepository.countByMemberNoAndFollowNo(nowLoginMember, member);
		boolean isfollow= tmpcount>0?true:false;

		Map<String, Object> ret=new HashMap();
		ret.put("no",member.getNo());
		ret.put("name", member.getName());
		ret.put("language", useLanguageLike);
		ret.put("problemsite", problemSiteList);
		ret.put("introduce", member.getIntroduce());
		
		ret.put("following", following);
		ret.put("follower", follower);
		ret.put("articleCount", articleCount);
		ret.put("helpmeCount", helpmeCount);
		ret.put("helpmeSuccessCount", helpmeSuccessCount);
		ret.put("followState", isfollow);
		
		return ret;
	}
	
	// 팔로워 리스트 가져오기
	@Transactional(readOnly = true)
	@Override
	public List<FollowMemberDto> getFollowers(Long memberNo,int page) {
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}

		List<FollowMemberDto> members= member_FollowRepositoryCustom.getFollowers(memberNo,nowLoginMemberNo,PageRequest.of(page, 20));	
	
		return members;
	}
	
	// 사람 팔로잉 리스트 가져오기
	@Transactional(readOnly = true)
	@Override
	public List<FollowMemberDto> getMemberFollowings(Long memberNo,int page) {
		Long nowLoginMemberNo = 0L;
		try {
			// 로그인 중이면 현재 로그인중인 유저 기준으로 likeState 설정
			nowLoginMemberNo = SecurityUtil.getCurrentMemberId();
		} catch (RuntimeException e) {
			nowLoginMemberNo = 0L;
		}
		
		List<FollowMemberDto> members= member_FollowRepositoryCustom.getMemberFollowings(memberNo,nowLoginMemberNo,PageRequest.of(page, 20));
	
		return members;
	}
	
	// 알고리즘 팔로잉 리스트 가져오기
	@Transactional(readOnly = true)
	@Override
	public List<String> getAlgorithmFollowings(Long memberNo,int page) {
		
		List<String> algorithm= algorithm_FolloweRepositoryCustom.getAlgorithmFollowings(memberNo,PageRequest.of(page, 20));
		
	
		return algorithm;
	}
	
	// 문제 팔로잉 리스트 가져오기
	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> getProblemFollowings(Long memberNo,int page) {
		
		List<Tuple> problem = followRepositoryCustom.getProblemFollowings(memberNo, PageRequest.of(page, 20));		
		List<Map<String, Object>> followingList = new ArrayList<>();
		
		for(Tuple prob: problem) {
			Map<String,Object> tmp =new HashMap<>();
			tmp.put("site",prob.get(0,String.class));
			tmp.put("no",prob.get(1,Long.class));
			followingList.add(tmp);
		}
	
		return followingList;
	}
}
