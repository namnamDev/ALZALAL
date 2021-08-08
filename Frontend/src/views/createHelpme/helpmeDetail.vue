<template>
  <div class="container">
    <div class="row my-5">
      <div class="col-lg-3 col-md-2 col-sm-3 col-3"></div>
      <div class="col-lg-6 col-md-10 col-sm-9 col-6 content">
       
        <div class="row top mb-3">
          <div class="row">
            <div class="col clickName"  @click="clickName">
              
            </div>
            <div class="col text-end">
             {{helpmeDate | moment("YYYY-MM-DD HH:mm:ss")}}
            </div>
          </div>
        </div>

        <div class="row middle py-4 px-0">
           {{this.problemSiteName}} {{this.problemNo}}번 문제
          <div class="row mb-5">
            <div>
                {{this.helpmeContent}}
            </div>
            <div>
              {{this.helpmeSenderName}}님이 {{this.helpmeReceptorName}}에게 요청한 문제
            </div>
            <div>
              {{getStatus}}
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
              <CreateComment :helpmeNo="this.helpmeNo"/>
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
import CreateComment from '@/components/comment/CreateCommentEditor.vue'
import axios from 'axios';
import $ from 'jquery';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  name: 'helpmeDetail',
  props:{
    // articleno: Number
  },

  components:{
    Comment,
    CreateComment,
  },

  data: function() {
    return {
      helpmeContent: '',
      helpmeNo: '',
      content:'',
      commentCount: '',
      likeCount:'',
      likeState:'',
      problemSiteName: '',
      problemNo: '',
      language: '',
      helpmeDate: '',
      helpmeReceptorNo:'',
      helpmeReceptorName:'',
      helpmeSenderNo: '',
      helpmeSenderName: '',
      helpmeStatus:'',
    }
  },
  computed: {

    getComments: function() {
      return this.$store.getters.getArticleComments
    },
    getStatus: function(){
      let status = ''
      if(this.helpmeStatus =='H00'){
        status =  '답변대기중'
      }else if(this.helpmeStatus =='H01'){
        status =  '답변중'
      }else if(this.helpmeStatus =='H02'){
        status =  '거절됨'
      }else if(this.helpmeStatus =='H03'){
        status =  '답변완료'
      }
      return status
    }
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
    
    const helpme = this.$store.getters.getHelpmeDetail
    this.helpmeContent = helpme.helpmeContent
    this.helpmeDate = helpme.helpmeDate
    this.helpmeNo = helpme.helpmeNo
    console.log(this.helpmeNo)
    this.helpmeReceptorNo = helpme.helpmeReceptorNo.no
    this.helpmeSenderNo = helpme.helpmeSenderNo.no
    this.helpmeReceptorName = helpme.helpmeReceptorNo.name
    this.helpmeSenderName = helpme.helpmeSenderNo.name
    this.commentCount = helpme.commentCount  
    this.likeState = helpme.likeState
    this.helpmeStatus = helpme.helpmeStatus
    console.log(this.helpmeStatus)
    this.problemNo = helpme.problemSite.problemNo
    this.problemSiteName = helpme.problemSite.problemSiteName
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
      const helpmeNo = localStorage.getItem('helpmeNo')
      axios({
        method: 'post',
        url: `${SERVER_URL}/like/article/article/${helpmeNo}`,
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