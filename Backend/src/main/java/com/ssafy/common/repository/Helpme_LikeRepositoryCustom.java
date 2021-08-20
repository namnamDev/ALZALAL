package com.ssafy.common.repository;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Like;
import com.ssafy.common.domain.helpme.Helpme;
import com.ssafy.common.domain.helpme.Helpme_Like;
import com.ssafy.common.domain.member.Member;

public interface Helpme_LikeRepositoryCustom {

	long deleteLike(Member member, Helpme article);

	Helpme_Like ifMemberExist(Long articlePK, Long memberNo);

}
