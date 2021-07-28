package com.ssafy.common.domain.article;

import java.io.Serializable;

import lombok.Data;

@Data
public class Article_LikePK implements Serializable{
	private long articleNo;
	private long member; 
}
