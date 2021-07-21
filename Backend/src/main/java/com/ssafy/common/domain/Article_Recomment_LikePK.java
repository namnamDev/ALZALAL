package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.Data;
@Data
public class Article_Recomment_LikePK implements Serializable{
  private long recomment;
  private long member; 
  
}
