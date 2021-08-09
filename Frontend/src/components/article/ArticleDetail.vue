<template>

  <div class="container" v-if="articleDetail">
    <div class="row my-5">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="col-lg-6 col-md-10 col-sm-9 col-11 content">

        <div class="row top mb-3">
          <div class="row title">
            {{articleDetail.articleTitle}}
          </div>
          <div class="row">
            <div class="col clickName"  @click="clickName">
              {{articleDetail.member.name}}
            </div>
            <div class="col text-end">
             {{articleDetail.articleDate | moment("YYYY-MM-DD HH:mm:ss")}}
            </div>
          </div>
        </div>

        <div class="row middle py-4 px-0">
          <div class="row mb-5">
            <div>
              <Viewer :viewerText="articleDetail.articleContent"/>  
            </div>

            <div class="col thumbs mb-3">
              <i class="fas fa-heart" @click="clickLike" v-if="likeState"></i>
              <i class="far fa-heart" @click="clickLike" v-else></i>
              <span class="ms-2" >
                {{likeCount}}
              </span>
            </div>

          </div>
        </div>

        <div class="row bottom my-3">
          <div class="row">
            <button class="mb-4 create-comment-btn" @click="clickCreateComment">댓글쓰기</button>
            <div id="create-comment">
              <CreateComment :articleNo="articleDetail.articleNo"/>
            </div>
          </div>
          <div class="row">
            <div class="col">
              총 {{commentCount}}개의 댓글이 있습니다.
            </div>
          </div>
          <div class="row my-3" v-for="comment,idx in commentList" :key="idx">
            <Comment :comment="comment"/>
          </div>
          <div class="row">
            <div class="col ">
              <span class="mx-4" @click="click1">1</span>
              <span class="mx-4" @click="click2">2</span>
              <span class="mx-4">3</span>
            </div>
          </div>
        </div>
      </div>
    
    </div>
  </div>
</template>

<script>
import Comment from '@/components/comment/CommentItem.vue'
import Viewer from '@/components/article/Viewer.vue'
import CreateComment from '@/components/comment/CreateCommentEditor.vue'
import $ from 'jquery';
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'ArticleDetail',

  components:{
    Comment,
    Viewer,
    CreateComment,    
  },
  props: {
    Page: String,
  },

  data: function() {
    return {
      articleDetail: '',
      likeState:'',
      commentCount: 0,
      commentList: null,
    }
  },

  mounted() { 
    this.getArticleDetail()       
    this.getCommentList()
    console.log('this.Page : ',this.Page)
  },

  methods: {
    // 몇시간전, 몇분전 표기
    getDate: function(year,month,day,hour,min,sec) {
      const today  = new Date()
      const timeValue  = new Date(year,month,day,hour,min,sec)

      const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
      if (betweenTime < 1) return '방금전';
      if (betweenTime < 60) {
          return `${betweenTime}분전`;
      }

      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
          return `${betweenTimeHour}시간전`;
      }

      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
          return `${betweenTimeDay}일전`;
      }

      return `${Math.floor(betweenTimeDay / 365)}년전`;

    },
    // 게시글 상세정보 불러오기
    getArticleDetail() {
      const articleNo = localStorage.getItem('articleNo')
      axios({
          method: 'get',
          url: `${SERVER_URL}/article/article/${articleNo}`,
          headers: this.getToken(),
        })   
        .then(res =>{
          const date = res.data.articleDetail.articleDate
          const year = date.substr(0,4)
          const month = date.substr(5,2)
          const day = date.substr(8,2)
          const hour = date.substr(11,2)
          const min = date.substr(14,2)
          const sec = date.substr(17,2)
          
          this.getDate(year,month,day,hour,min,sec)

          this.commentCount = res.data.articleDetail.commentCount
          this.articleDetail = res.data.articleDetail
          this.likeState = this.articleDetail.likeState
          this.likeCount= this.articleDetail.likeCount
        })
        .catch(err =>{  
          console.log(err)
        })     
    },

    //댓글 리스트 불러오기
    getCommentList:function() {
      const articleNo = localStorage.getItem('articleNo')
      console.log('asdfasd',this.Page)
      axios({
          method: 'get',
          url: `${SERVER_URL}/comment/article/${articleNo}?page=${this.Page}`,
          headers: this.getToken(),
        })   
        .then(res =>{
          this.commentList = res.data.articleComments
          this.$store.dispatch('createArticleComment',res.data.articleComments)
        })
        .catch(err =>{  
          console.log(err)
        }) 
    },

    click1() {
      location.href="/articleDetail/0"
    },
    click2() {
      location.href="/articleDetail/1"
    },
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },

    //댓글 작성하기
    clickCreateComment() {

      var commentForm = $('#create-comment')
 
      // commentForm 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
      if( commentForm.is(":visible") ){
          commentForm.slideUp(700);
      }else{
          commentForm.slideDown(1000);
      }
    },
    // 게시글 좋아요 누르기
    clickLike: function() {
      const articleNo = localStorage.getItem('articleNo')
      axios({
        method: 'post',
        url: `${SERVER_URL}/like/article/article/${articleNo}`,
        headers: this.getToken(),
        data :{}
      })
      .then(res=> {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
      if (this.likeState) {
        this.likeState = false
        this.likeCount -= 1
      }
      else{
        this.likeState = true
        this.likeCount += 1
      }
    },
    clickName: function(){
      localStorage.setItem('userPk',this.memberNo)
      this.$router.push({'name':'profilePage', params:{userPk:this.memberNo}})
    }

  }
}

</script>

<style scoped>
#create-comment{
  display: none;
}
.show{
  display: block !important;
}

.container{
  margin-top: 100px;
}
.top{
  width:100%;
  height:100px;
  /* border:1px solid black; */
}
.middle{
  width:100%;
  /* height:400px; */
  /* border:1px solid black; */
  border-top: 1px solid black;
  border-bottom: 1px solid black;
  position: relative;
}
.bottom{
  width:100%;
  height:100px;
  /* border:1px solid black; */
  position: relative;
}
.title{
  font-size:40px;
}
.fa-thumbs-up{
  font-size: 20px;
}
.thumbs{
  position: absolute;
  text-align: end;
  bottom: 0;
}
.fa-heart{
  color: red;
  cursor: pointer;
}
button{
  width: 120px;
  height: 30px;
}
.clickName{
  cursor: pointer;
}
.create-comment-btn{
  background-color: rgb(86, 149, 233);
  border-style: none;
  border-radius: 3px;
  color:rgb(255, 255, 255);
  font-weight: bold;
}
</style>