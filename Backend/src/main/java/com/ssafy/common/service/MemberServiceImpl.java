package com.ssafy.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.domain.Use_Language;
import com.ssafy.common.domain.Use_Language_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.member.RefreshToken;
import com.ssafy.common.domain.problem.Problem_Site_Like;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.dto.TokenDto;
import com.ssafy.common.jwt.TokenProvider;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.MemberRepository;
import com.ssafy.common.repository.Problem_Site_ListRepository;
import com.ssafy.common.repository.RefreshTokenRepository;
import com.ssafy.common.repository.Use_LanguageRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository mr;
	@Autowired
	private Problem_Site_ListRepository pr;
	@Autowired
	private Use_LanguageRepository ur;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private TokenProvider tokenProvider;
	@Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
	@Autowired
	private RefreshTokenRepository rr;
	
	@Override
	public long signup(Member member,List<String> problem_site_list, List<String> use_language_like) {
		//name, email 중복확인
	
		if(mr.findByName(member.getName()).isPresent())
			throw new IllegalStateException("이미 존재하는 이름입니다");
		
		if(mr.findByEmail(member.getEmail()).isPresent())
			throw new IllegalStateException("이미 존재하는 이메일입니다");
		
		
		//선호하는 문제 사이트 추가
		List<Problem_Site_Like> pslikeList =new ArrayList<>();
		if(problem_site_list!=null) {
			for(String s: problem_site_list) {
				Problem_Site_List pslist = pr.findOne(s);
				//선택된 문제 사이트가 존재하지 않는 경우
				if(pslist==null) {
					throw new IllegalStateException("존재하지 않는 문제 사이트 입니다");
				}
				Problem_Site_Like tmp= new Problem_Site_Like();
				tmp.setProblemSiteName(pslist);
				tmp.setMemberNo(member);
				
				pslikeList.add(tmp);
			}
		}
		
		//선호하는 언어 추가
		List<Use_Language_Like> ullikeList =new ArrayList<>();
		if(use_language_like!=null) {
			for(String s: use_language_like) {
				Use_Language ul=ur.findOne(s);
				//선택된 언어가 존재하지 않는 경우
				if(ul==null) {
					throw new IllegalStateException("존재하지 않는 언어 입니다");
				}
				Use_Language_Like tmp =new Use_Language_Like();
				tmp.setUseLanguage(ul);
				tmp.setMemberNo(member);
				
				ullikeList.add(tmp);
			}
		}
		
		//선호하는 사이트, 언어 추가
		member.createMember(pslikeList, ullikeList);
		
		//비밀번호 암호화
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		
		
		mr.save(member);

		return member.getNo();
	}
	
	@Override
	@Transactional
	public TokenDto login(Member member) {
		
		//유저 정보 검증

		//-------- 토큰 생성
    	//유저 id, password를 통해 UsernamePasswordAuthenticationToken객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());

        //authenticationToken를 이용해서 authenticate메소드가 실행이 될때 
        // 아까만든 CustomUserDetailsService의 loadUserByUsername 메소드가 실행됨
        // 그 결과값을 가지고 Authentication객체가 생성됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);//Authentication객체를 SecurityContext에 저장

        // memberName 가져와서 토큰만들때 집어넣음
        String memberName=mr.findByEmail(member.getEmail()).get().getName();
        
        //Authentication를 이용해 jwt토큰 생성
        TokenDto jwt = tokenProvider.generateTokenDto(authentication, memberName);
        //-------- 토큰 생성완료
        
        //RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(jwt.getRefreshToken())
                .build();

        rr.save(refreshToken);

		return jwt;
	}

	@Override
	@Transactional
    public TokenDto refresh(TokenDto tokenDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenDto.getRefreshToken())) {
            throw new IllegalStateException("Refresh Token 이 유효하지 않습니다");
        }

        // 2. Access Token 에서 Member ID(memberNo) 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = rr.findByMemberEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("로그아웃 된 사용자입니다"));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getRefreshToken().equals(tokenDto.getRefreshToken())) {
            throw new IllegalStateException("토큰의 유저 정보가 일치하지 않습니다");
        }
        
        // memberName 가져와서 토큰만들때 집어넣음
        String memberName="";    
    	Long memberId=Long.parseLong(authentication.getName());
    	if(memberId!=null) {
    		memberName=mr.findById(memberId).get().getName();
    	}
        
        // 5. 새로운 토큰 생성
        TokenDto retTokenDto = tokenProvider.generateTokenDto(authentication,memberName);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(retTokenDto.getRefreshToken());
        rr.save(newRefreshToken);

        // 토큰 발급
        return retTokenDto;
    }
	
	@Override
    @Transactional(readOnly = true)
    public Member getMemberInfo(String email) {
    	Member member=mr.findByEmail(email)
    			.orElseThrow(()->new IllegalStateException("유저정보가 없습니다"));
    	
        return member;               
    }

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
	@Override
    @Transactional(readOnly = true)
    public Member getMyInfo() {
    	Member member=mr.findByNo(SecurityUtil.getCurrentMemberId())
    			.orElseThrow(()->new IllegalStateException("로그인 유저정보가 없습니다"));

        return member; 
    }
    
    // 현재 유저정보의 비밀번호 동일한지 확인
	@Override
    @Transactional(readOnly = true)
    public void checkPassword(String password) {
    	Member member=mr.findByNo(SecurityUtil.getCurrentMemberId())
    			.orElseThrow(()->new IllegalStateException("로그인 유저정보가 없습니다"));
    	
    	if(!passwordEncoder.matches(password, member.getPassword())) {
    		throw new IllegalStateException("잘못된 비밀번호 입니다");
    	}

        return; 
    }
}
