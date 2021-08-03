package com.ssafy.common.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.common.jwt.JwtAccessDeniedHandler;
import com.ssafy.common.jwt.JwtAuthenticationEntryPoint;
import com.ssafy.common.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// CSRF 설정 Disable
		http.csrf().disable()
		        
				// exception handling 할 때 우리가 만든 클래스를 추가
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)

				.and().cors()
				
				// h2-console 을 위한 설정을 추가
				.and().headers().frameOptions().sameOrigin()

				// 시큐리티는 기본적으로 세션을 사용
				// 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				// 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
				.and().authorizeRequests()
				.antMatchers("/member/login").permitAll()
				.antMatchers("/member/signup").permitAll()
				.antMatchers("/member/refresh").permitAll()
				.antMatchers("/articlelist/*").permitAll()
				.antMatchers("/article/**").permitAll()
				.antMatchers(HttpMethod.GET,"/profile/**").permitAll()//프로필 이미지, 내용, 팔로잉,팔로워, 문제풀이요청리스트, 문제폴이 상세보기, 게시판 리스트 가져오기 API			
				.antMatchers(HttpMethod.GET,"/search/article/*").permitAll()//게시글 검색 관련 API	
				.antMatchers(HttpMethod.GET,"/helpme/**").permitAll()//프로필 해당유저 작성글 리스트 가져오기 API, helpme게시글 상세보기
				.antMatchers("/admin/**").permitAll()
				.anyRequest().authenticated()
				// 나머지 API 는 전부 인증 필요

				// JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
				.and().apply(new JwtSecurityConfig(tokenProvider));
	}
	
	  @Bean 
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();

	        configuration.addAllowedOriginPattern("*");
	        configuration.addAllowedHeader("*");
	        configuration.addAllowedMethod("*");
	        configuration.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
}