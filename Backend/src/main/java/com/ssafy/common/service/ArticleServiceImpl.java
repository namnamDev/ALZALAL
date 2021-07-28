package com.ssafy.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.ArticleRepositorySupport;
import com.ssafy.common.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
  @Autowired
  private ArticleRepositorySupport ars;
  @Autowired
  private MemberRepository memberRepository;
  @Override
  public Map<String, Object> sltMultiArticle(String articleClass) {
	//게시글 다건조회.. 추후 페이징처리 예정
    Map<String, Object> res = new HashMap<String,Object>();
    if (articleClass.equals("main")){
    	
    }else if(articleClass.equals("article")){
    	//게시글 전체 조회 결과
        List<Article> articleList = ars.findAll();
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
	    	Article article = ars.sltOne(pk);
	    	res.put("articleDetail",article);
	    	List<Article_Comment>comments = ars.articleComments(article);
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
	    	Article article = ars.sltOne(articlePk);
	    	if (article == null) {
	    		msg = "존재하지 않는 게시글입니다.";
	    	}else {
	    		//삭제 전 유저 일치 조회
	    		if(member != article.getMember()){
	    		msg ="자신의 글만 삭제할 수 있습니다.";
	    		}else{
		    		long deletedArticle = ars.articleDelete(articlePk);
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
		Article article = ars.sltOne(articlePk);
		if (article == null) {
    		msg = "존재하지 않는 게시글입니다.";
    	}else {
    		if(member != article.getMember()){
	    		msg ="자신의 글만 삭제할 수 있습니다.";
	    		}else{
	        		String content = (String) req.get("content");
	        		long updatedArticle = ars.updateArticle(articlePk,content);
	        		res.put("updatedArtcle", updatedArticle);
	        		msg = "성공적으로 수정되었습니다.";
	    		}
    	}

    	res.put("msg",msg);
	}else if(articleClass.equals("discussion")){
    	
    }
	return res;
}
  
}
