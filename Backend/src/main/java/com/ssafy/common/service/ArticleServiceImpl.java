package com.ssafy.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.repository.ArticleRepositorySupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
  @Autowired
  private ArticleRepositorySupport ars;

  @Override
  public Map<String, Object> sltMultiArticle(String articleClass) {
    System.out.println("2*****getArticle****");

    Map<String, Object> getArticleList = new HashMap<String,Object>();
    if (articleClass.equals("main")){}
    else if(articleClass.equals("article")){
      System.out.println("3*****getArticle****");
      System.out.println(ars);
      try {
        // System.out.println("4*****getArticle****"+ars.findAll());
        List<Article> articleList = ars.findAll();
        System.out.println("6*****getArticle****");
        System.out.println("articleList : "+articleList);
        getArticleList.put("article", articleList);
      } catch (NullPointerException ifnulls) {
        getArticleList.put("msg", "게시글이 존재하지 않습니다.");
      }catch(Exception e){

      }
    }
    else if(articleClass.equals("discussion")){}
    return getArticleList;
  }
  // @Autowired
  // @Override
  
}
