package com.ssafy.common.repository.discuss;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.discuss.Discuss_Comment;
import com.ssafy.common.domain.discuss.Discuss_Host;

public interface Discuss_HostRepository extends JpaRepository<Discuss_Host,Long>{


}
