package com.ssafy.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.common.domain.helpme.Helpme_Like;
import com.ssafy.common.domain.helpme.Helpme_LikePK;


public interface Helpme_LikeRepository extends JpaRepository<Helpme_Like, Helpme_LikePK>,Helpme_LikeRepositoryCustom{

	
}
