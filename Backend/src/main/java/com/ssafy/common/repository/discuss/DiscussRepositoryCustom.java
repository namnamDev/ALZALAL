package com.ssafy.common.repository.discuss;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.discuss.Discuss;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.DiscussDTO;

public interface DiscussRepositoryCustom {


//	List<Article_Comment> articleComments(Long article);

	long articleDelete(Long articlePk);

	long updateArticle(long articlePk, String content);
	
	DiscussDTO sltOne(Long pk, Long nowLoginMemberNo);

	Discuss sltOneArticle(Long pk);

	Optional<List<DiscussDTO>> sltMulti(Pageable page);

	Optional<List<DiscussDTO>> sltMultiByDisHost(Long HostPk, Pageable page);
}
