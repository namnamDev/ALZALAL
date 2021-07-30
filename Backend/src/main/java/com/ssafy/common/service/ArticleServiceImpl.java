package com.ssafy.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;


import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.domain.Use_Language;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Algorithm;
import com.ssafy.common.domain.article.Article_Class;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.Article_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.Use_LanguageRepository;
import com.ssafy.common.repository.Algorithm.AlgorithmRepository;
import com.ssafy.common.repository.article.ArticleRepository;
import com.ssafy.common.repository.article.Article_AlgorithmRepository;
import com.ssafy.common.repository.article.Article_LikeRepository;
import com.ssafy.common.repository.member.MemberRepository;
import com.ssafy.common.repository.problem.Problem_Site_ListRepository;
import com.ssafy.common.repository.problem.Problem_Site_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
  @Autowired
  private  ArticleRepository ArticleRepo;
//  @Autowired
//  private ArticleRepository ArticleRepo;
  
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private Problem_Site_Repository problemSiteRepo;
  @Autowired
  private Use_LanguageRepository useLanguageRepo;
  @Autowired
  private AlgorithmRepository AlgoRepo;
  @Autowired
  private Article_AlgorithmRepository ArtiAlgoRepo;
  @Autowired
  private Problem_Site_ListRepository problemSiteListRepo;
  @Autowired
  private Article_LikeRepository ArticleLikeRepo;
  @Override
  public Map<String, Object> sltMultiArticle(String articleClass) {
	//게시글 다건조회.. 추후 페이징처리 예정
    Map<String, Object> res = new HashMap<String,Object>();
    if (articleClass.equals("main")){
    	
    }else if(articleClass.equals("article")){
    	//게시글 전체 조회 결과
        List<Article> articleList = ArticleRepo.findAll();
        res.put("article", articleList);
        if (articleList.size()==0) {//게시글이 존재하지 않을 시.
        	res.put("msg", "게시글이 존재하지 않습니다.");
        }
    }
    else if(articleClass.equals("discussion")){
    	
    }
    return res;
    
  }
  @Override
  public Map<String, Object>sltOneArticle(String articleClass,long pk){
	  //게시글 단건조회 및 댓글 조회
	  Map<String,Object>res = new HashMap<String,Object>();
	  	if(articleClass.equals("article")){
	    	Article article = ArticleRepo.sltOne(pk);
//	    	ArticleRepo.
	    	res.put("articleDetail",article);
	    	long countLike = ArticleRepo.likeArticle(article);
	    	res.put("like", countLike);
	    	List<Article_Comment>comments = ArticleRepo.articleComments(article);
	    	String msg = "";
	    	if (comments.size() == 0) {
	    		msg = "등록된 댓글이 없습니다.";
	    	}
	    	res.put("msg",msg);
	    	res.put("articleComments", comments);
	    }
	    else if(articleClass.equals("discussion")){
	    	
	    }
	  return res;
  }
  @Override
  public Map<String, Object>deleteArticle(String articleClass,long articlePk){
	  Map<String,Object>res = new HashMap<String,Object>();
	  String msg = "";
	  Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
	  	if(articleClass.equals("article")){
	  		
	  		//삭제 전 단건조회
	    	Article article = ArticleRepo.sltOne(articlePk);
	    	if (article == null) {
	    		msg = "존재하지 않는 게시글입니다.";
	    	}else {
	    		//삭제 전 유저 일치 조회
	    		if(member != article.getMember()){
	    		msg ="자신의 글만 삭제할 수 있습니다.";
	    		}else{
		    		long deletedArticle = ArticleRepo.articleDelete(articlePk);
		    		res.put("deletedArtcle", deletedArticle);
		    		msg = "성공적으로 삭제되었습니다.";
	    		}
	    	}

	    	res.put("msg",msg);

	    }
	    else if(articleClass.equals("discussion")){
	    	
	    }
	  return res;
  }
@Override
public Map<String, Object> updateArticle(String articleClass, long articlePk, Map<String, Object> req) {
	Map<String,Object>res = new HashMap<String,Object>();
	String msg = "";
	Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
	if(articleClass.equals("article")){
		//수정 전 단건 조회
		Article article = ArticleRepo.sltOne(articlePk);
		if (article == null) {
    		msg = "존재하지 않는 게시글입니다.";
    	}else {
    		if(member != article.getMember()){
	    		msg ="자신의 글만 삭제할 수 있습니다.";
	    		}else{
	        		String content = (String) req.get("content");
	        		long updatedArticle = ArticleRepo.updateArticle(articlePk,content);
	        		res.put("updatedArtcle", updatedArticle);
	        		msg = "성공적으로 수정되었습니다.";
	    		}
    	}

    	res.put("msg",msg);
	}else if(articleClass.equals("discussion")){
    	
    }
	return res;
}
@Override
public Map<String, Object> insertArticle(String articleClass,Map<String, Object> req){
	String msg = "";
	Map<String,Object>res = new HashMap<String,Object>();
	Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
	//임시멤버 등록
//	long memberNo = 1;
//	Member member = memberRepository.findByNo(memberNo).orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
	if (articleClass.equals("article")){
		String category = (String) req.get("category");
		String language = (String) req.get("language");
		String problemSiteNM = (String) req.get("pSite");
		String problemNoCheck =  (String) req.get("pNum");
		if (problemNoCheck.equals("")){
			msg = "문제넘버를 입력해주세요";
//			throw new IllegalStateException(msg);
		}else {
			Problem_Site_List problemSite = problemSiteListRepo.findOne(problemSiteNM);
			if (problemSite == null){
				msg = "존재하지 않는 문제 사이트입니다.";
			}else {
				long problemNo = Long.valueOf(problemNoCheck);
				
				Problem_Site problem =problemSiteRepo.sltOneProblem(problemSite, problemNo);
				Use_Language useLanguage= useLanguageRepo.findOne(language);
				if (problem == null) {
					Problem_Site insertProblemSite = new Problem_Site();
					insertProblemSite.setProblemNo(problemNo);
					insertProblemSite.setProblemSiteName(problemSite);
					insertProblemSite.setProblemSiteLink("");
					problem =problemSiteRepo.save(insertProblemSite);
					problemSiteRepo.flush();
				}
				
				if(useLanguage == null){
					msg = "해당 언어가 존재하지 않습니다.";
				}else{
					Article insertArticle = new Article();
					insertArticle.setArticleClass(Article_Class.valueOf(category));
					insertArticle.setArticleContent((String) req.get("content"));
					insertArticle.setArticleTitle((String)req.get("title"));
					insertArticle.setMember(member);
					insertArticle.setProblemSite(problem);
					insertArticle.setUseLanguage(useLanguage);
					Article inserted = ArticleRepo.save(insertArticle);
//					Article inserted = ArticleRepo.sa
					
					res.put("like",0);
					msg = "등록된 댓글이 없습니다.";
					if (category.equals("A01")){
						Article_Algorithm artiAlgo = new Article_Algorithm();
						List<String> usedAlgo = (List<String>) req.get("algo");
						for (String algo : usedAlgo) {
							Algorithm sltOneAlgo = AlgoRepo.sltOne(algo);
							if (sltOneAlgo != null){
								artiAlgo.setAlgorithmName(sltOneAlgo);
								artiAlgo.setArticleNo(inserted);
								Article_Algorithm insertedArtiAlgo = ArtiAlgoRepo.save(artiAlgo);
							}else {
								msg = "알고리즘이 존재하지 않습니다.";
							}
						}
						ArtiAlgoRepo.flush();
					}
					res.put("article", inserted);
				}
			}	
		}
	}else if(articleClass.equals("discussion")){
	}
	res.put("msg", msg);
	return res;
			
}
@Override
public Map<String, Object> likeArticle(String articleClass, long articlePk, Map<String, Object> req) {
	//로그인한 유저 존재하는지 조회
	Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
	Map<String,Object>res = new HashMap<String,Object>();
	if (articleClass.equals("article")){
		//게시글 존재하는지 조회
		Article article = ArticleRepo.sltOne(articlePk);
		//좋아요 누른적 있는지 조회
		Article_Like arli = ArticleLikeRepo.ifMemberExist(member);
		//눌려져있으면 delete. 안눌러져 있으면 insert
		if(arli == null) {
			Article_Like insertLike = new Article_Like();
			insertLike.setArticleNo(article);
			insertLike.setMember(member);
			ArticleLikeRepo.save(insertLike);
			res.put("mylike", true);
		}else {
			ArticleLikeRepo.deleteLike(member,article);
			res.put("mylike", false);
		}
		long likeCount = ArticleRepo.likeArticle(article);
		res.put("likeCount", likeCount);
		res.put("articleNo", article.getArticleNo());
	}else if(articleClass.equals("discussion")){
		
	}
	return res;
}

}
