<template>
  <div class="container">
    <div class="row my-5">
      <div class="col-lg-3 col-md-2 col-sm-3 col-3"></div>
      <div class="col-lg-6 col-md-10 col-sm-9 col-6 content">
       
        <div class="row top mb-5">
          <div class="row mt-5">
            <div class="col clickName"  @click="clickName">
             {{discussDate | moment("YYYY-MM-DD HH:mm:ss")}}
              
            </div>
          </div>
        </div>

        <div class="row middle py-4 px-0">
           {{this.discussCompName}} {{this.disscussCompProblem}} 문제
          <div class="row mb-5">
              {{discussCompHostName}}
          </div>
        </div>

        <div class="row bottom my-3">
          <div class="row">
            <button class="mb-4" @click="clickAlgoInput">댓글쓰기</button>
            <div id="create-comment">
              <CreateComment :articleNo="this.discussionNo"/>
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
import CreateComment from '@/views/debate/createDebateComment.vue'
import $ from 'jquery';
import Vue from "vue";
import vueMoment from "vue-moment";
Vue.use(vueMoment);
export default {
  name: 'debateDetail',
  props:{
    discussionNo: Number
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
    }
  },
  computed: {

    getComments: function() {
      return this.$store.getters.getDebateComments
    },
  },

  created() {        
    const debate = this.$store.getters.getDebateDetail
    console.log(debate)
    this.commentCount = debate.commentCount
    this.discussCompHostNo = debate.discussCompHostNo.no
    this.discussCompHostName = debate.discussCompHostNo.name
    
    this.discussCompName = debate.discussCompName
    this.discussDate = debate.discussDate
    this.disscussCompProblem = debate.disscussCompProblem
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