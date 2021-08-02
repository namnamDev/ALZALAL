package com.ssafy.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.discuss.Discuss;
import com.ssafy.common.domain.discuss.Discuss_Comment;
import com.ssafy.common.domain.article.Article_Comment;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.Article_CommentDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
import com.ssafy.common.repository.article.ArticleRepository;
import com.ssafy.common.repository.article.Article_CommentRepository;
import com.ssafy.common.repository.discuss.DiscussRepository;
import com.ssafy.common.repository.discuss.Discuss_CommentRepository;
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
	  private Article_CommentRepository ArtiComRepo;
	@Autowired
	  private Discuss_CommentRepository disComRepo;
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
				Long deleted = ArtiComRepo.artiComDelete(commentPK);
				res.put("deleted", deleted);
			}else {
				msg = "작성자만 삭제할 수 있습니다.";
			}
		}else if (articleClass.equals("discussion")) {
			Discuss_Comment artiCom = disComRepo.sltOneArtiCom(commentPK);
			if (artiCom.getMember().getNo() == member.getNo()) {
				msg="삭제완료";
				Long deleted = disComRepo.artiComDelete(commentPK);
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
				
		}
		res.put("msg", msg);
		return res;
		
	}

}
