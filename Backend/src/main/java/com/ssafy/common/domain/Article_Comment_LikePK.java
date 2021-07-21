package com.ssafy.common.domain;

import java.io.Serializable;

import lombok.Data;
@Data
public class Article_Comment_LikePK implements Serializable{
  private long comment;
  private long member; 
  
}
