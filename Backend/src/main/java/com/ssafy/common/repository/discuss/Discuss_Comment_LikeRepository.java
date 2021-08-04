package com.ssafy.common.repository.discuss;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.discuss.Discuss_Comment_Like;

public interface Discuss_Comment_LikeRepository extends JpaRepository<Discuss_Comment_Like,Long>,Discuss_Comment_LikeRepositoryCustom{


}
