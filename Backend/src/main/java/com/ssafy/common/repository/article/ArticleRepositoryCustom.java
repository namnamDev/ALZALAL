package com.ssafy.common.repository.article;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Class;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.ArticleDTO;

public interface ArticleRepositoryCustom {


//	List<Article_Comment> articleComments(Long article);

	Article insertArticle(Member member, Article myinsert);

	long articleDelete(Long articlePk);


	//작성자를 통한 게시글 갯수 조회
	long countByMember(Long memberNo);

	long likeArticle(Article article);
	
	// memberNo를 통해 해당 member를 작성자로 가지는 모든 게시글, 좋아요 수, memberNo유저의 좋아요 여부, 댓글 갯수
	Optional<List<ArticleDTO>> getListByMemberNO(Long memberNo,Long nowLoginMemberNo,Pageable page);

	ArticleDTO sltOne(Long pk, Long nowLoginMemberNo);

	Article sltOneArticle(Long pk);

	Optional<List<ArticleDTO>> sltMulti(Long nowLoginMemberNo, Pageable page);

	Optional<List<ArticleDTO>> sltMultiQnA(Long nowLoginMemberNo, Pageable page, Article_Class articleClass);

	long updateArticle(long articlePk, Article updateArticle);
}
