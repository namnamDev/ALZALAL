package com.ssafy.common.domain.discuss;
import java.io.Serializable;
import lombok.Data;
@Data
public class Disscuss_Recomment_LikePK implements Serializable{
  private long recomment;
  private long member; 
}
