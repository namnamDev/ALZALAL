<template>
  <div class="container" >
    <div class="row" v-if="articleList">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>

      <div class="col-lg-6 col-md-10 col-sm-9 col-10">
        <div class="row top">
          <div class="col">
            <div v-if="problem">
              #{{ problem }} 
              <span class="follow-cancel-btn" @click="follow" v-if="followingState">팔로우취소</span>
              <span class="follow-btn" @click="follow" v-else>팔로우</span>
            </div>
          </div>
        </div>

        <div class="row middle mb-2">
          <div class="col">
            <div style="display: inline" class="me-3" v-if="followingNumber">
              팔로잉 {{ followingNumber }}
            </div>
            <div style="display: inline" class="me-3" v-else>팔로잉 0</div>
            <div style="display: inline" class="me-3" v-if="articleNumber">
              게시글 {{ articleNumber }}
            </div>
            <div style="display: inline" class="me-3" v-else>게시글 0</div>
          </div>
        </div>

        <div class="row mb-2">
          <div class="col">
            <span class="include-algo me-2" v-for="item, idx in includeAlgo" :key="idx">{{item}}</span>
          </div>
        </div>
        <div class="row mb-4">
          <div class="col">
            <span class="exclude-algo me-2" v-for="item, idx in excludeAlgo" :key="idx">{{item}}</span>
          </div>
        </div>

        <div class="row">
          <div class="col">
            총 {{articleCount}}개의 게시글이 검색되었습니다.
          </div>
          <div class="col text-end">
            <span class="order-by-new" @click="clickOrderByNew">최신순</span> | 
            <span class="order-by-like" @click="clickOrderByLike">좋아요순</span>
          </div>          
        </div>

        <div          
          class="row bottom my-3"
          v-for="(item, idx) in articleList"
          :key="idx"
        >
          <div class="col-2 pt-4 member-name">
            {{ item.member.name }}
          </div>
          <div
            class="col article-content"
            @click="articleDetail(item.articleNo)"
          >
            <div class="row article-title">
              {{ item.articleTitle }}
            </div>
            <div class="row">
              <div class="col p-0">
                <span class="hashtag has-category" v-if="item.articleClass == 'A01'"
                  >문제풀이</span
                >
                <span class="hashtag has-category" v-else>QnA</span>
                <span class="hashtag has-language">{{ item.useLanguage }}</span>
                <span
                  class="hashtag has-algo"
                  v-for="(alg, idx) in item.algo"
                  :key="idx"
                  >{{ alg }}</span
                >
              </div>
            </div>
            <div class="row">
              <div class="col p-0">
                {{ item.articleDate }}
              </div>
              <div class="col text-end">
                <i class="fas fa-heart me-2" v-if="item.likeState"></i>
                <i class="far fa-heart me-2" v-else></i>
                <span>{{ item.likeCount }}</span>
                <i class="far fa-comment-dots mx-2"></i>
                <span>{{ item.commentCount }}</span>
              </div>
            </div>
          </div>
        </div>        
      </div>
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="sprial">
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px"
      >
        No more results.
      </div>
      <div
        slot="no-results"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px"
      >
        검색결과가 존재하지 않습니다.
      </div>
    </infinite-loading>
  </div>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
import axios from "axios";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    InfiniteLoading
  },
  data() {
    return {
      includeAlgo: "",
      excludeAlgo: "",
      problem: "",
      problemSite: '',
      problemNo: '',
      language: "",
      followingNumber: "",
      articleNumber: "",
      articleList: [],
      articleLikeList: [],
      articleDateList: [],
      page: 0,
      params: '',
      articleCount: 0 ,   
      followingState: false,   
    };
  },
  computed: {
    getArticle: function () {
      return this.$store.getters.getSearchArticle;
    },
  },
  methods: {
    follow() {
      this.followingState = !this.followingState
      if(this.followingState){
        this.followingNumber += 1
        this.$swal(`'${this.problem}' 문제를 팔로우 하셨습니다.`)
      }
      else{
        this.followingNumber -= 1
        this.$swal(`'${this.problem}' 문제를 팔로우 취소 하셨습니다.`)
      }
      const data = {
        problemSite: this.problemSite,
        problemNo: this.problemNo,
      }
      axios({
        method: 'post',
        url: `${SERVER_URL}/follow/problem`,
        headers: this.getToken(),
        data: data
      })
      .then(()=>{
        
      })
      .catch(err=>{
        console.log(err)
      })
    },
    clickOrderByLike() {
      this.articleList = this.articleLikeList
      const like = document.querySelector('.order-by-like')
      const date = document.querySelector('.order-by-new')
      like.style.color = 'black'
      like.style.fontSize = '18px'
      date.style.color = 'lightgray'
      date.style.fontSize = '16px'
    },
    clickOrderByNew() {
      this.articleList = this.articleDateList
      const like = document.querySelector('.order-by-like')
      const date = document.querySelector('.order-by-new')
      like.style.color = 'lightgray'
      like.style.fontSize = '16px'
      date.style.color = 'black'
      date.style.fontSize = '18px'
    },
    getToken() {
      const token = sessionStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },

    infiniteHandler($state) {      
      axios({
        method: "get",
        url: `${SERVER_URL}/search/article/problem` + "?page=" + this.page,
        headers: this.getToken(),
        params: this.params,
      })
        .then((res) => {
          this.followingState = res.data.followInfo.followingState
          this.articleCount = res.data.articleSearchCount
          setTimeout(() => {
            if (res.data.articleList.length) {
              this.articleDateList = this.articleDateList.concat(res.data.articleList);
              this.articleList = this.articleDateList
              this.followingNumber = res.data.followInfo.followingNumber;
              this.articleNumber = res.data.followInfo.articleNumber;
              $state.loaded();
              this.page += 1;
              //console.log("after", this.page)
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면
              if (res.data.articleList.length / 10 < 1) {
                $state.complete();
              }
            } else {
              // 끝 지정(No more data)
              $state.complete();
            }
          }, 1000);
        })
        .catch(() => {
          // console.error(err);
          $state.complete();
        });

        //좋아요 순으로 요청
        this.params.sort = 'like'
        axios({
          method: "get",
          url: `${SERVER_URL}/search/article/problem` + "?page=" + this.page,
          headers: this.getToken(),
          params: this.params,
        })
          .then((res) => {
            this.articleCount = res.data.articleSearchCount

            setTimeout(() => {
              if (res.data.articleList.length) {
                //console.log(res.data.article.length)      
                this.articleLikeList = this.articleLikeList.concat(res.data.articleList);
                if (this.isOneAlgo){
                  this.followingNumber = res.data.followInfo.followingNumber;
                  this.articleNumber = res.data.followInfo.articleNumber;
                }              
                $state.loaded();
                this.page += 1;
                //console.log("after", this.page)
                // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면
                if (res.data.articleList.length / 10 < 1) {
                  $state.complete();
                }
              } else {
                // 끝 지정(No more data)
                $state.complete();
              }
            }, 1000);
          })
          .catch(() => {
            // console.error(err);
            $state.complete();
          });
    },


    articleDetail(articleNo) {
      localStorage.setItem("articleNo", articleNo);
      axios({
        method: "get",
        url: `${SERVER_URL}/article/article/${articleNo}`,
        headers: this.getToken,
      })
        .then((res) => {
          const data = res.data.articleDetail;
          this.$store.dispatch("createArticleDetail", data);
          console.log(res);
        })
        .then((err) => {
          console.log(err);
        });

      // // 댓글 정보 요청후 store에 저장
      axios({
        method: "get",
        url: `${SERVER_URL}/comment/article/${articleNo}`,
      })
        .then((res) => {
          this.$store.dispatch(
            "createArticleComment",
            res.data.articleComments
          );
          this.$router.push({ name: "articleDetail" });
          // location.href = 'articleDetail'
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
      
    const data = this.getArticle;

    this.problem = data.problem;
    this.problemSite = data.problemSite;
    this.problemNo = data.problemNo;
    this.includeAlgo = data.includeAlgo;
    this.excludeAlgo = data.excludeAlgo;
    this.language = data.language;

    let and = "";
    data.includeAlgo.forEach((element) => {
      and += element + ",";
    });

    let not = "";
    data.excludeAlgo.forEach((element) => {
      not += element + ",";
    });

    and = and.substring(0, and.length - 1);
    not = not.substring(0, not.length - 1);

    let params = {
      problem: data.problem,
      language: data.language,
      and: and,
      not: not,
    };
    if (not == "") {
      delete params.not;
    }
    if (and == "") {
      delete params.and;
    }
    if (params.language == "null") {
      delete params.language;
    }
    this.params = params
    // this.requestProblem(params);
  },
};
</script>

<style scoped>
.top {
  margin-top: 150px;
  font-size: 50px;
  font-weight: bold;
}
.member-name {
  font-size: 20px;
}
.article-content {
  -webkit-box-shadow: 0 0px 10px rgba(0, 0, 0, 0.08);
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.12);
  width: 100%;
  padding: 10px 20px;
  border-radius: 4px;
}
.article-content:hover {
  -webkit-box-shadow: 0 0px 20px rgba(161, 212, 226, 0.6);
  box-shadow: 0 0px 20px rgba(161, 212, 226, 0.6);
  transform: scale(1.1);
  cursor: pointer;
}
.article-title {
  font-size: 30px;
}
.hashtag {
  display: inline-block;
  font-size: 13px;
  border-radius: 3px;
  /* background-color: rgba(221, 223, 230, 1); */
  margin-right: 3px;
  padding: 1px 3px;
  margin-bottom: 4px;
}
.fa-heart {
  color: red;
}
.order-by-new{
  cursor: pointer;
  color: black;
  font-size:18px;
}
.order-by-like{
  cursor: pointer;
  color: lightgray;
}
.has-category{
  background-color:rgba(170, 224, 217) ;
  font-weight: bold;
}
.has-problem{
  background-color:rgb(97, 209, 209) ;
}
.has-language{
  background-color:rgb(126, 208, 233) ; 
}
.has-algo{
  background-color: rgba(186,184,189,1);
}
.include-algo{
  background-color: rgba(161,212,226,1);
  border-radius: 5px;
  padding: 1px 3px;
  font-size: 15px;
  font-weight: bold;
  display: inline-block;
}
.exclude-algo{
  background-color: rgb(246, 163, 142);
  border-radius: 5px;
  padding: 1px 3px;
  font-size: 15px;
  font-weight:bold;
  display: inline-block
}
.follow-btn{
  font-size:20px;
  background-color: rgb(0, 153, 255);
  padding:4px 12px;
  border-radius: 4px;
  color:white;
  cursor: pointer;
}
.follow-btn:hover{
  background-color: rgb(0, 89, 255);
  padding:5px 16px;
  font-size:22px
}
.follow-cancel-btn{
  font-size:20px;
  background-color: white;
  border: 1px solid blue;
  padding:3px 12px;
  border-radius: 4px;
  color:rgb(59, 121, 223);
  cursor: pointer;
}
.follow-cancel-btn:hover{
  /* background-color: rgb(0, 89, 255); */
  font-size:22px;
  padding:5px 16px;
}
</style>