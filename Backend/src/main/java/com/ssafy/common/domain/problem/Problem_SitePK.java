package com.ssafy.common.domain.problem;
import java.io.Serializable;

import lombok.Data;


@Data
public class Problem_SitePK implements Serializable{
  // private static final long serialVersionUID = -3033073092220146757L;
  private String problemSiteName;
  private int problemNo;
}
