package com.ssafy.common.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.Tuple;

public interface Problem_FollowRepositoryCustom {

	 List<Tuple> getProblemFollowings(Long memberNo,Pageable page);
}
