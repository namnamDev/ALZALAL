<template>
  <div class="animate__animated animate__fadeInUp my-4 main" v-if="articleDetail">
      <div class="article-box col-lg-6 col-md-10 col-sm-9 col-10">
        <div>
          <div class="title">
            {{articleDetail.articleTitle}}
          </div>
          <div class="row">
            <div class="col clickName"  >
              <span @click="clickName" class="member-name">{{articleDetail.member.name}}</span>
            </div>
            <div class="col text-end text-secondary">
             {{articleDetail.articleDate | moment("YYYY-MM-DD HH:mm:ss")}}
            </div>
          </div>
        </div>

        <div class="middle px-0">

            <div class="col text-end px-0" v-if="isMine">
              <span @click="modifyArticle" style="cursor:pointer">수정</span> | 
              <span @click="deleteArticle" style="cursor:pointer">삭제</span>
            </div>
            <div class="viewer">
              <Viewer :viewerText="articleDetail.articleContent"/>  
            </div>
        </div>

        <div class="bottom mt-3">
          <div>
            <div class="col thumbs">
              <i class="fas fa-heart" @click="clickLike" v-if="likeState"></i>
              <i class="far fa-heart" @click="clickLike" v-else></i>
              <span >
                {{likeCount}}
              </span>
            </div>
            <button class="create-comment-btn" @click="clickCreateComment">댓글쓰기</button>
            <div id="create-comment">
              <CreateComment :articleNo="articleDetail.articleNo"/>
            </div>
          </div>
          <div class="row" v-for="comment,idx in getArticleComments" :key="idx">
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
import jwt_decode from 'jwt-decode'


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
      currentPage: Number(this.Page)+1,
      articleClassNo:'',
      articleContent:'',
      articleTitle:'',
      articleNo:'',
      algo:'',
      problemSiteName:'',
      problemSiteNo:'',
      useLanguage:'',
    }
  },


  computed: {
    getArticleComments: function() {
      return this.$store.getters.getArticleComments
    },
    isMine: function() {
      const token = sessionStorage.getItem('jwt')
      let username = '';
      if (token) {
        const decoded = jwt_decode(token)
        username = decoded.name
        if (username == this.articleDetail.member.name){
          return true
        }
        else{
          return false
        }
      }
      return false
    }
  },


  mounted() { 
    this.getArticleDetail()       
    this.getCommentList()
  },

  methods: {
    clickNextBtn: function() {
      if (Number(this.Page) < this.commentCount -1){
        const page = String(Number(this.Page) + 1)
        location.href=`/articleDetail/${page}`  
      }
    },
    clickPreviousBtn: function() {
      if (Number(this.Page) > 0){
        const page = String(Number(this.Page) - 1)
        location.href=`/articleDetail/${page}`  
      }
    },
    goPage: function() {
      location.href=`/articleDetail/${this.currentPage-1}`
    },


    // 삭제 버튼
    deleteArticle: function() {
      const articleNo = localStorage.getItem('articleNo')
      this.$swal.fire({
        title: '글을 삭제하시겠습니까?',
        text: "삭제하시면 다시 복구시킬 수 없습니다.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
      }).then((result) => {
        if (result.value) {
          axios({
            method: 'delete',
            url: `${SERVER_URL}/article/article/${articleNo}`,
            headers: this.getToken(),
          })   
          .then(() =>{
            this.$router.push({name: 'timeline'})
          })
          .catch(err =>{  
            console.log(err)
          }) 
        }
      })
      
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
          this.commentCount = Math.ceil(res.data.articleDetail.commentCount/10 ,1)
          this.articleDetail = res.data.articleDetail
          this.likeState = this.articleDetail.likeState
          this.likeCount= this.articleDetail.likeCount
          console.log(this.articleDetail)
        })
        .catch(err =>{  
          console.log(err)
        })     
    },

    // 게시글 수정
    modifyArticle(){
      const articleNo = localStorage.getItem('articleNo')
      this.$router.push({name: 'modifyArticle', params:{
        articleNo: articleNo,
        }})
    },


    //댓글 리스트 불러오기
    getCommentList:function() {
      this.$store.dispatch('deleteArticleComment')
      const articleNo = localStorage.getItem('articleNo')
      axios({
          method: 'get',
          url: `${SERVER_URL}/comment/article/${articleNo}?page=${this.Page}`,
          headers: this.getToken(),
        })   
        .then(res =>{
          this.$store.dispatch('createArticleComment',res.data.articleComments)
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
      localStorage.setItem('userPk',this.articleDetail.member.no)
      this.$router.push({'name':'profilePage', params:{userPk:this.articleDetail.member.no}})
    }

  }
}

</script>

<style scoped>
#create-comment{
  display: none;
}
.article-box {
  background: white;

  box-shadow: 0 0 0px 0.7px gray;
  border-radius: 5px;
  padding: 15px 15px;
  /* height: 400px; */
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
  /* border-top: 1px solid black;
  border-bottom: 1px solid black; */
  /* box-shadow: 0 0 0px 0.7px gray; */
  /* border-radius: 5px; */
  /* padding: 15px 15px 15px 15px; */
  position: relative;
}
.bottom{
  width:100%;
  /* height:100px; */
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
  /* position: absolute; */
  text-align: end;
  bottom: 0;
}
.fa-heart{
  color: rgba(62 ,171 ,111 , 1);
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
</style>