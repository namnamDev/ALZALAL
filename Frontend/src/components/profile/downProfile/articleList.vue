<template>
  <div class="row">
      <!-- 게시글 시작-->
    <div class="boardList col-12 col-ml-12 col-lg-12" id="boardList" v-for="(item,index) in articleList" :key="index">
        <div class="feed-card col-12 col-lg-12 col-ml-12">
            <div class="contentsWrap">
                <p class="title" @click="clickArticle">{{item.articleTitle}}     <span>{{item.algo}}</span></p>
                <p>{{item.problemSite.problemSiteName}} {{item.problemSite.problemNo}}</p>                
                <div class="articleContent">{{item.articleContent}}</div>
            </div>
        </div>
    
        <div class="btn-group wrap col-12 col-lg-12 col-ml-12">
            <div class="col box1 like-comment">
              <i class="fas fa-heart me-2" v-if="item.likeState"></i>
              <i class="far fa-heart me-2" v-else></i>
              <span>{{item.likeCount}}</span>
              <i class="far fa-comment-dots mx-2"></i>
              <span >{{item.commentCount}}</span>
              <div><p class="date">{{item.articleDate}}</p></div>
            </div>
        </div>
        
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="sprial">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
    </infinite-loading>
     <!-- 게시글 끝  -->
  </div>
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL
import InfiniteLoading from 'vue-infinite-loading';
import axios from 'axios';
import jwt_decode from "jwt-decode";
const token = localStorage.getItem("jwt");
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
  
}
export default {
  components:{
    InfiniteLoading
  },
  data(){
    return{
      articleList:[],
      likeState: '',
      page:0
    }
  },
	computed: {
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    }
	},
  methods:{
    infiniteHandler($state) {
      // fetch(`${SERVER_URL}/search/article/${userpk}`+"?page=" + (this.page), {method: "get"
      //   }).then(resp => {
      //     return resp.json()
      axios({
        method: 'get',
        url: `${SERVER_URL}/search/article/${userpk}`+"?page=" + (this.page),
        headers: this.getToken
        }).then(res => {
          console.log(this.getToken)
          setTimeout(() => {
            if(res.data.articleList.length) {
              console.log(res.data.articleList)
              this.articleList = this.articleList.concat(res.data.articleList)
              $state.loaded()
              this.page += 1
              // 끝인지 판별
              if(res.data.articleList.length / 20 < 1) {
                $state.complete()
              }
            } else {
              // 끝 지정(No more data)
              $state.complete()
            }
          }, 1000)
        }).catch(err => {
          console.error(err);
        })
    },
    clickArticle: function(){
      this.$router.push({'name':'articleDetail', params:{articleNo:this.articleList.articleNo}})
    }
  }
}
</script>

<style scoped>
.fa-heart {
  color: red;
}
.boardList{
  margin-left: 30px;
}
.feed-card {
  box-sizing: content-box;
  /* box-shadow: 0 0 0 1px #ddd; */
  color: #000;
  float: left;
  border-radius: 5px;
  overflow: hidden;
  
}
.contentsWrap {
  box-sizing: border-box;
  padding: 12px;
  float: left;
}
.title {
  color:#000;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
  line-height: 1.5em;
  font-size: 25px;
  font-weight: 550;
  margin: 0 0 8px;
  white-space: normal;
}
.date {
  float: right;
  font-size: 15px;
  color:rgba(0, 0, 0, .5);
  
}
.feed-item {
  margin-bottom: 30px;
  border-bottom: 1px solid grey;
  padding-bottom: 20px;
}
.user-info, .content {
  width: calc(100% - 50px);
  float: right; 
}
.user-name {
  float: left;
}
.user-name button {
  font-weight: 600;
}
.user-name span {
   margin-left: 10px;
}
.date {
  float: right;
}
#clickBoard:hover {
  background-color:#a1d4e2;
}
#clickRequest:hover{
  background-color: #a1d4e2;
}
#clickSend:hover{
  background-color: #a1d4e2;
}
@media (max-width:577px) {
  .feed{
    margin-left:0;
  }
}
.comment{
  margin-right: 30px;
}
.articleContent {
  overflow:hidden;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  
  white-space:normal;
  }
</style>