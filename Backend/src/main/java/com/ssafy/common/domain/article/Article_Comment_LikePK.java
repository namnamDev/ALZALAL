package com.ssafy.common.domain.article;

import java.io.Serializable;

import lombok.Data;
@Data
public class Article_Comment_LikePK implements Serializable{
  private long articleComment;
  private long member; 
  
}
