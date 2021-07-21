package com.ssafy.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Use_Language {

	@Id
	@Column(length = 30)
	String use_language ;
	
	//Use_Language_Like 도메인과 관계
	@OneToMany(mappedBy = "use_language")
	List<Use_Language_Like> member =new ArrayList<>();
	
}
