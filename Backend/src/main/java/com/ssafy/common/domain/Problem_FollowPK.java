package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Problem_FollowPK implements Serializable{
  private long member;
  private Problem_Site followingProblemSiteName;
  // private long problem_no;
  // private String problem_site_name;
}
