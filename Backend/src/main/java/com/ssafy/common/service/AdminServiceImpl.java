package com.ssafy.common.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.common.domain.Algorithm;
import com.ssafy.common.domain.Use_Language;
import com.ssafy.common.domain.Use_Language_Like;
import com.ssafy.common.domain.article.Article;
import com.ssafy.common.domain.article.Article_Algorithm;
import com.ssafy.common.domain.article.Article_Class;
import com.ssafy.common.domain.article.Article_Like;
import com.ssafy.common.domain.discuss.Discuss;
import com.ssafy.common.domain.discuss.Discuss_Host;
import com.ssafy.common.domain.member.Member;
import com.ssafy.common.domain.problem.Problem_Site;
import com.ssafy.common.domain.problem.Problem_Site_Like;
import com.ssafy.common.domain.problem.Problem_Site_List;
import com.ssafy.common.dto.ArticleDTO;
import com.ssafy.common.dto.Article_CommentDTO;
import com.ssafy.common.dto.DiscussDTO;
import com.ssafy.common.dto.Discuss_CommentDTO;
import com.ssafy.common.jwt.util.SecurityUtil;
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

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	@Autowired
	private ArticleRepository ArticleRepo;
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

	// 회원가입 관련
	@Autowired
	private Problem_Site_ListRepository problem_Site_ListRepository;
	@Autowired
	private Use_LanguageRepository use_LanguageRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	// 회원가입 관련 끝

		// 게시글 다건조회.. 추후 페이징처리 예정
	@Override
	public Map<String, Object> insertArticle(String articleClass, int num) {
		String msg = "";
		Map<String, Object> res = new HashMap<String, Object>();
		//멤버 list
		List<Member>members = memberRepository.findAll();
		//알고리즘 list
		List<Algorithm>algos = AlgoRepo.findAll();
		//문제사이트리스트 list
		List<Problem_Site_List> proSiteLists = problem_Site_ListRepository.findAll();
		//언어 list
		List<Use_Language> useLanguageLists = use_LanguageRepository.findAll();
		//A00 A01
		String[] ifQnA = {"A00","A01"};
		//제목 list
		String[] titles = {
				"컴공 들어가면 노트북 새로 사야댐?",
				"예체능에서 편입했는데",
				"학부때 C++ 안개서 개꿀 했는데",
				"incref decref가 뭐 줄임말임?",
				"자바하다가 js하니깐 초반엔 졸라편했는데",
				"몸이 안좋으니 하루가 길군 후우",
				"오늘은 dfs를 공부해볼거야",
				"뉴비 골3문제 한 번에 품 ㅋㅋㅋ ",
				"프린이 열혈 vs 따배씨 둘중에 하나추천좀여",
				"흠.. 내가 본 재능충",
				};
		//콘텐츠 list
		String[] contents = {
				"3년전인가 50만원짜리 노트북 있는데\r\n" + "\r\n" + "레노버 ideapad 330이고\r\n" + "\r\n" + "그래픽은 gxt 1050임\r\n" + "\r\n",
				"이번학기가 첫학기라서 다른사람보다 떨어지는건 당연하다 생각하지만 조별과제할때 얘기 같이하다보면 너무 차이나는거같아서 좀 비참하네\r\n" + 	"과제점수랑 중간고사 평균보다 조금 높은데 내가 욕심이많은걸까",
				"지금 아주 개 좆털리는중 씨팔 ㅠㅜ ",
				"문서도 없이 코드 볼때마다 좆같노",
				"자바하다가 js하니깐 초반엔 졸라편했는데\r\n" + "\r\n" + "ㅅㅂ\r\n" + "\r\n" + "더복잡한거같다 ..\r\n" + 	"\r\n" + "\r\n" + "\r\n" + "자꾸문자열 비교할때 equals 를 쳐쓰고있네 ;개짱난다",
				"ㅇㄹㄴ",
				"그것은 기본중에 기본인데스",
				"와 진짜 성장했다\r\n" + "\r\n" + "팰린드롬? 이 문제 ㅋㅋ\r\n" + 	"이거보다 내리막길이 더 어렵던데..\r\n" + "\r\n" + "곧 200문제 찍음 ㅎㅎ",
				"!!",
				"중경외시 디자인과 나온 지지배인데\r\n" + "\r\n" + "코딩 1도 모름. 물론 포토샵과 일러는 잘했음..\r\n" + "\r\n" + "\r\n" +	"\r\n" + 
				"html 3일 가르침.\r\n" + "\r\n" + 	"\r\n" + "\r\n" + "클론 코딩으로 네이버 배끼기 시킴. 3일 걸려서 완성.\r\n" + "\r\n" + "그담부터 다음 클론 코딩 2일 걸림. 조선족 피싱사이트 수준으로 완벽하게 복제\r\n" + 
				"\r\n" + "\r\n" + "\r\n" + 	"그리고 한 1주일 지났나...\r\n" + "\r\n" + "그냥 눈에 보이는 아무거나 \"야~ 저거 좀 화면 따봐~\" 하면 1시간만에 사이트를 복제 따버리는 신공..\r\n" + 
				"\r\n" + "\r\n" + "\r\n" + "또 그렇게 1달 뒤..\r\n" + "\r\n" + "나보고 \"에이~ 그렇게 하면 css가 안먹고 밀리죠...\" 하면서 보닌한테 역으로 훈계하기 시작..\r\n" + 
				"\r\n" + "\r\n" + "\r\n" + 
				"근데 좆도 몇명도 없는데 다른 애들 (지잡지잡지잡)에들이 견제가 일어남..\r\n" + 	"\r\n" + "야근은 존나 잘 견디던 애가 정치질에 스트레스에 맛이 감.\r\n" + 
				"\r\n" + "결국 퇴사...\r\n" + 	"\r\n" + "\r\n" + "\r\n" + 
				"사람에 대해 존나 많은걸 깨우치게 된 경험이 되어씀"
				
		};
		//문제넘버 
		String[] problemNums = {"123","215","1","66","2"};
		System.out.println(members.size());
		System.out.println(algos.size());
		System.out.println(proSiteLists.size());
		System.out.println(useLanguageLists.size());
		if (articleClass.equals("article")) {
			Random r = new Random();
			for (int n = 0; n< num;n++) {
				Member member = members.get(r.nextInt(members.size()));
				String category = ifQnA[r.nextInt(2)];
				
				
				
				
				String problemSiteNM = proSiteLists.get(r.nextInt(proSiteLists.size())).getProblemSiteName();
				String language = useLanguageLists.get(r.nextInt(useLanguageLists.size())).getUseLanguage();
				String problemNoCheck = problemNums[r.nextInt(problemNums.length)];

				Problem_Site_List problemSite = problemSiteListRepo.findOne(problemSiteNM);
				long problemNo = Long.valueOf(problemNoCheck);
				//문제 사이트명 단건조회
				Problem_Site problem = problemSiteRepo.sltOneProblem(problemSite, problemNo);
				//언어 단건 조회
				Use_Language useLanguage = useLanguageRepo.findOne(language);
				//문제 미 존재시 입력
				if (problem == null) {
					Problem_Site insertProblemSite = new Problem_Site();
					insertProblemSite.setProblemNo(problemNo);
					insertProblemSite.setProblemSiteName(problemSite);
					insertProblemSite.setProblemSiteLink("");
					problem = problemSiteRepo.save(insertProblemSite);
					problemSiteRepo.flush();
				}
					int ranArti = r.nextInt(titles.length); 
					Article insertArticle = new Article();
					insertArticle.setArticleClass(Article_Class.valueOf(category));
					insertArticle.setArticleTitle(titles[ranArti]);
					insertArticle.setArticleContent(contents[ranArti]);
					insertArticle.setMember(member);
					insertArticle.setProblemSite(problem);
					insertArticle.setUseLanguage(useLanguage);
					Article tempInserted = ArticleRepo.save(insertArticle);
					ArticleRepo.flush();
					if (category.equals("A01")) {
						int artiAlgoSize = r.nextInt(3)+1;
						List<String> usedAlgo = new ArrayList<>();
						
						for(int algo = 0;algo<artiAlgoSize;algo++) {
							usedAlgo.add(algos.get(r.nextInt(algos.size())).getAlgorithmName());
						}
						System.out.println(usedAlgo);
						for (String algo : usedAlgo) {
							Article_Algorithm artiAlgo = new Article_Algorithm();
							Algorithm sltOneAlgo = AlgoRepo.sltOne(algo);
								artiAlgo.setAlgorithmName(sltOneAlgo);
								artiAlgo.setArticleNo(tempInserted);
								//단건조회 후 미존재시 입력할것
								Optional<Article_Algorithm> aa = ArtiAlgoRepo.sltOne(sltOneAlgo, tempInserted);
								System.out.println(aa);
								System.out.println(sltOneAlgo);
								System.out.println(tempInserted);
								if (aa.isEmpty()) {
									ArtiAlgoRepo.save(artiAlgo);
									}
								}
						}
						ArtiAlgoRepo.flush();

							
						
						
					}
				
				// 초기 데이터 입력용 (관리자기능)
		} else if (articleClass.equals("discussion")) {
			System.out.println("discussion" + "초기데이터 입력 시작");
			String[] hosts = {"KAKAO", "LINE", "PROGRAMMERS", "BAEKJOON"};
			String[] year = {"2016", "2017", "2018", "2019", "2020", "2021"};
			String[] type = {"INTERN", "BLIND RECRUITMENT", "TEST", "CODE"};
			String[] date = {"0103", "0401", "0602", "1123", "1105"};
			String time = "09:00:00";
			String a = "";
			Random r = new Random();

			for (int i = 0; i < hosts.length; i++) {
				Long g = (long) i;
				String hostOne = hosts[i];
				Discuss_Host dh = new Discuss_Host();
				dh.setDiscussCompHostName(hostOne);
				dh.setDiscussCompHostNo(0L);
				Discuss_Host inserted = disHostRepo.save(dh);
				a = hostOne;
				for (String yearOne : year) {
					for (String typeOne : type) {
						int dateOne = r.nextInt(5);
						LocalDateTime d = LocalDateTime.parse(
								yearOne + date[dateOne] + " " + time,
								DateTimeFormatter
										.ofPattern("yyyyMMdd HH:mm:ss"));
						Discuss ds = new Discuss();
						ds.setDiscussCompHostNo(inserted);
						ds.setDiscussCompName(
								hostOne + " " + yearOne + " " + typeOne);
						ds.setDiscussDate(d);
						for (int ii = 1; ii < 6; ii++) {
							ds.setDiscussCompProblem(ii + "번");
						}
						Discuss insertedDiscuss = disRepo.save(ds);

					}

				}

			}
			msg = "성공적으로 입력하였습니다.";

		}
		res.put("msg", msg);
		return res;

	}
	@Override
	public Map<String, Object> likeArticle(String articleClass, long articlePk,
			Map<String, Object> req) {
		// 로그인한 유저 존재하는지 조회
		Member member = memberRepository
				.findByNo(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new IllegalStateException("로그인 유저정보가 없습니다"));
		Map<String, Object> res = new HashMap<String, Object>();
		if (articleClass.equals("article")) {
			// 게시글 존재하는지 조회
			Article article = ArticleRepo.sltOneArticle(articlePk);
			// 좋아요 누른적 있는지 조회
			Article_Like arli = ArticleLikeRepo.ifMemberExist(articlePk,member.getNo());
			// 눌려져있으면 delete. 안눌러져 있으면 insert
			if (arli == null) {
				Article_Like insertLike = new Article_Like();
				insertLike.setArticleNo(article);
				insertLike.setMember(member);
				ArticleLikeRepo.save(insertLike);
				res.put("mylike", true);
			} else {
				ArticleLikeRepo.deleteLike(member, article);
				res.put("mylike", false);
			}
			long likeCount = ArticleRepo.likeArticle(article);
			res.put("likeCount", likeCount);
			res.put("articleNo", article.getArticleNo());
		} else if (articleClass.equals("discussion")) {

		} else if (articleClass.equals("helpme")) {
			
		}
		return res;
	}
	@Override
	public Map<String, Object> sltMultiDiscussByHost(Long HostPK, int page) {
		Map<String, Object> res = new HashMap<String, Object>();
		String msg = "";
		List<DiscussDTO> discussList = disRepo
				.sltMultiByDisHost(HostPK, PageRequest.of(page, 20))
				.orElse(null);
		res.put("article", discussList);
		if (discussList.size() == 0) {
			msg = "등록된 토론이 없습니다";
		}
		res.put("msg", msg);
		return res;

	}

	// 회원가입
	@Override
	public int signup(Map<String, Object> req, int num) {
		int cnt = 0;
		for (int i = 0; i < num; i++) {
			Member member = new Member();
			member.setEmail((String) req.get("email") + i);
			member.setPassword((String) req.get("password"));
			member.setName((String) req.get("name") + i);
			List<String> problem_site = (List<String>) (req
					.get("problem_site"));
			List<String> use_language = (List<String>) (req
					.get("use_language"));

			try {
				singupMethod(member, problem_site, use_language);
				cnt++;
			} catch (IllegalStateException e) {
			}
		}
		return cnt;

	}
	//회원가입 동작 로직
	private void singupMethod(Member member, List<String> problem_site_list,
			List<String> use_language_like) {
		if (memberRepository.findByName(member.getName()).isPresent())
			throw new IllegalStateException("이미 존재하는 이름입니다");

		if (memberRepository.findByEmail(member.getEmail()).isPresent())
			throw new IllegalStateException("이미 존재하는 이메일입니다");

		// 선호하는 문제 사이트 추가
		List<Problem_Site_Like> pslikeList = new ArrayList<>();
		if (problem_site_list != null) {
			for (String s : problem_site_list) {
				Problem_Site_List pslist = problem_Site_ListRepository
						.findOne(s);
				// 선택된 문제 사이트가 존재하지 않는 경우
				if (pslist == null) {
					throw new IllegalStateException("존재하지 않는 문제 사이트 입니다");
				}
				Problem_Site_Like tmp = new Problem_Site_Like();
				tmp.setProblemSiteName(pslist);
				tmp.setMemberNo(member);

				pslikeList.add(tmp);
			}
		}

		// 선호하는 언어 추가
		List<Use_Language_Like> ullikeList = new ArrayList<>();
		if (use_language_like != null) {
			for (String s : use_language_like) {
				Use_Language ul = use_LanguageRepository.findOne(s);
				// 선택된 언어가 존재하지 않는 경우
				if (ul == null) {
					throw new IllegalStateException("존재하지 않는 언어 입니다");
				}
				Use_Language_Like tmp = new Use_Language_Like();
				tmp.setUseLanguage(ul);
				tmp.setMemberNo(member);

				ullikeList.add(tmp);
			}
		}

		// 선호하는 사이트, 언어 추가
		member.createMember(pslikeList, ullikeList);

		// 비밀번호 암호화
		member.setPassword(passwordEncoder.encode(member.getPassword()));

		memberRepository.save(member);
	}


}
