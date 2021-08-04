package com.ssafy.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.helpme.Helpme_Comment;

public interface Helpme_CommentRepository extends JpaRepository<Helpme_Comment,Long>,Helpme_CommentRepositoryCustom{


}
