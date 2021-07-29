package com.ssafy.common.repository;

import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;

public interface Problem_Site_RepositoryCustom {

	Problem_Site sltOneProblem(Problem_Site_List problemSite, long No);

}
