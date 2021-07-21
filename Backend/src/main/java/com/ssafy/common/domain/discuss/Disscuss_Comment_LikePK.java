package com.ssafy.common.domain.discuss;

import java.io.Serializable;

import lombok.Data;
@Data
public class Disscuss_Comment_LikePK implements Serializable{
  private long comment;
  private long member; 
}
