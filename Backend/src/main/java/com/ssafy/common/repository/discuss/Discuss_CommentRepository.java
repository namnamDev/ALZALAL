package com.ssafy.common.repository.discuss;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.discuss.Discuss_Comment;

public interface Discuss_CommentRepository extends JpaRepository<Discuss_Comment,Long>,Discuss_CommentRepositoryCustom{


}
