<template>
  <div>
    <div class="row bottom my-3" v-for="item,idx in articleDateList" :key="idx">

      <div class="col article-box" @click="articleDetail(item.articleNo)">
       <div class="row">
        <div class="col-2 image">
          <img class="profileImg" :src="getImgSrc(item.member.no)" @error="imageError = true" alt="프로필사진">
        </div>
        <div class="col">
          <div class="row" >
            <div class="col fs-6 fw-bold">
              <span class="member-name">{{ item.member.name }}</span>
            </div>
            <div class="col text-end">
              <span class="text-secondary">{{item.articleDate}}</span>
            </div>
          </div>
          <div class="row">
            <div>
              <div class="fs-5">{{ item.articleTitle }}</div>
              <div class="hashtag d-flex align-items-center">
                <span v-if="item.articleClass=='A01'" class="has-category">문제풀이</span>
                <span v-else class="has-category">QnA</span>
                <span class="has-language">{{ item.useLanguage }}</span>
                <span class='has-algo ' v-for="alg,idx in item.algo" :key="idx">{{alg}}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="row bottom">
        <div class="col like-comment">
          <i class="fas fa-heart me-2" v-if="item.likeState"></i>
          <i class="far fa-heart me-2" v-else></i>
          <span>{{item.likeCount}}</span>
          <i class="far fa-comment-dots mx-2"></i>
          <span >{{item.commentCount }}</span>
        </div>
      </div>
      
      </div>
    </div>
    <infinite-loading @infinite="infiniteHandlerDate" spinner="sprial">
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px"
      >
        목록의 끝입니다 :)
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
    InfiniteLoading,
  },
  data() {
    return {
      articleDateList: [],
      page: 0,
    };
  },
  computed: {
    params: function () {
      const params = this.$store.getters.getSearchParams;
      params.sort = "like";
      return params;
    },
  },
  methods: {
    getToken() {
      const token = sessionStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },

    infiniteHandlerDate($state) {
      //최신순으로 요청
      axios({
        method: "get",
        url: `${SERVER_URL}/search/article/algorithm` + "?page=" + this.page,
        headers: this.getToken(),
        params: this.params,
      })
        .then((res) => {
          setTimeout(() => {
            if (res.data.articleList.length) {
              this.articleDateList = this.articleDateList.concat(
                res.data.articleList
              );

              $state.loaded();
              this.page += 1;

              if (res.data.articleList.length / 10 < 1) {
                $state.complete();
              }
            } else {
              // 끝 지정(No more data)
              $state.complete();
            }
          }, 1000);
        })
        .catch((err) => {
          console.error(err);
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
          this.$router.push({ name: "articleDetail", params: { Page: "0" } });
          // location.href = 'articleDetail'
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getImgSrc(memberNo){
      return `${SERVER_URL}/profile/img/${memberNo}`;
    }
  }
}
</script>

<style scoped>
.fa-heart {
  color: rgba(62 ,171 ,111 , 1);
}
.fa-comment-dots{
  color:black
}
.image {
  text-align: center;
  align-self: center;
}
.name {
  height: 25px;
  margin-top: 10px;
}
.title {
  height: 30px;
  font-size: 25px;
  margin-bottom: 0;
}
.middle {
  margin-top: 10px;
  padding: 0 20px;  
  /* height: 210px; */
}
.content {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 6; /* 라인수 */
  -webkit-box-orient: vertical;
  word-wrap: break-word;
  height: 100%;
  margin: 0 1rem 0 1rem;
}

.like-comment {
  text-align: end;
}
.hashtag > span {
  font-size: 13px;
  border-radius: 3px;
  /* background-color: rgba(221, 223, 230, 1); */
  padding:4px 8px;
  margin-right: 6px;
  display:inline-block;
}
.has-category{
  padding-top: 2px;
  padding-bottom: 2px;
  background-color:rgba(170, 224, 217) ;
  font-weight: bold;
}
.has-problem {
  background-color: rgb(97, 209, 209);
}
.has-language {
  background-color: rgb(126, 208, 233);
}
.has-algo {
  background-color: rgba(186, 184, 189, 1);
}
.member-name{
  cursor: pointer;
}
.member-name:hover{
  font-size:18px;
}
.profileImg {
    width: 75px;
    height: 75px;
    border-radius: 75%;
}
.article-box:hover {
  box-shadow: 0 0 0px 5px rgba(62 ,171 ,111 , 1);
}

@media (max-width: 767px) {
  .top {
    margin-bottom: 30px;
  }

}
</style>