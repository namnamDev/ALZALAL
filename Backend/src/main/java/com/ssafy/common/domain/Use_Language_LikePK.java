package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Use_Language_LikePK implements Serializable{
	private Long memberNo;
	private String useLanguage;	
}
