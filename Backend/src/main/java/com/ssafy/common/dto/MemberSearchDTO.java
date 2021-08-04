package com.ssafy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSearchDTO {
	//회원번호
	long no;
	//닉네임
	String name;
	//팔로잉수(사람 팔로잉)
	long followingCount;
	//팔로워수
	long followerCount;
	//팔로우 상태(로그인 한사람의 팔로우 여부)
	boolean followState;
	//작성 게시글 수
	long articleCount;
	public MemberSearchDTO(long no, String name, long followingCount,
			long followerCount, long followState, long articleCount) {
		this.no = no;
		this.name = name;
		this.followingCount = followingCount;
		this.followerCount = followerCount;
		this.followState = followState>0?true:false;
		this.articleCount = articleCount;
	}
}
