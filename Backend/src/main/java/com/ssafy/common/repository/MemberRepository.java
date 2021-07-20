package com.ssafy.common.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

	@PersistenceContext
	private EntityManager em;

	
	
	
}
