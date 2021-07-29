package com.ssafy.common.domain.problem;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem_FollowPK implements Serializable{
  private long member;
  private Problem_SitePK followingProblemSiteName;
  // private long problem_no;
  // private String problem_site_name;
}
