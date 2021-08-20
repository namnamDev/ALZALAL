package com.ssafy.common.domain.article;

import java.io.Serializable;

import lombok.Data;
@Data
public class Article_Recomment_LikePK implements Serializable{
  private long articleRecommentNo;
  private long member; 
  
}
