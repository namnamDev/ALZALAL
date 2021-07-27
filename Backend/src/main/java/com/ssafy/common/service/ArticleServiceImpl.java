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
//@Transactional
public class ArticleServiceImpl implements ArticleService{
  @Autowired
  private ArticleRepositorySupport ars;

  @Override
  public Map<String, Object> sltMultiArticle(String articleClass) {
	//게시글 다건조회.. 추후 페이징처리 예정
    Map<String, Object> getArticleList = new HashMap<String,Object>();
    if (articleClass.equals("main")){
    	
    }else if(articleClass.equals("article")){
    	//게시글 전체 조회 결과
        List<Article> articleList = ars.findAll();
        getArticleList.put("article", articleList);
        if (articleList.size()==0) {//게시글이 존재하지 않을 시.
        	getArticleList.put("msg", "게시글이 존재하지 않습니다.");
        }
    }
    else if(articleClass.equals("discussion")){
    	
    }
    return getArticleList;
  }
  // @Autowired
  // @Override
  
}
