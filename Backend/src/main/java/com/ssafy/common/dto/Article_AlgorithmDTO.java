package com.ssafy.common.dto;

import java.time.LocalDateTime;

import com.ssafy.common.domain.article.Article_Class;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Article_AlgorithmDTO {
	private Long articleNo;

	private String usedAlgorithm;


	public Article_AlgorithmDTO(
			Long articleNo,
			String usedAlgorithm
			) {
		super();
		this.articleNo = articleNo;
		this.usedAlgorithm = usedAlgorithm;

	}




	
	
	
	
}
