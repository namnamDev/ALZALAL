package com.ssafy.common.domain.helpme;

import java.io.Serializable;

import lombok.Data;
@Data
public class Helpme_Recomment_LikePK implements Serializable{
  private long recomment;
  private long member; 
  
}
