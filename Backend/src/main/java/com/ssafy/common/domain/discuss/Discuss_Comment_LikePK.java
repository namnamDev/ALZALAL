package com.ssafy.common.domain.discuss;

import java.io.Serializable;

import lombok.Data;
@Data
public class Discuss_Comment_LikePK implements Serializable{
  private long disscussCommentNo;
  private long member; 
}
