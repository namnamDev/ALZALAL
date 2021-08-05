<template>
  <div class="container">
    <div class="row my-5">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="col-lg-6 col-md-10 col-sm-9 col-11 content">

        <div class="row top mb-3">
          <div class="row title">
            {{articleTitle}}
          </div>
          <div class="row">
            <div class="col">
              {{memberName}}
            </div>
            <div class="col text-end">
             {{date | moment("YYYY-MM-DD HH:mm:ss")}}
            </div>
          </div>
        </div>

        <div class="row middle py-4 px-0">
          <div class="row mb-5">
            <div>
              <Viewer :viewerText="content"/>  
            </div>

            <div class="col thumbs mb-3">
              <i class="fas fa-heart" @click="clickLike" v-if="likeState"></i>
              <i class="far fa-heart" @click="clickLike" v-else></i>
              <span class="ms-2" >
                {{this.likeCount}}
              </span>
            </div>

          </div>
        </div>

        <div class="row bottom my-3">
          <div class="row">
            <button class="mb-4" @click="clickAlgoInput">댓글쓰기</button>
            <div id="create-comment">
              <CreateComment :articleNo="this.articleNo"/>
            </div>
          </div>
          <div class="row my-3" v-for="comment,idx in getComments" :key="idx">
            <Comment :comment="comment"/>
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

  data: function() {
    return {
      articleTitle: '',
      articleNo: '',
      content:'',
      commentCount: '',
      likeCount:'',
      likeState:'',
      memberName: '',
      memberNo:'',
      problemSite: '',
      problemNo: '',
      language: '',
      date: '',
    }
  },
  computed: {
    getComments: function() {
      return this.$store.getters.getArticleComments
    },
    // getTime: function() {
    //   const milliSeconds = new Date() - this.date
    //   const seconds = milliSeconds / 1000
    //   if (seconds < 60) return `방금 전`
    //   const minutes = seconds / 60
    //   if (minutes < 60) return `${Math.floor(minutes)}분 전`
    //   const hours = minutes / 60
    //   if (hours < 24) return `${Math.floor(hours)}시간 전`
    //   const days = hours / 24
    //   if (days < 7) return `${Math.floor(days)}일 전`
    //   const weeks = days / 7
    //   if (weeks < 5) return `${Math.floor(weeks)}주 전`
    //   const months = days / 30
    //   if (months < 12) return `${Math.floor(months)}개월 전`
    //   const years = days / 365
    //   return `${Math.floor(years)}년 전`
    // }
  },

  created() {    
    const article = this.$store.getters.getArticleDetail
    this.articleNo = article.articleNo
    this.articleTitle = article.articleTitle
    this.memberName = article.member.name
    this.date = article.articleDate
    this.content = article.articleContent
    this.likeCount = article.likeCount
    this.Comment = article.commentCount  
    this.likeState = article.likeState
  },
  methods: {
    getToken(){
      const token = localStorage.getItem('jwt')
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

    clickLike: function() {
      const articleNo = localStorage.getItem('articleNo')
      axios({
        method: 'post',
        url: `${SERVER_URL}/like/article/article/${articleNo}`,
        headers: this.getToken(),
        data :{}
      })
      .then( {
        // console.log(res)
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
</style>