package com.ssafy.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.helpme.Helpme_Comment_Like;

public interface Helpme_Comment_LikeRepository extends JpaRepository<Helpme_Comment_Like,Long>,Helpme_Comment_LikeRepositoryCustom{


}
