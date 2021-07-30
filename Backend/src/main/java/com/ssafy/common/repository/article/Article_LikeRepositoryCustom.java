package com.ssafy.common.repository.article;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Like;
import com.ssafy.common.domain.member.Member;

public interface Article_LikeRepositoryCustom {

	long deleteLike(Member member, Article article);

	Article_Like ifMemberExist(Member member);

}
