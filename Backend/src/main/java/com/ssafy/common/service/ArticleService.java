package com.ssafy.common.service;

import java.util.Map;


public interface ArticleService {
  public Map<String, Object> sltMultiArticle(String articleClass);
  public Map<String, Object> sltOneArticle(String articleClass, long pk);
Map<String, Object> deleteArticle(String articleClass, long pk, long memberPk);
}
