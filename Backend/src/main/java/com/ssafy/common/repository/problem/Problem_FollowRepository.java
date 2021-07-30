package com.ssafy.common.repository.problem;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.problem.Problem_Follow;
import com.ssafy.common.domain.problem.Problem_FollowPK;

public interface Problem_FollowRepository extends JpaRepository<Problem_Follow, Problem_FollowPK> {

}
