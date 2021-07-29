package com.ssafy.common.domain.problem;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem_SitePK implements Serializable{
  private String problemSiteName;
  private long problemNo;
}
