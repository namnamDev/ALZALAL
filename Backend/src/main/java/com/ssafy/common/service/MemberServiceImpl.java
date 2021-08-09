package com.ssafy.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.domain.Use_Language;
import com.ssafy.common.domain.Use_Language_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site_Like;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.dto.NotificationDTO;
import com.ssafy.common.dto.TokenDto;
import com.ssafy.common.jwt.TokenProvider;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.redis.util.RedisUtil;
import com.ssafy.common.repository.NotificationRepository;
import com.ssafy.common.repository.Use_LanguageRepository;
import com.ssafy.common.repository.Use_Language_LikeRepository;
import com.ssafy.common.repository.member.MemberRepository;
import com.ssafy.common.repository.problem.Problem_Site_LikeRepository;
import com.ssafy.common.repository.problem.Problem_Site_ListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final Problem_Site_ListRepository problem_Site_ListRepository;
	private final Use_LanguageRepository use_LanguageRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final Problem_Site_LikeRepository problem_Site_LikeRepository;
	private final Use_Language_LikeRepository use_Language_LikeRepository;
	private final NotificationRepository notiRepo;
	private final RedisUtil redisUtil;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 3;            // 3시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    
	@Override
	public long signup(Member member, List<String> problem_site_list, List<String> use_language_like) {
		// name, email 중복확인

		if (memberRepository.findByName(member.getName()).isPresent())
			throw new IllegalStateException("이미 존재하는 이름입니다");

		if (memberRepository.findByEmail(member.getEmail()).isPresent())
			throw new IllegalStateException("이미 존재하는 이메일입니다");

		// 선호하는 문제 사이트 추가
		List<Problem_Site_Like> pslikeList = new ArrayList<>();
		if (problem_site_list != null) {
			for (String s : problem_site_list) {
				Problem_Site_List pslist = problem_Site_ListRepository.findOne(s);
				// 선택된 문제 사이트가 존재하지 않는 경우
				if (pslist == null) {
					throw new IllegalStateException("존재하지 않는 문제 사이트 입니다");
				}
				Problem_Site_Like tmp = new Problem_Site_Like();
				tmp.setProblemSiteName(pslist);
				tmp.setMemberNo(member);

				pslikeList.add(tmp);
			}
		}

		// 선호하는 언어 추가
		List<Use_Language_Like> ullikeList = new ArrayList<>();
		if (use_language_like != null) {
			for (String s : use_language_like) {
				Use_Language ul = use_LanguageRepository.findOne(s);
				// 선택된 언어가 존재하지 않는 경우
				if (ul == null) {
					throw new IllegalStateException("존재하지 않는 언어 입니다");
				}
				Use_Language_Like tmp = new Use_Language_Like();
				tmp.setUseLanguage(ul);
				tmp.setMemberNo(member);

				ullikeList.add(tmp);
			}
		}

		// 선호하는 사이트, 언어 추가
		member.createMember(pslikeList, ullikeList);

		// 비밀번호 암호화
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberRepository.save(member);

		return member.getNo();
	}

	@Override
	public TokenDto login(Member member) {

		// 유저 정보 검증

		// -------- 토큰 생성
		// 유저 id, password를 통해 UsernamePasswordAuthenticationToken객체 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				member.getEmail(), member.getPassword());

		// authenticationToken를 이용해서 authenticate메소드가 실행이 될때
		// 아까만든 CustomUserDetailsService의 loadUserByUsername 메소드가 실행됨
		// 그 결과값을 가지고 Authentication객체가 생성됨
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);//Authentication객체를 SecurityContext에 저장

		// memberName 가져와서 토큰만들때 집어넣음
		String memberName = memberRepository.findByEmail(member.getEmail()).get().getName();

		// Authentication를 이용해 jwt토큰 생성
		TokenDto jwt = tokenProvider.generateTokenDto(authentication, memberName);
		// -------- 토큰 생성완료

		// RefreshToken 저장(mariaDB에 저장하던걸 Redis에 저장하는걸로 변경)
//		RefreshToken refreshToken = RefreshToken.builder().key(authentication.getName()).value(jwt.getRefreshToken())
//				.build();
//		refreshTokenRepository.save(refreshToken);
		
		Long refreshTokenExpiresIn=jwt.getAccessTokenExpiresIn() - ACCESS_TOKEN_EXPIRE_TIME + REFRESH_TOKEN_EXPIRE_TIME;
		redisUtil.setDataExpire(authentication.getName(),jwt.getRefreshToken(),refreshTokenExpiresIn);
		
		return jwt;
	}

	@Override
	public TokenDto refresh(TokenDto tokenDto) {
		// 1. Refresh Token 검증
		if (!tokenProvider.validateToken(tokenDto.getRefreshToken())) {
			throw new IllegalStateException("Refresh Token 이 유효하지 않습니다");
		}

		// 2. Access Token 에서 Member ID(memberNo) 가져오기
		Authentication authentication = tokenProvider.getAuthentication(tokenDto.getAccessToken());

		// 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴(mariaDB에서 가져오던걸 Redis에서 가져오게 변경)
//		RefreshToken refreshToken = refreshTokenRepository.findByMemberNo(authentication.getName())
//				.orElseThrow(() -> new IllegalStateException("로그아웃 된 사용자입니다"));
		String refreshToken=redisUtil.getData(authentication.getName());

		// 4. Refresh Token 일치하는지 검사
		if (!refreshToken.equals(tokenDto.getRefreshToken())) {
			throw new IllegalStateException("토큰의 유저 정보가 일치하지 않습니다");
		}

		// memberName 가져와서 토큰만들때 집어넣음
		String memberName = "";
		Long memberId = Long.parseLong(authentication.getName());
		if (memberId != null) {
			memberName = memberRepository.findById(memberId).get().getName();
		}

		// 5. 새로운 토큰 생성
		TokenDto retTokenDto = tokenProvider.generateTokenDto(authentication, memberName);

		// 6. 저장소 정보 업데이트(mariaDB에 저장하던걸 Redis에 저장하는걸로 변경)
//		RefreshToken newRefreshToken = refreshToken.updateValue(retTokenDto.getRefreshToken());
//		refreshTokenRepository.save(newRefreshToken);
		Long refreshTokenExpiresIn=retTokenDto.getAccessTokenExpiresIn() - ACCESS_TOKEN_EXPIRE_TIME + REFRESH_TOKEN_EXPIRE_TIME;
		redisUtil.setDataExpire(authentication.getName(), retTokenDto.getRefreshToken(), refreshTokenExpiresIn);

		// 토큰 발급
		return retTokenDto;
	}

	// 현재 유저정보의 비밀번호 동일한지 확인
	@Override
	@Transactional(readOnly = true)
	public void checkPassword(String password) {
		Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new IllegalStateException("잘못된 비밀번호 입니다");
		}

		return;
	}


	// 현재 SecurityContext 에 있는 유저 정보 가져오기
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getMyInfo() {
		Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));

		Map<String, Object> mem = new HashMap<>();
		mem.put("no", member.getNo());
		mem.put("email", member.getEmail());
		mem.put("name", member.getName());

		// 선호하는 사이트 리스트 가져옴
		List<String> problemSiteList = new ArrayList<String>();
		for (Problem_Site_Like tmp : member.getProblemSiteList()) {
			problemSiteList.add(tmp.getProblemSiteName().getProblemSiteName());
		}
		mem.put("problemSiteList", problemSiteList);

		// 선호하는 언어 리스트 가져옴
		List<String> useLanguageLike = new ArrayList<String>();
		for (Use_Language_Like tmp : member.getUseLanguageLike()) {
			useLanguageLike.add(tmp.getUseLanguage().getUseLanguage());
		}
		mem.put("useLanguageLike", useLanguageLike);
		
		return mem;
	}

	// 회원정보 수정
	@Override
	public TokenDto setMemberInfo(Member inpmem, List<String> problem_site_list, List<String> use_language_like) {
		Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));

		// 패스워드 입력했으면 변경
		if (inpmem.getPassword().length() != 0)
			member.setPassword(passwordEncoder.encode(inpmem.getPassword()));

		// Name 중복체크후 변경
		if (!inpmem.getName().equals(member.getName())) {
			if (memberRepository.findByName(inpmem.getName()).isPresent()) {
				throw new IllegalStateException("이미 존재하는 이름입니다");
			}
			member.setName(inpmem.getName());
		}
		
		
		//기존 선호하는 문제사이트 삭제
		for(Problem_Site_Like tmp:member.getProblemSiteList()) {
			problem_Site_LikeRepository.delete(tmp);
		}
		member.getProblemSiteList().clear();
		memberRepository.flush();
		
		//선호하는 문제 사이트 추가
		List<Problem_Site_Like> pslikeList = new ArrayList<>();
		if (problem_site_list != null) {
			for (String s : problem_site_list) {
				Problem_Site_List pslist = problem_Site_ListRepository.findOne(s);
				// 선택된 문제 사이트가 존재하지 않는 경우
				if (pslist == null) {
					throw new IllegalStateException("존재하지 않는 문제 사이트 입니다");
				}
				Problem_Site_Like tmp = new Problem_Site_Like();
				tmp.setProblemSiteName(pslist);
				tmp.setMemberNo(member);

				pslikeList.add(tmp);
			}
		}
		
		//기존 선호하는 언어 삭제
		for(Use_Language_Like tmp:member.getUseLanguageLike()) {
			use_Language_LikeRepository.delete(tmp);
		}
		member.getUseLanguageLike().clear();
		memberRepository.flush();
		
		// 선호하는 언어 추가
		List<Use_Language_Like> ullikeList = new ArrayList<>();
		if (use_language_like != null) {
			for (String s : use_language_like) {
				Use_Language ul = use_LanguageRepository.findOne(s);
				// 선택된 언어가 존재하지 않는 경우
				if (ul == null) {
					throw new IllegalStateException("존재하지 않는 언어 입니다");
				}
				Use_Language_Like tmp = new Use_Language_Like();
				tmp.setUseLanguage(ul);
				tmp.setMemberNo(member);

				ullikeList.add(tmp);
			}
		}

		// 선호하는 사이트, 언어 추가
		member.createMember(pslikeList, ullikeList);
		
	
		// -----------------------------------------회원정보 변경 완료

		// 회원정보중 비밀번호가 변경 됬을 경우에만 새로운 토큰 발급
		// -----------------------------------------새로운 토큰 발급 시작
		TokenDto jwt=null;
		if (inpmem.getPassword().length() != 0) {
			// 회원정보 변경후 토큰 갱신
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					member.getEmail(), inpmem.getPassword());

			// Authentication객체 생성
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

			// memberName 가져와서 토큰만들때 집어넣음
			String memberName = member.getName();

			// Authentication를 이용해 jwt토큰 생성
			jwt = tokenProvider.generateTokenDto(authentication, memberName);
			// -------- 토큰 생성완료

			// RefreshToken 저장 (mariaDB에 저장하던걸 Redis에 저장하는걸로 변경) 
//			RefreshToken refreshToken = RefreshToken.builder().key(authentication.getName())
//					.value(jwt.getRefreshToken()).build();
//			refreshTokenRepository.save(refreshToken);			
			Long refreshTokenExpiresIn=jwt.getAccessTokenExpiresIn() - ACCESS_TOKEN_EXPIRE_TIME + REFRESH_TOKEN_EXPIRE_TIME;
			redisUtil.setDataExpire(authentication.getName(), jwt.getRefreshToken(), refreshTokenExpiresIn);

		}

		return jwt;
	}
	
	//회원탈퇴
	@Override
	public void deleteMember() {
		Long memberNo=SecurityUtil.getCurrentMemberId();
		Member member = memberRepository.findByNo(memberNo)
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
//		memberRepository.deleteById(memberNo);
		
		//회원은 삭제되는게 아니라 회원정보만 지움
		//근데 지금 not null세팅되어있음
		member.setEmail("탈퇴한 회원"+memberNo);
		member.setPassword("탈퇴한 회원"+memberNo);// 비번은 원래 암호화 되어 저장되어야하는데 디비에 평문이 들어가 있어서 로그인 못함
		member.setName("탈퇴한 회원"+memberNo);
		member.setProblemSiteList(null);
		member.setUseLanguageLike(null);
		member.setIntroduce(null);
		member.setAuthority(null);
		
		return;
	}
	
	//memberNo를 통한 프로필이미지 이름 출력
	@Override
	public String getProfileImgUri(Long memberNo) {
		System.out.println("qwer   "+memberNo);
		Member member=memberRepository.findById(memberNo).get();
		return member.getProfileImg();
	}
	
	@Override
	public Map<String, Object> getMemberNoti(int page){
		  Map<String,Object>res = new HashMap<String,Object>();
		  String msg = "";
		  Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				  .orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		  List<NotificationDTO> notiList = notiRepo.notiList(member.getNo(),PageRequest.of(page, Common.PAGE)).orElse(null);
		  res.put("notificationList", notiList);
		  if (notiList==null) {
			  msg = "알람이 없습니다.";
			  res.put("msg", msg);
		  }
		return res;
		  
		  
	}
}
