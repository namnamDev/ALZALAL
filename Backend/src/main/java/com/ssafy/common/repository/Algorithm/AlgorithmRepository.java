package com.ssafy.common.repository.Algorithm;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.Algorithm;

public interface AlgorithmRepository extends JpaRepository<Algorithm, String>,AlgorithmRepositoryCustom{

}
