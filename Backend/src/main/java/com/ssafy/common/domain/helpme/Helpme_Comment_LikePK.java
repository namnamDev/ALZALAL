package com.ssafy.common.domain.helpme;

import java.io.Serializable;

import lombok.Data;
@Data
public class Helpme_Comment_LikePK implements Serializable{
  private long comment;
  private long member;

}
