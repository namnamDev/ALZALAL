package com.ssafy.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.discuss.Discuss;
import com.ssafy.common.domain.discuss.Discuss_Comment;
import com.ssafy.common.domain.discuss.Discuss_Comment_Like;
import com.ssafy.common.domain.helpme.Helpme;
import com.ssafy.common.domain.helpme.Helpme_Comment;
import com.ssafy.common.domain.helpme.Helpme_Comment_Like;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.article.Article_Comment_Like;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.Article_CommentDTO;
import com.ssafy.common.dto.Discuss_CommentDTO;
import com.ssafy.common.dto.Helpme_CommentDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.HelpmeRepository;
import com.ssafy.common.repository.Helpme_CommentRepository;
import com.ssafy.common.repository.Helpme_Comment_LikeRepository;
import com.ssafy.common.repository.article.ArticleRepository;
import com.ssafy.common.repository.article.Article_CommentRepository;
import com.ssafy.common.repository.article.Article_Comment_LikeRepository;
import com.ssafy.common.repository.discuss.DiscussRepository;
import com.ssafy.common.repository.discuss.Discuss_CommentRepository;
import com.ssafy.common.repository.discuss.Discuss_Comment_LikeRepository;
import com.ssafy.common.repository.member.MemberRepository;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{
	@Autowired
	  private MemberRepository memberRepository;
	@Autowired
	  private  ArticleRepository articleRepo;
	@Autowired
	  private  DiscussRepository discussRepo;
	@Autowired
	  private HelpmeRepository helpRepo;
	@Autowired
	  private Article_CommentRepository ArtiComRepo;
	@Autowired
	  private Discuss_CommentRepository disComRepo;
	@Autowired
	  private Helpme_CommentRepository helpComRepo;
	@Autowired
	  private Article_Comment_LikeRepository ArtiComLiRepo;
	@Autowired
	  private Discuss_Comment_LikeRepository disComLiRepo;
	@Autowired
	  private Helpme_Comment_LikeRepository helpComLiRepo;
	
	@Override
	public Map<String, Object> sltMultCommentByArticle(String articleClass,long articlePk,int page){
		String msg = "";
		Map<String, Object> res = new HashMap<String,Object>();
		Long nowLoginMemberNo=0L;
		try {
			nowLoginMemberNo=SecurityUtil.getCurrentMemberId();
		}catch (RuntimeException e) {
			nowLoginMemberNo=0L;
		}
		if (articleClass.equals("article")) {
		Article article = articleRepo.sltOneArticle(articlePk);
		if (article != null) {
	    	List<Article_CommentDTO> comments = ArtiComRepo.artiComments(articlePk,nowLoginMemberNo,PageRequest.of(page, 20)).orElse(null);
	    	if (comments.size() == 0) {
	    		msg = "등록된 댓글이 없습니다.";
	    	}else {
	    		res.put("articleComments", comments);
	    	}
    	}else {
    		msg="게시글이 존재하지 않습니다.";
    		}
		}else if(articleClass.equals("discussion")){
			Discuss article = discussRepo.sltOneArticle(articlePk);
			if (article != null) {
		    	List<Discuss_CommentDTO> comments = disComRepo.artiComments(articlePk,nowLoginMemberNo,PageRequest.of(page, 20)).orElse(null);
		    	if (comments.size() == 0) {
		    		msg = "등록된 댓글이 없습니다.";
		    	}else {
		    		res.put("articleComments", comments);
		    	}
	    	}else {
	    		msg="게시글이 존재하지 않습니다.";
	    		}

		}else if(articleClass.equals("helpme")){
			Helpme article = helpRepo.getById(articlePk);
			if (article != null) {
		    	List<Helpme_CommentDTO> comments = helpComRepo.artiComments(articlePk,nowLoginMemberNo,PageRequest.of(page, 20)).orElse(null);
		    	if (comments.size() == 0) {
		    		msg = "등록된 댓글이 없습니다.";
		    	}else {
		    		res.put("articleComments", comments);
		    	}
	    	}else {
	    		msg="게시글이 존재하지 않습니다.";
	    		}

		}
		
		res.put("msg",msg);
		return res;
	}
	@Override
	public Map<String, Object> insertArticleComment(String articleClass, long articlePk, Map<String, Object> req) {
		Map<String, Object> res = new HashMap<String,Object>();
		String msg = "";
		Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		String content = (String)req.get("commentContent");
		if (articleClass.equals("article")) {
			Article article = articleRepo.sltOneArticle(articlePk);
			if (article != null) {
				Article_Comment comment= new Article_Comment();
				comment.setCommentContent(content);
				comment.setMember(member);
				comment.setArticleNo(article);
				ArtiComRepo.save(comment);
				msg = "성공적으로 입력되었습니다";
			}else {
				msg = "게시글이 존재하지 않습니다.";		
			}
			res.put("msg", msg);
		}else if(articleClass.equals("discussion")){
			Discuss article = discussRepo.sltOneArticle(articlePk);
			System.out.println(article);
			if (article != null) {
				Discuss_Comment comment = new Discuss_Comment();
				comment.setDiscussCommentContent(content);
				comment.setMember(member);
				comment.setDiscussNo(article);
				disComRepo.save(comment);
			}else {
				msg = "토론게시글이 존재하지 않습니다.";
			}
			res.put("msg", msg);

		}else if(articleClass.equals("helpme")) {
			Helpme article = helpRepo.getById(articlePk);
			System.out.println(article);
			if (article != null) {
				Helpme_Comment comment = new Helpme_Comment();
				comment.setHelpmeCommentContent(content);
				comment.setMember(member);
				comment.setHelpmeNo(article);
				helpComRepo.save(comment);
			}else {
				msg = "토론게시글이 존재하지 않습니다.";
			}
			res.put("msg", msg);
		}
		return res;
	}
	
	@Override
	public Map<String, Object> deleteArticleComment(String articleClass , Long commentPK){
		Map<String, Object> res = new HashMap<String,Object>();
		String msg = "";
		Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		if (articleClass.equals("article")) {
			//댓글단건조회
			Article_Comment artiCom = ArtiComRepo.sltOneArtiCom(commentPK);
			//로그인중인 유저와 일치하는지 조회
			if (artiCom.getMember().getNo() == member.getNo()) {
				msg="삭제완료";
				ArtiComRepo.deleteById(commentPK);
				Long deleted = commentPK;
				res.put("deleted", deleted);
			}else {
				msg = "작성자만 삭제할 수 있습니다.";
			}
		}else if (articleClass.equals("discussion")) {
			Discuss_Comment artiCom = disComRepo.sltOneArtiCom(commentPK);
			if (artiCom.getMember().getNo() == member.getNo()) {
				msg="삭제완료";
				disComRepo.deleteById(commentPK);
				Long deleted =commentPK;
				res.put("deleted", deleted);
			}else {
				msg = "작성자만 삭제할 수 있습니다.";
			}
		}else if (articleClass.equals("helpme")) {
			
		}
		res.put("msg", msg);
		return res;
	}
	
	@Override
	public Map<String, Object>updateArtiComment(String articleClass,Long commentPK,Map<String, Object> req){
		Map<String, Object> res = new HashMap<String,Object>();
		String msg = "";
		Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		if (articleClass.equals("article")) {
		//댓글단건조회
			Article_Comment artiCom = ArtiComRepo.sltOneArtiCom(commentPK);
			if (artiCom.getMember().getNo() == member.getNo()) {
				String content = (String)req.get("commentContent");
				msg="수정완료";
				Long updated = ArtiComRepo.artiComUpdate(commentPK, content);
				res.put("updated", updated);
			}else {
				msg = "작성자만 수정할 수 있습니다.";
			}
		}else if (articleClass.equals("discussion")) {
			Discuss_Comment artiCom = disComRepo.sltOneArtiCom(commentPK);
			if (artiCom.getMember().getNo() == member.getNo()) {
				String content = (String)req.get("commentContent");
				msg="수정완료";
				Long updated = disComRepo.artiComUpdate(commentPK, content);
				res.put("updated", updated);
			}else {
				msg = "작성자만 수정할 수 있습니다.";
			}
			
		}else if (articleClass.equals("helpme")) {
			Helpme_Comment artiCom = helpComRepo.sltOneArtiCom(commentPK);
			if (artiCom.getMember().getNo() == member.getNo()) {
				String content = (String)req.get("commentContent");
				msg="수정완료";
				Long updated = helpComRepo.artiComUpdate(commentPK, content);
				res.put("updated", updated);
			}else {
				msg = "작성자만 수정할 수 있습니다.";
			}
		}
		res.put("msg", msg);
		return res;
		
	}
	@Override
	public Map<String, Object> likeComment(String articleClass, long commentPk, Map<String, Object> req){
		//로그인한 유저 존재하는지 조회
		Member member = memberRepository.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		Map<String,Object>res = new HashMap<String,Object>();
		if (articleClass.equals("article")){
			Article_Comment artiCom = ArtiComRepo.getById(commentPk);
			Article_Comment_Like arCoLi = ArtiComLiRepo.ifMemberExist(commentPk, member.getNo());
			if (arCoLi == null) {
				Article_Comment_Like insertLike = new Article_Comment_Like();
				insertLike.setArticleComment(artiCom);
				insertLike.setMember(member);
				ArtiComLiRepo.save(insertLike);
				res.put("myLike", true);
			}else {
				ArtiComLiRepo.deleteLike(member.getNo(), commentPk);
				res.put("mylike", false);
				}
		}else if(articleClass.equals("discussion")){
			Discuss_Comment artiCom = disComRepo.getById(commentPk);
			Discuss_Comment_Like arCoLi = disComLiRepo.ifMemberExist(commentPk, member.getNo());
			if (arCoLi == null) {
				Discuss_Comment_Like insertLike = new Discuss_Comment_Like();
				insertLike.setDiscussCommentNo(artiCom);
				insertLike.setMember(member);
				disComLiRepo.save(insertLike);
				res.put("myLike", true);
			}else {
				disComLiRepo.deleteLike(member.getNo(), commentPk);
				res.put("mylike", false);
				}
			
		}else if(articleClass.equals("helpme")){
			Helpme_Comment artiCom = helpComRepo.getById(commentPk);
			Helpme_Comment_Like arCoLi = helpComLiRepo.ifMemberExist(commentPk, member.getNo());
			if (arCoLi == null) {
				Helpme_Comment_Like insertLike = new Helpme_Comment_Like();
				insertLike.setHelpmeCommentNo(artiCom);
				insertLike.setMember(member);
				helpComLiRepo.save(insertLike);
				res.put("myLike", true);
			}else {
				helpComLiRepo.deleteLike(member.getNo(), commentPk);
				res.put("mylike", false);
				}
			
		}
		return res;
	}
}
