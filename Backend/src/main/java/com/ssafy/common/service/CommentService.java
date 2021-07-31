package com.ssafy.common.service;

import java.util.Map;

public interface CommentService {

	Map<String, Object> insertArticleComment(String articleClass, long articlePk, Map<String, Object> req);

	Map<String, Object> deleteArticleComment(String ArticleClass,Long commentPk);

	Map<String, Object> updateArtiComment(String articleClass, Long commentPK, Map<String, Object> req);

}
