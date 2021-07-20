package com.ssafy.common.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Algorithm_Like_Id.class)
public class Algorithm_Like {

	@Id
	@ManyToOne
	@JoinColumn(name="algorithm_name")
	private Algorithm algorithm;
	
	@Id
	@ManyToOne
	@JoinColumn(name="member_no")
	private Member member;
	
}
