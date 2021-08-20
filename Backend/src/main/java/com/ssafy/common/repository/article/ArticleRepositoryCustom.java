package com.ssafy.common.repository.article;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Class;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;
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

	//문제 검색 
	Optional<List<ArticleDTO> > getProblemSearch(Long nowLoginMemberNo,Problem_Site problem, String language,
			List<String> and, List<String> not, Pageable page,String sort);

	//문제검색 결과 count
	Long getProblemSearchCount(Problem_Site problem, String language,List<String> and, List<String> not);
	
	//알고리즘 검색 
	Optional<List<ArticleDTO> > getAlgorithmSearch(Long nowLoginMemberNo, String language,
			List<String> and, List<String> not, Pageable page,String sort);
	
	//알고리즘 검색결과 Count
	Long getAlgorithmSearchCount(String language, List<String> and, List<String> not);
	
	//해당 알고리즘이 속한 문제 갯수 리턴
	long countbyAlgorithm(String algorithmName);	
	
	//해당 문제가 속한 문제 갯수 리턴
	long countbyProblem(Problem_Site problem);
	
	long updateArticle(long articlePk, Article updateArticle);

}
