package com.ssafy.common.domain.article;
import java.io.Serializable;

import com.ssafy.common.domain.Algorithm;

import lombok.Data;

@Data
public class Article_AlgorithmPK implements Serializable{
  // private static final long serialVersionUID = -3033073092220146757L;
  private String algorithmName;
  private Long articleNo;
}
