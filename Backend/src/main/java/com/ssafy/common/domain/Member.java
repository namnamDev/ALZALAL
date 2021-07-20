package com.ssafy.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DynamicInsert
public class Member {
	//회원넘버
	@Id	@GeneratedValue
	private long member_no;
	
	//회원 이메일
	@Column(length = 30,unique = true,nullable = false)
	private String member_email;
	
	//회원 비밀번호
	@Column(length = 20,nullable = false)
	private String member_password;
	
	//회원 이름
	@Column(length = 20, unique = true, nullable = false)
	private String member_name;

	//회원 방식
	@Column(length = 4)
	@ColumnDefault(value = "'M00'")
	@Enumerated(EnumType.STRING)
	private MemberWay member_way;
	
	//선호하는 문제 사이트
	@OneToMany(mappedBy = "member")
	private List<Problem_Site_Like> problem_site_list=new ArrayList<>(); 
}
