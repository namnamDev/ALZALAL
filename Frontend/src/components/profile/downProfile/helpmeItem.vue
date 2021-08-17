<template>
  <div class="animate__animated animate__fadeInUp my-4 main">
    <div class="article-box col-12 col-ml-12 col-lg-12" id="sendQuiz">
      <div class="feed-card col-12 col-lg-12 col-ml-12">
        <span class="status">{{getStatus}}</span>
        <div class="contentsWrap" @click="clickHelpmeName">
          <span class="probleminfo">{{this.problemSiteName}}  </span>
          <span class="probleminfo">{{this.problemNo}}번 문제</span>
          
          
          <div>
            <p class="helpmeContent">{{this.helpmeContent}}</p>
          </div>
          <div class="btn-group wrap">
              <div class="col box1 like-comment">
                <i class="fas fa-heart me-2" v-if="this.likeState"></i>
                <i class="far fa-heart me-2" v-else></i>
                <span>{{this.likeCount}}</span>
                <i class="far fa-comment-dots mx-2"></i>
                <span >{{this.commentCount}}</span>
                <!-- <div><p class="date">{{this.helpmeDate}}</p></div> -->
              </div>
          </div>
          
        </div>
        <div><p class="date">{{this.helpmeDate}}</p></div>
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
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
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
      helpmeStatus:'',
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
        const detail = res.data.helpme
         this.item = res.data
        this.helpmeSenderNo= detail.helpmeSenderNo.no,
        this.helpmeSenderName= detail.helpmeSenderNo.name,
        this.commentCount= detail.commentCount,
        this.likeCount= detail.likeCount,
        this.likeState= detail.likeState,
        this.problemSiteName= detail.problemSite.problemSiteName,
        this.problemNo= detail.problemSite.problemNo,
        this.helpmeReceptorNo= detail.helpmeReceptorNo.no,
        this.helpmeReceptorName= detail.helpmeReceptorNo.name,
        this.helpmeStatus= detail.helpmeStatus
        const date = res.data.helpme.helpmeDate
        this.helpmeDate = this.getDate(date)
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

      // 댓글 리스트 불러오기
      axios({
        method: 'get',
        url: `${SERVER_URL}/comment/helpme/${this.helpmeNo}`,
      })   
      .then(res =>{
        console.log(res)
        this.$store.dispatch('createHelpmeComment',res.data.articleComments)
      })
      .catch(err =>{  
        console.log(err)
      })

      this.$router.push({name : 'helpmeDetail', params:{'Page':'0', helpmeNo:this.helpmeNo}})
    },   
    
  },
}
</script>

<style scoped>
.feed-card {
    box-sizing: content-box;
    /* box-shadow: 0 0 0 1px #ddd; */
    color: #000;
    
    border-radius: 5px;
    overflow: hidden;
    
}
@media (max-width:692px) {
  .article-box {
  background: white;
  width: 400px;
  box-shadow: 0 0 0px 0.7px gray;
  border-radius: 5px;
  padding: 15px 15px;
  /* height: 400px; */
  cursor: pointer;
}
}
 .contentsWrap {
        box-sizing: border-box;
        padding: 12px;

        cursor:pointer
 }
 .probleminfo{
   font-size: 20px;
   font-weight: 500;
 }
 .status{
   font-weight: 550;
   float: right;
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
.date {
  float: right;
  font-size: 15px;
  font-weight: 550;
}
.article-box {
  background: white;

  box-shadow: 0 0 0px 0.7px gray;
  border-radius: 5px;
  padding: 15px 15px;
  /* height: 400px; */
  cursor: pointer;
}
.article-box:hover {
  box-shadow: 0 0 0px 5px rgba(62 ,171 ,111 , 1);
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