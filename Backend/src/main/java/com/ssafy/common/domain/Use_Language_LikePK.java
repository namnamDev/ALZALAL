package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Use_Language_LikePK implements Serializable{
	private long member_no;
	private String use_language;	
}
