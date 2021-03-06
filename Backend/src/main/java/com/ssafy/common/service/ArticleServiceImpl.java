package com.ssafy.common.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;


import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.domain.Use_Language;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Algorithm;
import com.ssafy.common.domain.article.Article_Class;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.Article_Like;
import com.ssafy.common.domain.discuss.Discuss;
import com.ssafy.common.domain.discuss.Discuss_Host;
import com.ssafy.common.domain.helpme.Helpme;
import com.ssafy.common.domain.helpme.Helpme_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.Article_AlgorithmDTO;
import com.ssafy.common.dto.Article_CommentDTO;
import com.ssafy.common.dto.DiscussDTO;
import com.ssafy.common.dto.Discuss_CommentDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.HelpmeRepository;
import com.ssafy.common.repository.Helpme_LikeRepository;
import com.ssafy.common.repository.Use_LanguageRepository;
import com.ssafy.common.repository.Algorithm.AlgorithmRepository;
import com.ssafy.common.repository.article.ArticleRepository;
import com.ssafy.common.repository.article.Article_AlgorithmRepository;
import com.ssafy.common.repository.article.Article_CommentRepository;
import com.ssafy.common.repository.article.Article_LikeRepository;
import com.ssafy.common.repository.discuss.DiscussRepository;
import com.ssafy.common.repository.discuss.Discuss_CommentRepository;
import com.ssafy.common.repository.discuss.Discuss_HostRepository;
import com.ssafy.common.repository.member.MemberRepository;
import com.ssafy.common.repository.problem.Problem_Site_ListRepository;
import com.ssafy.common.repository.problem.Problem_Site_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
  @Autowired
  private  ArticleRepository ArticleRepo;
  
  @Autowired
  private HelpmeRepository helpmeRepo;
  @Autowired
  private Helpme_LikeRepository helLiRepo;
  @Autowired
  private Article_CommentRepository articleCommetRepo;
  
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
  
  @Autowired
  private DiscussRepository disRepo;
  @Autowired
  private Discuss_CommentRepository disComRepo;
  @Autowired
  private Discuss_HostRepository disHostRepo;
  @Override
  public Map<String, Object> sltMultiArticle(String articleClass,int page) {
    Map<String, Object> res = new HashMap<String,Object>();
    Long nowLoginMemberNo=0L;
	try {
		//????????? ????????? ?????? ??????????????? ?????? ???????????? likeState ??????
		nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
	}catch (RuntimeException e) {
		nowLoginMemberNo=0L;
	}
	if(articleClass.equals("article")){
    	//????????? ?????? ?????? ??????
        List<ArticleDTO> articleList = ArticleRepo.sltMulti(nowLoginMemberNo, PageRequest.of(page, Common.PAGE)).orElse(null);
        if (articleList.size()==0) {//???????????? ???????????? ?????? ???.
        	res.put("msg", "?????? ???????????? ???????????? ????????????.");
        }else {
        	//????????? - ???????????? ?????? ???????????????
        	for(int idx = 0; idx < articleList.size();idx++) {
        		List<Article_AlgorithmDTO> artiAlgo= ArtiAlgoRepo.sltMultiByArticleDTO(articleList.get(idx));
        		List<String> tempAlgo = new ArrayList<String>();
        		for(Article_AlgorithmDTO AlgoOne:artiAlgo) {
        			tempAlgo.add(AlgoOne.getUsedAlgorithm());
        		}
        		articleList.get(idx).setAlgo(tempAlgo);
        	}
        	 res.put("article", articleList);
        }
    }else if(articleClass.equals("qna")){
        List<ArticleDTO> articleList = ArticleRepo.sltMultiQnA(nowLoginMemberNo, PageRequest.of(page, Common.PAGE), Article_Class.A00).orElse(null);
        res.put("article", articleList);
        if (articleList.size()==0) //???????????? ???????????? ?????? ???.
        	res.put("msg", "QnA ???????????? ???????????? ????????????.");

    }
    else if(articleClass.equals("discussion")){
    	List<DiscussDTO>discussList = disRepo.sltMulti(PageRequest.of(page, 20)).orElse(null);
    	res.put("discussion",discussList);
    	 if (discussList.size()==0) //???????????? ???????????? ?????? ???.
         	res.put("msg", "????????? ???????????? ????????????.");
    }
    return res;
    
  }
  
  
  
  @Override
  public Map<String, Object>sltOneArticle(String articleClass,long pk){
	  //????????? ???????????? ??? ?????? ??????
	Long nowLoginMemberNo=0L;
	try {
		//????????? ????????? ?????? ??????????????? ?????? ???????????? likeState ??????
		nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
	}catch (RuntimeException e) {
		nowLoginMemberNo=0L;
	}
	String msg = "";
	  Map<String,Object>res = new HashMap<String,Object>();
	  	if(articleClass.equals("article")){
	    	ArticleDTO article = ArticleRepo.sltOne(pk, nowLoginMemberNo);
	    	if (article != null) {
	    		List<Article_AlgorithmDTO> existAlgos = ArtiAlgoRepo.sltMultiByArticleDTO(article);
	    		List<String> tempAlgo = new ArrayList<String>();
        		for(Article_AlgorithmDTO AlgoOne:existAlgos) {
        			tempAlgo.add(AlgoOne.getUsedAlgorithm());
        		}
	    		article.setAlgo(tempAlgo);
		    	res.put("articleDetail",article);
//		    	res.put("algo",  existAlgos);
	    	}else {
	    		msg="???????????? ???????????? ????????????.";
	    	}
	    }
	    else if(articleClass.equals("discussion")){
	    	DiscussDTO article = disRepo.sltOne(pk, nowLoginMemberNo);
	    	if (article != null) {
	    		res.put("articleDetail",article);
	    	}
	    	else {
	    		msg="?????? ????????? ???????????? ????????????.";
	    	}
	    	
	    }
	  	res.put("msg",msg);
	  return res;
  }
  @Override
  public Map<String, Object>deleteArticle(String articleClass,long articlePk){
	  Map<String,Object>res = new HashMap<String,Object>();
	  String msg = "";
	  Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
	  	if(articleClass.equals("article")){
	  		
	  		//?????? ??? ????????????
	    	Article article = ArticleRepo.sltOneArticle(articlePk);
	    	if (article == null) {
	    		msg = "???????????? ?????? ??????????????????.";
	    	}else {
	    		//?????? ??? ?????? ?????? ??????
	    		if(member != article.getMember()){
	    		msg ="????????? ?????? ????????? ??? ????????????.";
	    		}else{
		    		ArticleRepo.deleteById(articlePk);
		    		long deletedArticle = articlePk;
		    		res.put("deletedArtcle", deletedArticle);
		    		msg = "??????????????? ?????????????????????.";
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
			.orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
	if(articleClass.equals("article")){
		//?????? ??? ?????? ??????
		Article article = ArticleRepo.sltOneArticle(articlePk);
		if (article == null) {
    		msg = "???????????? ?????? ??????????????????.";
    	}else {
    		if(member != article.getMember()){
	    		msg ="????????? ?????? ????????? ??? ????????????.";
	    		}else{
	        		String content = (String) req.get("content");
	        		String title = (String)req.get("title");
	        		String category = (String) req.get("category");
	        		article.setArticleContent(content);
	        		article.setArticleTitle(title);
	        		if (content.trim() == "" || title.trim() == "") {
							msg = "???????????? ????????? ???????????? ????????????";
							res.put("msg", msg);
							return res;
						}
	        		article.setArticleClass(Article_Class.valueOf(category));
	        		long updatedArticle = ArticleRepo.updateArticle(articlePk,article);
	        		res.put("updatedArtcle", updatedArticle);
	        		msg = "??????????????? ?????????????????????.";
	        		if (category.equals("A01")){
						Article_Algorithm artiAlgo = new Article_Algorithm();
						List<String> usedAlgo = (List<String>) req.get("algo");
						List<Article_Algorithm> existAlgos = ArtiAlgoRepo.sltMultiByArticle(article);
						if (existAlgos != null) {
						for (Article_Algorithm existAlgo: existAlgos) {
							ArtiAlgoRepo.delete(existAlgo);
							}
						}
						if (usedAlgo !=null) {
							for (String algo : usedAlgo) {
								Algorithm sltOneAlgo = AlgoRepo.sltOne(algo);
								if (sltOneAlgo != null){
									artiAlgo.setAlgorithmName(sltOneAlgo);
									artiAlgo.setArticleNo(article);
									Article_Algorithm insertedArtiAlgo = ArtiAlgoRepo.save(artiAlgo);
									res.put("updated", insertedArtiAlgo.getArticleNo().getArticleNo());
								}else {
									msg = "??????????????? ???????????? ????????????.";
								}
							}
						}
						ArtiAlgoRepo.flush();
						
					}
	        		
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
			.orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));

	if (articleClass.equals("article")){
		String category = (String) req.get("category");
		String language = (String) req.get("language");
		String problemSiteNM = (String) req.get("pSite");
		String problemNoCheck =  (String) req.get("pNum");
		if (problemNoCheck.equals("")){
			msg = "??????????????? ??????????????????";
		}else {
			Problem_Site_List problemSite = problemSiteListRepo.findOne(problemSiteNM);
			if (problemSite == null){
				msg = "???????????? ?????? ?????? ??????????????????.";
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
					msg = "?????? ????????? ???????????? ????????????.";
				}else{
					String content = (String) req.get("content");
					String title = (String)req.get("title");
					if (content.trim() == "" || title.trim() == "") {
						msg = "???????????? ????????? ???????????? ????????????";
						}else {
						Article insertArticle = new Article();
						insertArticle.setArticleClass(Article_Class.valueOf(category));
						insertArticle.setArticleContent(content);
						insertArticle.setArticleTitle(title);
						insertArticle.setMember(member);
						insertArticle.setProblemSite(problem);
						insertArticle.setUseLanguage(useLanguage);
						Article tempInserted = ArticleRepo.save(insertArticle);
						ArticleRepo.flush();
						if (category.equals("A01")){
							Article_Algorithm artiAlgo = new Article_Algorithm();
							List<String> usedAlgo = (List<String>) req.get("algo");
							if (usedAlgo !=null) {
								for (String algo : usedAlgo) {
									Algorithm sltOneAlgo = AlgoRepo.sltOne(algo);
									if (sltOneAlgo != null){
										artiAlgo.setAlgorithmName(sltOneAlgo);
										artiAlgo.setArticleNo(tempInserted);
										ArtiAlgoRepo.save(artiAlgo);
									}else {
										msg = "??????????????? ???????????? ????????????. ??????????????? ???????????? ?????? ?????? ???????????????.";
									}
								}
							}
							ArtiAlgoRepo.flush();
							
						}
						ArticleDTO inserted = ArticleRepo.sltOne(tempInserted.getArticleNo(), member.getNo());
						List<Article_CommentDTO> comments = articleCommetRepo.artiComments(inserted.getArticleNo(),member.getNo(),PageRequest.of(0, 20)).orElse(null);
						res.put("article", inserted);
						res.put("articleComments", comments);
					}
				}
			}	
		}
	}
	res.put("msg", msg);
	return res;
			
}
@Override
public Map<String, Object> likeArticle(String articleClass, long articlePk, Map<String, Object> req) {
	//???????????? ?????? ??????????????? ??????
	Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new IllegalStateException("????????? ??????????????? ????????????"));
	Map<String,Object>res = new HashMap<String,Object>();
	if (articleClass.equals("article")){
		//????????? ??????????????? ??????
		Article article = ArticleRepo.sltOneArticle(articlePk);
		if (article == null) {
			res.put("msg", "???????????? ???????????? ????????????");
			return res;
		}
		//????????? ????????? ????????? ??????
		Article_Like arli = ArticleLikeRepo.ifMemberExist(articlePk,member.getNo());
		//?????????????????? delete. ???????????? ????????? insert
		if(arli == null) {
			Article_Like insertLike = new Article_Like();
			insertLike.setArticleNo(article);
			insertLike.setMember(member);
			ArticleLikeRepo.save(insertLike);
			res.put("myLike", true);
			res.put("member",article.getMember().getNo());//????????? ?????????
		}else {
			ArticleLikeRepo.deleteLike(member,article);
			res.put("myLike", false);
		}
		long likeCount = ArticleRepo.likeArticle(article);
		res.put("likeCount", likeCount);
		res.put("articleNo", article.getArticleNo());
	}else if(articleClass.equals("discussion")){
		//????????? ????????? ?????????????????? ???
	}else if(articleClass.equals("helpme")) {
		Helpme article = helpmeRepo.getById(articlePk);
		Helpme_Like arli = helLiRepo.ifMemberExist(articlePk,member.getNo());
		if (article == null) {
			res.put("msg", "???????????? ???????????? ????????????");
			return res;
		}
		if(arli == null) {
			Helpme_Like insertLike = new Helpme_Like();
			insertLike.setHelpmeNo(article);
			insertLike.setMember(member);
			helLiRepo.save(insertLike);
			res.put("myLike", true);
			res.put("member",article.getHelpmeSenderNo().getNo());//????????? ?????????
		}else {
			helLiRepo.deleteLike(member,article);
			res.put("myLike", false);
		}
		long likeCount = helpmeRepo.likeArticle(article);
		res.put("likeCount", likeCount);
		res.put("articleNo", article.getHelpmeNo());
	}
	
	return res;
}
@Override
public Map<String,Object>sltMultiDiscussByHost(Long HostPK,int page){
	Map<String,Object>res = new HashMap<String,Object>();
	String msg = "";
	List<DiscussDTO> discussList = disRepo.sltMultiByDisHost(HostPK, PageRequest.of(page, Common.PAGE)).orElse(null);
	res.put("article", discussList);
	if (discussList.size() ==0) {
		msg = "????????? ????????? ????????????";
	}
	res.put("msg", msg);
	return res;
	
}

}
