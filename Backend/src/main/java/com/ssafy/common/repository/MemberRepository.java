package com.ssafy.common.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ssafy.common.domain.Member;

@Repository
public class MemberRepository {

	@PersistenceContext
	private EntityManager em;

	public Long save(Member member) {
		em.persist(member);
		return member.getMember_no();
	}

	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
			
	public List<Member> findAll() {
		return em.createQuery("Select m from Member m", Member.class).getResultList();
	}

	public Member findByName(String name) {
		Member member = null;
		try {
			member = em.createQuery("select m from Member m where m.member_name = :name", Member.class)
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return member;
	}

	public Member findByEmail(String email) {
		System.out.println("email"+email);
		Member member = null;
		try {
			member=em.createQuery("select m from Member m where m.member_email = :email", Member.class)
					.setParameter("email", email).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
		return member;
	}

}
