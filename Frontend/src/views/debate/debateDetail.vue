<template>
  <div class="animate__animated animate__fadeInUp my-4 main">
    <div class="article-box col-lg-6 col-md-10 col-sm-9 col-10">
      <div>
        <div class="title">
           {{this.discussCompName}} {{this.disscussCompProblem}} 문제
        </div>
        <div class="text-end">
          {{discussCompHostName}}
        </div>
        <div class="row top mb-5 text-end text-secondary">
            <div class="col clickName"  @click="clickName">
              {{discussDate | moment("YYYY-MM-DD HH:mm:ss")}}
            </div>
        </div>
      </div>
        <div class="bottom my-3">
          <div class="">
          </div>
          <div class="row" v-for="comment,idx in getComments" :key="idx">
            <Comment :comment="comment"/>
          </div>
         
          <div class="row mb-1 my-1">
            <div class="col text-center">
              <span class="previous-btn" @click="clickPreviousBtn">previous</span>
              <input type="text" v-model="currentPage" class="current-page" @keyup.enter="goPage">
              /<span class="mx-4">{{commentCount}}</span>
              <span class="next-btn" @click="clickNextBtn">next</span>
            </div>
          </div>
          <button class="my-3 create-comment-btn" @click="clickAlgoInput">댓글쓰기</button>
          <div id="create-comment">
            <CreateComment :articleNo="this.discussionNo"/>
          </div>
        </div>
      </div>
    

  </div>
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL
import axios from 'axios';
import Comment from '@/components/comment/CommentDiscussionItem.vue'
import CreateComment from '@/views/debate/createDebateComment.vue'
import $ from 'jquery';
import Vue from "vue";
import vueMoment from "vue-moment";
Vue.use(vueMoment);
export default {
  name: 'debateDetail',
  props:{
    Page: String,
  },

  components:{
    Comment,
    CreateComment,

  },

  data: function() {
    return {
        commentCount:'',
        discussCompHostNo:'',
        discussCompHostName:'',
        discussCompName:'',
        discussDate:'',
        disscussCompProblem:'',
        currentPage: Number(this.Page)+1,
        discussionNo:'',
        comments:[],
        socketSubscription:null,
    }
  },
  computed: {

    getComments: function() {
      return this.comments;
    },
  },

  created() { 
    this.getDiscussDetail()       
    this.getCommentList()
  },
  mounted(){
    let stompClient=this.$store.getters.getSocketConnection;
    
    if(stompClient!=null){
      //app.vue에서 소켓 연결 하기전에 여기서 가져다 쓰면 에러떠서 0.5초 기다려줌
      let tmp = setInterval(() => {
        this.socketSubscription=stompClient.subscribe("/discuss/"+this.discussionNo, this.onMessageReceived);
        clearInterval(tmp);
      }, 500); 
    }
  },
  destroyed(){
    this.socketSubscription.unsubscribe();
  },
  methods: {
    clickNextBtn: function() {
      if (Number(this.Page) < this.commentCount -1){
        const page = String(Number(this.Page) + 1)
        location.href=`/debate/debateDetail/${page}`  
      }
    },
    clickPreviousBtn: function() {
      if (Number(this.Page) > 0){
        const page = String(Number(this.Page) - 1)
        location.href=`/debate/debateDetail/${page}`  
      }
    },
    goPage: function() {
      location.href=`/debate/debateDetail/${this.currentPage-1}`
    },
    //게시글 상세정보 가져오기
    getDiscussDetail(){
      const discussionNo = localStorage.getItem('discussionNo');
      this.discussionNo=discussionNo;
      axios({
        method: 'get',
        url: `${SERVER_URL}/article/discussion/${discussionNo}`,
        headers: this.getToken(),
      })   
      .then(res =>{
        const detail = res.data.articleDetail
        this.commentCount = Math.ceil(detail.commentCount/50 ,1)
        this.discussCompHostNo = detail.discussCompHostNo.no
        this.discussCompHostName = detail.discussCompHostNo.name
        
        this.discussCompName = detail.discussCompName
        this.discussDate = detail.discussDate
        this.disscussCompProblem = detail.disscussCompProblem
      })
      .catch(err =>{  
        console.log(err)
      })
    },
    //댓글 리스트 불러오기
    getCommentList:function() {
      this.$store.dispatch('deleteArticleComment')
      const discussionNo = localStorage.getItem('discussionNo')
      axios({
          method: 'get',
          url: `${SERVER_URL}/comment/discussion/${discussionNo}?page=${this.Page}`,
          headers: this.getToken(),
        })   
        .then(res =>{
          this.comments=res.data.articleComments;
          // this.$store.dispatch('createDebateComment',res.data.articleComments)
        })
        .catch(err =>{  
          console.log(err)
        }) 
    },
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
    clickAlgoInput() {
      // document.getElementById("create-comment").classList.toggle("show");

      var commentForm = $('#create-comment')
 
      // commentForm 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
      if( commentForm.is(":visible") ){
          commentForm.slideUp(700);
      }else{
          commentForm.slideDown(1000);
      }
    },

    clickName: function(){
      localStorage.setItem('userPk',this.memberNo)
      this.$router.push({'name':'profilePage', params:{userPk:this.memberNo}})
    },
    onMessageReceived:function (payload) {
      let discussComment = JSON.parse(payload.body);
      //마지막 페이지에서만 댓글 추가되게
      if(this.Page==this.commentCount-1){
        this.comments.push(discussComment);
      }
    },

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
.title{
  font-size:40px;
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

.date {
  float: right;
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
button{
  width: 120px;
  height: 30px;
}
.clickName{
  cursor: pointer;
}
.create-comment-btn{
  background-color: rgba(62 ,171 ,111 , 1);
  border-style: none;
  border-radius: 3px;
  color:rgb(255, 255, 255);
  font-weight: bold;
}
.current-page{
  width: 30px;
  height:23px;
  margin-left:10px;
  margin-right:15px;
  text-align: center;
}
.previous-btn{
  cursor: pointer;
}
.next-btn{
  cursor: pointer;
}
.member-name{
  cursor:pointer;
}
.member-name:hover{
  font-size:18px;
}

.article-box {
  background: white;

  box-shadow: 0 0 0px 0.7px gray;
  border-radius: 5px;
  padding: 15px 15px;
  /* height: 400px; */
}
</style>