package com.ssafy.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {
	
	@Id
	@GeneratedValue
	private long member_no;//회원번호
	//회원 이메일
	@Column(length = 30)
	private String member_email;
	//회원 비밀번호
	@Column(length = 20)
	private String member_password;
	//회원 이름
	@Column(length = 20)
	private String member_name;

	//소셜인지 자체인지 로그인 타입
	@Column(length = 3)
	@Enumerated(EnumType.STRING)
	private MemberWay member_way;

	@Override
	public String toString() {
		return "Member [member_no=" + member_no + ", member_email=" + member_email + ", member_password="
				+ member_password + ", member_name=" + member_name + ", member_way=" + member_way + "]";
	}
	
	
}
