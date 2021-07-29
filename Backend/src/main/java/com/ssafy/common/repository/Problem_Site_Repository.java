package com.ssafy.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_SitePK;


public interface Problem_Site_Repository extends JpaRepository<Problem_Site,Problem_SitePK>,Problem_Site_RepositoryCustom{

}
