package com.ssafy.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
public class ProblemSiteDTO {
   private String problemSiteName;
   private Long problemNo;
   private String problemSiteLink;
}