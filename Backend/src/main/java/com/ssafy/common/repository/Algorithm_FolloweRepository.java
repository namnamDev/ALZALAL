package com.ssafy.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.Algorithm_Follow;
import com.ssafy.common.domain.Algorithm_FollowPK;

public interface Algorithm_FolloweRepository extends JpaRepository<Algorithm_Follow, Algorithm_FollowPK>{

}
