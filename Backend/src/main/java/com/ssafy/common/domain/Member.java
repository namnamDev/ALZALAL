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
	private Long member_no;//회원 넘버
	//회원아이디
	@Column(length = 20)
	private String member_id;	
	//회원 비밀번호
	@Column(length = 20)
	private String member_password;
	@Column(length = 20)
	private String member_name;
	@Column(length = 30)
	private String member_email;
	
	@Column(length = 3)
	@Enumerated(EnumType.STRING)
	private MemberWay member_way;
}
