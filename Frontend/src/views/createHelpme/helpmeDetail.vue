<template>
  <div class="container">
    <div class="row my-5">
      <div class="col-lg-3 col-md-2 col-sm-3 col-3"></div>
      <div class="col-lg-6 col-md-10 col-sm-9 col-6 content">
       
        <div class="row top mb-5">
          <div class="row mt-5">
            <div class="col clickName"  @click="clickName">   
            {{helpmeDate | moment("YYYY-MM-DD HH:mm:ss")}}   
             <div><p class="status">{{getStatus}}</p></div>     
              <div class="fromto">
                <span class="username">{{this.helpmeSenderName}}</span>님이 <span class="username">{{this.helpmeReceptorName}}</span>님에게 요청한 문제
              </div>
               {{this.problemSiteName}} {{this.problemNo}}번 문제
            </div>
          </div>
        </div>

        <div class="row middle py-4 px-0">
          <div class="row mb-5">
            <div>
                {{this.helpmeContent}}
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
          <div class="row mb-5 py-5">
            <div class="col text-center">
              <span class="previous-btn" @click="clickPreviousBtn">previous</span>
              <!-- <span class="mx-4" @click="click1">1</span>/ -->
              <input type="text" v-model="currentPage" class="current-page" @keyup.enter="goPage">
              /<span class="mx-4">{{commentCount}}</span>
              <span class="next-btn" @click="clickNextBtn">next</span>
            </div>
          </div>
        </div>
      </div>
    
    </div>
  </div>
</template>

<script>
import Comment from '@/components/comment/CommentHelpmeItem.vue'
import CreateComment from '@/views/createHelpme/createHelpmeComment.vue'

import Vue from "vue";
import vueMoment from "vue-moment";
import axios from 'axios';
import $ from 'jquery';
Vue.use(vueMoment);

const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  name: 'helpmeDetail',
  props:{
    // articleno: Number
    Page: String,
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
      currentPage: Number(this.Page)+1,
    }
  },
  computed: {

    getComments: function() {
      return this.$store.getters.getHelpmeComments
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
    clickNextBtn: function() {
      if (Number(this.Page) < this.commentCount -1){
        const page = String(Number(this.Page) + 1)
        location.href=`/helpmeDetail/${page}`  
      }
    },
    clickPreviousBtn: function() {
      if (Number(this.Page) > 0){
        const page = String(Number(this.Page) - 1)
        location.href=`/helpmeDetail/${page}`  
      }
    },
    goPage: function() {
      location.href=`/helpmeDetail/${this.currentPage-1}`
    },
    getCommentList:function() {
      this.$store.dispatch('deleteArticleComment')
      const articleNo = localStorage.getItem('articleNo')
      axios({
          method: 'get',
          url: `${SERVER_URL}/comment/helpme/${articleNo}?page=${this.Page}`,
          headers: this.getToken(),
        })   
        .then(res =>{
          this.$store.dispatch('createHelpmeComment',res.data.articleComments)
        })
        .catch(err =>{  
          console.log(err)
        }) 
    },

    //댓글 작성하기
    clickCreateComment() {
      const token = sessionStorage.getItem('jwt')
      if(!token){
        this.$swal.fire({          
          text: "로그인 후 이용해주세요.",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: '로그인',
          cancelButtonText: '취소'
        }).then((result) => {
          if (result.value) {
            this.$router.push({'name':'login'})
          }
        })
      }

      var commentForm = $('#create-comment')
 
      // commentForm 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
      if( commentForm.is(":visible") ){
          commentForm.slideUp(700);
      }else{
          commentForm.slideDown(1000);
      }
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
    clickLike: function() {
      const helpmeNo = localStorage.getItem('helpmeNo')
      axios({
        method: 'post',
        url: `${SERVER_URL}/like/helpme/article/${helpmeNo}`,
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
.top{
  width:100%;
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
.fromto{
  font-size: 20px;
}
.username{
  font-weight: 550;
}
.status{
  font-weight: 550;
}
</style>