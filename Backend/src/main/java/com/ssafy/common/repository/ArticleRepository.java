package com.ssafy.common.repository;

import java.util.ArrayList;
import java.util.List;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.ssafy.common.domain.article.Article;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository {
  @PersistenceContext
  private EntityManager em;

  // public List<Article> SltMul(String articleClass,int page){
  //   Article findAll()
  //   // List<Article> articleList = new ArrayList<Article>();
  //   // CriteriaBuilder cb = em.getCriteriaBuilder();
  //   // CriteriaQuery<Article> query = cb.createQuery(Article.class);
  //   // Root<Article> a =query.from(Article.class);
  //   // CriteriaQuery<Article> articles = query.select(a); 
  // }
  
}
