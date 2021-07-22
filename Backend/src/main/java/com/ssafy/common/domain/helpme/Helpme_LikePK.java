package com.ssafy.common.domain.helpme;

import java.io.Serializable;

import lombok.Data;

@Data
public class Helpme_LikePK implements Serializable {
  private long helpmeNo;
  private long member; 
}
