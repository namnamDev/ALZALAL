package com.ssafy.common.domain.problem;
import java.io.Serializable;

import lombok.Data;


@Data
public class Problem_SitePK implements Serializable{
  private String problemSiteName;
  private long problemNo;
}
