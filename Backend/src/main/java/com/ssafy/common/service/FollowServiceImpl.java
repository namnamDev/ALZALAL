package com.ssafy.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.domain.Algorithm_Follow;
import com.ssafy.common.domain.Algorithm_FollowPK;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.member.Member_Follow;
import com.ssafy.common.domain.member.Member_FollowPK;
import com.ssafy.common.domain.problem.Problem_Follow;
import com.ssafy.common.domain.problem.Problem_FollowPK;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_SitePK;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.AlgorithmRepository;
import com.ssafy.common.repository.Algorithm_FolloweRepository;
import com.ssafy.common.repository.MemberRepository;
import com.ssafy.common.repository.Member_FollowRepository;
import com.ssafy.common.repository.Problem_FollowRepository;
import com.ssafy.common.repository.Problem_Site_ListRepository;
import com.ssafy.common.repository.Problem_Site_Repository;

@Service
@Transactional
public class FollowServiceImpl implements FollowService{
	
	@Autowired
	private Member_FollowRepository member_FollowRepository;
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private Algorithm_FolloweRepository algorithm_FolloweRepository;
	@Autowired
	private AlgorithmRepository algorithmRepository;
	
	@Autowired
	private Problem_Site_Repository problem_Site_Repository;
	@Autowired
	private Problem_Site_ListRepository problem_Site_ListRepository;
	@Autowired
	private Problem_FollowRepository problem_FollowRepository;
	
	
	
	//멤버 팔로우, 팔로우취소
	@Override
	public Map<String,Object> memberFollow(Long memberNo) {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Member followerMember = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		
		if(memberNo==followerMember.getNo()) {
			throw new IllegalStateException("자기 자신을 팔로우 할 수 없습니다");
		}

		Member followingMember=memberRepository.findByNo(memberNo)
				.orElseThrow(()-> new IllegalStateException("존재하지 않는 유저 입니다"));
		
		Member_FollowPK member_FollowPK=new Member_FollowPK(followerMember.getNo(), followingMember.getNo());
		
		//이미 팔로우 중인지 확인
		if(member_FollowRepository.findById(member_FollowPK).isPresent()) {
			member_FollowRepository.deleteById(member_FollowPK);
			ret.put("success","True");
			ret.put("msg","팔로우 취소 완료");
			return ret;
		}
		
		Member_Follow member_Follow=new Member_Follow();
		
		//팔로우 하는사람 설정
		member_Follow.setMemberNo(followerMember);
		
		//팔로우 당하는사람 설정
		member_Follow.setFollowNo(followingMember);
		
		member_FollowRepository.save(member_Follow);
		
		ret.put("success","True");
		ret.put("msg","팔로우 완료");
		return ret;
	}
	
	//알고리즘 팔로우, 팔로우취소
	@Override
	public Map<String,Object> algorithmFollow(String algorithm) {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Member followerMember = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		
		Algorithm followingAlgorithm= algorithmRepository.findById(algorithm)
				.orElseThrow(() -> new IllegalStateException("존재하지 않는 알고리즘 입니다"));
		
		Algorithm_FollowPK algorithm_FollowPK=new Algorithm_FollowPK(followerMember.getNo(),algorithm);
		//이미 팔로우 중인지 확인
		if(algorithm_FolloweRepository.findById(algorithm_FollowPK).isPresent()) {
			algorithm_FolloweRepository.deleteById(algorithm_FollowPK);
			ret.put("success","True");
			ret.put("msg","팔로우 취소 완료");
			return ret;
		}
		
		Algorithm_Follow algorithm_Follow=new Algorithm_Follow();
		
		//팔로우 하는사람 설정
		algorithm_Follow.setMemberNo(followerMember);
		
		//팔로우 당하는알고리즘 설정
		algorithm_Follow.setFollowingArgorithmNo(followingAlgorithm);
		
		algorithm_FolloweRepository.save(algorithm_Follow);
		
		ret.put("success","True");
		ret.put("msg","팔로우 완료");
		return ret;
	}
	
	//문제 팔로우, 팔로우취소
	@Override
	public Map<String,Object> problemFollow(String problemSite, int problemNo) {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Member followerMember = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		
		Problem_Site_List problem_Site_List = problem_Site_ListRepository.findOne(problemSite);
	
		if(problem_Site_List==null) {
			throw new IllegalStateException("존재하지 않는 사이트 입니다");
		}
		
		System.out.println("---------------------------------  "+ problem_Site_List);
		System.out.println("---------------------------------  "+ problem_Site_List.getProblemSiteName());
		Problem_Site followingProblem = null;
		
		Problem_SitePK problem_SitePK = new Problem_SitePK(problemSite,problemNo);
		
		followingProblem= problem_Site_Repository.findById(problem_SitePK).orElse(null);
		
		if(followingProblem==null) {
			System.out.println("문제 없어서 삽입함 ");
			followingProblem=new Problem_Site();
			followingProblem.setProblemSiteName(problem_Site_List);
			followingProblem.setProblemNo(problemNo);
			problem_Site_Repository.save(followingProblem);
			problem_Site_Repository.flush();		
		}
	
		System.out.println("aaaa "+followingProblem);

		
		Problem_FollowPK problem_FollowPK=new Problem_FollowPK(followerMember.getNo(),problem_SitePK);
		System.out.println("zzzzzzzzzzzzzzzzz0");
		//이미 팔로우 중인지 확인
		if(problem_FollowRepository.findById(problem_FollowPK).isPresent()) {
			System.out.println("zzzzzzzzzzzzzzzzz00");
			problem_FollowRepository.deleteById(problem_FollowPK);
			ret.put("success","True");
			ret.put("msg","팔로우 취소 완료");
			return ret;
		}
		System.out.println("zzzzzzzzzzzzzzzzz1");
		
		Problem_Follow problem_Follow=new Problem_Follow();
		
		//팔로우 하는사람 설정
		problem_Follow.setMember(followerMember);
		System.out.println("zzzzzzzzzzzzzzzzz2");
		//팔로우 당하는알고리즘 설정
		problem_Follow.setFollowingProblemSiteName(followingProblem);
		System.out.println("zzzzzzzzzzzzzzzzz");
		problem_FollowRepository.save(problem_Follow);
		
		ret.put("success","True");
		ret.put("msg","팔로우 완료");
		return ret;
	}
	
}
