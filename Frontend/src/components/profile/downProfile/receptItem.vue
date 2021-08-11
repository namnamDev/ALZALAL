<template>
  <div class="sendQuiz col-12 col-ml-12 col-lg-12" id="sendQuiz">
    <div class="feed-card col-12 col-lg-12 col-ml-12">
      <div class="contentsWrap">
        <span @click="clickHelpmeName">{{this.problemSiteName}}  </span>
        <span @click="clickHelpmeName">{{this.problemNo}}번 문제</span>
        <span>  {{getStatus}}</span>
        <div>
          <p @click="clickHelpmeName" class="helpmeContent">{{this.helpmeContent}}</p>
        </div>
        <div>
            <button v-if="clickok" @click="clickOk" class="btn">수락하기</button>
            <button v-if="clickno" @click="clickNo" class="btn">거절하기</button>
        </div>
        <div v-if="clickanswer">
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
      helpmeStatus:'',
      item:null,
      clickok:true,
      clickno:true,
      clickanswer:false,
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
        if(this.helpmeStatus =='H00'){
          this.clickok = true
          this.clickno = true
        }else if(this.helpmeStatus =='H01'){
          this.clickok = false
          this.clickno = false
          this.clickanswer = true
        }else if(this.helpmeStatus =='H02'){
          this.clickanswer = false
          this.clickok = false
          this.clickno = false
        }else if(this.helpmeStatus =='H03'){
          this.clickok = false
          this.clickno = false
        }
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
      clickOk: function(){
        this.clickanswer = true;
        this.clickno = false;
        this.clickok = false;
        axios({
          method: 'post',
          url:`${SERVER_URL}/helpme/state/${this.helpmeNo}`,
          data: {
            'answer':'accept'
          },
          headers: this.getToken
        }).then(res =>{
          console.log(res)
        }).catch(err =>{
          console.log(err)
        })
      },
      clickNo: function(){
        this.clickanswer = false;
        this.clickok = false;
        this.clickno = false;
        axios({
          method: 'post',
          url:`${SERVER_URL}/helpme/state/${this.helpmeNo}`,
          data: {
            'answer':'reject'
          },
          headers: this.getToken
        }).then(res =>{
          console.log(res)
        }).catch(err =>{
          console.log(err)
        })
      },
    // 게시글 상세 정보 페이지로 이동
    clickHelpmeName: function() {
      localStorage.setItem('helpmeNo', this.helpmeNo)

      this.$store.dispatch('createHelpmeDetail',this.item.helpme)

      // 댓글 정보 요청후 store에 저장
      axios({
        method: 'get',
        url: `${SERVER_URL}/comment/helpme/${this.helpmeNo}`,
      })   
      .then(res =>{
        this.$store.dispatch('createArticleComment',res.data.articleComments)
      })
      .catch(err =>{  
        console.log(err)
      })

      this.$router.push({name : 'helpmeDetail', params:{'Page':'0', helpmeNo:this.helpmeNo}})
    },
    // 문제답변하기 버튼누르면 문제풀이 글쓰기로 이동
    sendAnswer: function(){
        this.$router.push({name : 'createHelpmeAnswer',
         params:{
          helpmeNo:this.helpmeNo,
          problemSiteName:this.problemSiteName,
          problemNo: this.problemNo,
          helpmeSenderNo: this.helpmeSenderNo,
          helpmeSenderName: this.helpmeSenderName,
          helpmeReceptorNo: this.helpmeReceptorNo,
          helpmeReceptorName: this.helpmeReceptorName,
          
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