package com.ssafy.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Algorithm {

	@Id
	private String algorithm_name;
	
	@OneToMany(mappedBy = "algorithm")
	private List<Algorithm_Like> member = new ArrayList<>();
}
