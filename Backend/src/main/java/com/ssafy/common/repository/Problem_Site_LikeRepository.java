package com.ssafy.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.problem.Problem_Site_Like;
import com.ssafy.common.domain.problem.Problem_Site_LikePK;

public interface Problem_Site_LikeRepository extends JpaRepository<Problem_Site_Like, Problem_Site_LikePK> {
}
