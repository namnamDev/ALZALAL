package com.ssafy.common.repository.discuss;

import com.ssafy.common.domain.discuss.Discuss;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussRepository extends JpaRepository<Discuss, Long>,DiscussRepositoryCustom{
  
}
