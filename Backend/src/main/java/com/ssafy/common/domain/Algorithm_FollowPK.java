package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Algorithm_FollowPK implements Serializable {
	private Long memberNo;
	private String followingArgorithmNo;

}
