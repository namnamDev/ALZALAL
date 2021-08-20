package com.ssafy.common.repository.Algorithm;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.common.domain.Algorithm;
import static com.ssafy.common.domain.QAlgorithm.*;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AlgorithmRepositoryImpl implements AlgorithmRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public Algorithm sltOne(String algoName){
		return queryFactory.selectFrom(algorithm).
				where(algorithm.algorithmName.eq(algoName)).
				fetchFirst();
	}
}
