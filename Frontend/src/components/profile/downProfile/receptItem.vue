<template>
  <div class="sendQuiz col-12 col-ml-12 col-lg-12" id="sendQuiz">
    <div class="feed-card col-12 col-lg-12 col-ml-12">
      <div class="contentsWrap">
        <span @click="clickHelpmeName">{{this.problemSiteName}}  </span>
        <span @click="clickHelpmeName">{{this.problemNo}}번 문제</span>
        <div>
          <p @click="clickHelpmeName" class="helpmeContent">{{this.helpmeContent}}</p>
        </div>
        <div>
           <button @click="sendAnswer" class="btn">답변하기</button>
        </div>
        <div class="btn-group wrap col-12 col-lg-12 col-ml-12">
            <div class="col box1 like-comment">
              <i class="fas fa-heart me-2" v-if="this.likeState"></i>
              <i class="far fa-heart me-2" v-else></i>
              <span>{{this.likeCount}}</span>
              <i class="far fa-comment-dots mx-2"></i>
              <span >{{this.commentCount}}</span>
              <div><p class="date">{{this.helpmeDate}}</p></div>
            </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
    props:{
        helpmeNo:Number,
        helpmeContent:String,
    },
      computed:{
    Content: function() {
      return this.content
    },
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
  },
  data: function() {
    return{
      helpmeSenderNo: '',
      helpmeSenderName: '',
      helpmeDate: '',
      commentCount: '',
      likeCount: '',
      likeState: '',
      problemSiteName: '',
      problemNo: '',
      helpmeReceptorNo: '',
      helpmeReceptorName: '',
      item:null
    }
  },
   mounted() {
    axios({
        method: 'get',
        url: `${SERVER_URL}/helpme/${this.helpmeNo}`,
        headers: this.getToken,
      })   
      .then(res =>{
          console.log(res.data)
        // const detail = res.data.articleDetail
         this.item = res.data
        // this.articleClass= detail.articleClass,
        // this.articleTitle= detail.articleTitle,
        // this.date= detail.articleDate,
        // this.commentCount= detail.commentCount,
        // this.likeCount= detail.likeCount,
        // this.likeState= detail.likeState,
        // this.memberName= detail.member.name,
        // this.memberNo= detail.member.no,
        // this.problemSite= detail.problemSite.problemSiteName,
        // this.problemNo= detail.problemSite.problemNo,
        // this.language= detail.useLanguage
        // this.algoList = detail.algo
      })
      .catch(err =>{  
        console.log(err)
      })
  },
    methods: {    
    // 게시글 상세 정보 페이지로 이동
    clickHelpmeName: function() {
      localStorage.setItem('helpmeNo', this.helpmeNo)

      this.$store.dispatch('createHelpmeDetail',this.item.helpme)

      // 댓글 정보 요청후 store에 저장
      axios({
        method: 'get',
        url: `${SERVER_URL}/comment/article/${this.helpmeNo}`,
      })   
      .then(res =>{
        this.$store.dispatch('createArticleComment',res.data.articleComments)
      })
      .catch(err =>{  
        console.log(err)
      })

      this.$router.push({name : 'helpmeDetail', params:{helpmeNo:this.helpmeNo}})
    },
    // 문제답변하기 버튼누르면 qna 글쓰기로 이동
    sendAnswer: function(){
        this.$router.push({name : 'createArticle',
         params:{helpmeNo:this.helpmeNo,
         problemSiteName:this.problemSiteName,
         problemNo: this.problemNo
         }
         })
    }   
    
  },
}
</script>

<style scoped>
.nav-link {
  cursor: pointer;
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
        cursor:pointer
 }
 .title {
    color:#000;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    word-wrap:break-word; 
    line-height: 1.5em;
    font-size: 15px;
    margin: 0 0 8px;
    white-space: normal;
}
.date {
  float: right;
  font-size: 10px;
  color:rgba(0, 0, 0, .5);
}
.feed-item {
    margin-bottom: 30px;
    border-bottom: 1px solid grey;
    padding-bottom: 20px;
}
.profile-image {
    float: left;
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
.profileImg{
  width: 100px;
  height: 100%;
  box-sizing: border-box;
  float:left;
  border:1px solid grey;
  border-radius: 3px;
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
.helpmeContent{
  overflow:hidden;
  text-overflow:ellipsis;
  white-space:nowrap;
}
</style>