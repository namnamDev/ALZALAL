package com.ssafy.common.repository.problem;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ssafy.common.domain.problem.Problem_Site_List;

@Repository
public class Problem_Site_ListRepository {
	@PersistenceContext
	private EntityManager em;
	

	//문제사이트 PK를 통한 select
	public Problem_Site_List findOne(String id) {
		return em.find(Problem_Site_List.class, id);
	}
	
	//전체 문제사이트 select
	public List<Problem_Site_List> findAll() {
		return em.createQuery("Select p from Problem_Site_List p", Problem_Site_List.class).getResultList();
	}
}
