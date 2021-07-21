package com.ssafy.common.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ssafy.common.domain.Use_Language;

@Repository
public class Use_LanguageRepository {
	@PersistenceContext
	private EntityManager em;
	

	//선호 언어 PK를 통한 select
	public Use_Language findOne(String id) {
		return em.find(Use_Language.class, id);
	}
	
	//전체 선호 언어 select
	public List<Use_Language> findAll() {
		return em.createQuery("Select u from Use_Language u", Use_Language.class).getResultList();
	}
}
