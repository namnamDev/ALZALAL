<template>
  <div class="container">

    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1">        
      </div>

      <div class="col-lg-6 col-md-10 col-sm-9 col-10">

        <div class="row top">
          <div class="col">
            <div v-if="isOneAlgo">
              <div v-if="articleList"> 
                #{{articleList[0].algo[0]}}
              </div>
            </div>
          </div>
        </div>

        <div class="row middle mb-5">
          <div class="col">
            <div style="display:inline;" class="me-3" v-if="followingNumber">
              팔로잉  {{followingNumber}}
            </div>
            <div style="display:inline;" class="me-3" v-else>
              팔로잉  0
            </div>
            <div style="display:inline;" class="me-3" v-if="articleNumber">
              게시글  {{articleNumber}}
            </div>
            <div style="display:inline;" class="me-3" v-else>
              게시글  0
            </div>
          </div>
        </div>

        <div class="row bottom my-3" v-for="item,idx in articleList" :key="idx">
          <div class="col-2 pt-4 member-name">
            {{item.member.name}}
          </div>
          <div class="col article-content" @click="articleDetail(item.articleNo)">
            <div class="row article-title">
              {{item.articleTitle}}
            </div>
            <div class="row">
              <div class="col p-0">
                <span class="hashtag" v-if="item.articleClass=='A01'">문제풀이</span>
                <span class="hashtag" v-else>QnA</span>
                <span class="hashtag" v-for="alg,idx in item.algo" :key="idx">{{alg}}</span>
                <span class="hashtag">{{item.useLanguage}}</span>
              </div>
            </div>
            <div class="row">
              <div class="col p-0">
                {{item.articleDate}}                
              </div>
              <div class="col text-end">
                <i class="fas fa-heart me-2" v-if="item.likeState"></i>
                <i class="far fa-heart me-2" v-else></i>
                <span>{{item.likeCount}}</span>
                <i class="far fa-comment-dots mx-2"></i>
                <span >{{item.commentCount}}</span>
              </div>
            </div>
          </div>

        </div>

      </div>

    </div>


  </div>
</template>

// <script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  data() {
    return{
      isOneAlgo: true,
      includeAlgo: '',
      excludeAlgo: '',
      language: '',
      followingNumber: '',
      articleNumber: '',
      articleList: '',
    }
  },
  computed: {
    getArticle: function() {
      return this.$store.getters.getSearchArticle
    }
  },

  created() {
    console.log('1234')
    const data = this.getArticle

    this.language = data.language
    this.includeAlgo = data.includeAlgo
    this.excludeAlgo = data.excludeAlgo

    axios({
      method: 'get',
      url: `${SERVER_URL}/search/article/algorithm`,
      headers: this.getToken(),
      params: {
        language: this.language,
        and: this.includeAlgo[0]
      }
    })
    .then(res=>{
      this.articleList = res.data.aricleList
      this.followingNumber = res.data.followInfo.followingNumber
      this.articleNumber = res.data.followInfo.articleNumber
    })
    .catch(err=>{
      console.log(err)
    })
  },

  methods: {
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
    articleDetail(articleNo){
      localStorage.setItem('articleNo', articleNo)
      axios({
        method: 'get',
        url: `${SERVER_URL}/article/article/${articleNo}`,
        headers: this.getToken,
      })
      .then(res=>{
        const data = res.data.articleDetail
        this.$store.dispatch('createArticleDetail',data)
        console.log(res)
      })
      .then(err=>{
        console.log(err)
      })

      // // 댓글 정보 요청후 store에 저장
      axios({
        method: 'get',
        url: `${SERVER_URL}/comment/article/${articleNo}`,
      })   
      .then(res =>{
        this.$store.dispatch('createArticleComment',res.data.articleComments)
        // this.$router.push({name : 'articleDetail'})
        location.href = 'articleDetail'
      })
      .catch(err =>{  
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
.top{
  margin-top: 150px;
  font-size: 50px;
  font-weight: bold;
}
.member-name{
  font-size:20px;
}
.article-content{
  -webkit-box-shadow : 0 0px 10px rgba(0, 0, 0, 0.08);
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.12);
  width: 100%;
  padding: 10px 20px;
  border-radius: 4px;
}
.article-content:hover{
  -webkit-box-shadow: 0 0px 20px rgba(161, 212, 226, 0.6);
  box-shadow: 0 0px 20px rgba(161, 212, 226, 0.6);
  transform: scale(1.1);
  cursor: pointer;
}
.article-title{
  font-size: 30px;  
}
.hashtag{
  display: inline-block;
  font-size: 13px;
  border-radius: 3px;
  background-color: rgba(221,223,230,1);
  margin-right:3px;
  padding: 1px 3px;
  margin-bottom: 4px;
}
.fa-heart{
  color: red;
}
</style>