package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Algorithm_FollowPK implements Serializable{
  private String followingArgorithmNo;
  private Long memberNo;
}
