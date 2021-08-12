<template>
  <div @click="clickArticle" class="boardList col-12 col-ml-12 col-lg-12" id="boardList">
        <div class="feed-card col-12 col-lg-12 col-ml-12">
            <p>{{this.problemSite}} {{this.problemNo}}번 문제</p>
            <div class="contentsWrap">
                <p class="title">{{this.articleTitle}}     <span class="algoList">{{this.algoList}}</span></p>
                           
                <div class="articleContent">{{this.articleContent}}</div>
            </div>
        </div>
    
        <div class="btn-group wrap col-12 col-lg-12 col-ml-12">
            <div class="col box1 like-comment">
              <i class="fas fa-heart me-2" v-if="this.likeState"></i>
              <i class="far fa-heart me-2" v-else></i>
              <span>{{this.likeCount}}</span>
              <i class="far fa-comment-dots mx-2"></i>
              <span >{{this.commentCount}}</span>
              <div><p class="date">{{this.date}}</p></div>
            </div>
        </div>
        
    </div>
</template>

<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  components:{
      
  },
  props:{
      articleContent: String,
      articleNo: Number,
      
  },
  computed:{
    Content: function() {
      return this.content
    },
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
  },
  data: function() {
    return{
      articleClass: '',
      articleTitle: '',
      date: '',
      commentCount: '',
      likeCount: '',
      likeState: '',
      memberName: '',
      memberNo: '',
      problemSite: '',
      problemNo: '',
      language: '',
      item: null,
      algoList: null,
    }
  },

  mounted() {
    axios({
        method: 'get',
        url: `${SERVER_URL}/article/article/${this.articleNo}`,
        headers: this.getToken,
      })   
      .then(res =>{
        const detail = res.data.articleDetail
        this.item = res.data
        this.articleClass= detail.articleClass,
        this.articleTitle= detail.articleTitle,
        
        this.commentCount= detail.commentCount,
        this.likeCount= detail.likeCount,
        this.likeState= detail.likeState,
        this.memberName= detail.member.name,
        this.memberNo= detail.member.no,
        this.problemSite= detail.problemSite.problemSiteName,
        this.problemNo= detail.problemSite.problemNo,
        this.language= detail.useLanguag
        this.algoList = detail.algo
        const date = res.data.articleDetail.articleDate
        this.date = this.getDate(date)
      })
      .catch(err =>{  
        console.log(err)
      })
  },
  methods: {    
    getDate: function(date) {
      const today  = new Date()
      const timeValue  = new Date(date)

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
    // 게시글 상세 정보 페이지로 이동
    clickArticle: function() {
      localStorage.setItem('articleNo', this.articleNo)

      this.$store.dispatch('createArticleDetail',this.item.articleDetail)

      // 댓글 정보 요청후 store에 저장
      axios({
        method: 'get',
        url: `${SERVER_URL}/comment/article/${this.articleNo}`,
      })   
      .then(res =>{
        this.$store.dispatch('createArticleComment',res.data.articleComments)
      })
      .catch(err =>{  
        console.log(err)
      })

      this.$router.push({name : 'articleDetail', params:{'Page':'0', articleNo:this.articleNo}})
    },   
    
  },
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
.algoList{
  font-size: 20px
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