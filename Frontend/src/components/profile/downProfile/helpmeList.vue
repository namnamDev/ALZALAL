<template>
  <div class="row">
  <!-- 보낸요청 시작 -->
  <div class="sendQuiz col-12 col-ml-12 col-lg-12" id="sendQuiz" v-for="(item,index) in helpmeSendList" :key="index">
    <div class="feed-card col-10 col-lg-10 col-ml-10">
      <div class="contentsWrap">
        <span>{{item.problemSite.problemSiteName}}  </span>
        <span>{{item.problemSite.problemNo}}번 문제</span>
        <div>
          <p class="helpmeContent">{{item.helpmeContent}}</p>
        </div>
        <div class="wrap">
          <p class="comment"><i class="fas fa-comment"></i>  {{item.commentCount}}</p>
          <p class="date">{{item.helpmeDate}}</p>
        </div>
      </div>
    </div>
   
    <div class="btn-group wrap  col-2 col-lg-2 col-ml-2">
      <div class="like likeScrap">
        <svg
          class="svg-inline--fa fa-heart fa-w-16 icon full"
          aria-hidden="true"
          data-prefix="fas"
          data-icon="heart"
          role="img"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
          data-fa-i2svg
        >
          <path
            fill="currentColor"
            d="M462.3 62.6C407.5 15.9 326 24.3 275.7 76.2L256 96.5l-19.7-20.3C186.1 24.3 104.5 15.9 49.7 62.6c-62.8 53.6-66.1 149.8-9.9 207.9l193.5 199.8c12.5 12.9 32.8 12.9 45.3 0l193.5-199.8c56.3-58.1 53-154.3-9.8-207.9z"
          />
        </svg>
        <i class="fas fa-heart icon full"></i>
        <svg
          class="svg-inline--fa fa-heart fa-w-16 icon empty"
          aria-hidden="true"
          data-prefix="far"
          data-icon="heart"
          role="img"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
          data-fa-i2svg
        >
          <path
            fill="currentColor"
            d="M458.4 64.3C400.6 15.7 311.3 23 256 79.3 200.7 23 111.4 15.6 53.6 64.3-21.6 127.6-10.6 230.8 43 285.5l175.4 178.7c10 10.2 23.4 15.9 37.6 15.9 14.3 0 27.6-5.6 37.6-15.8L469 285.6c53.5-54.7 64.7-157.9-10.6-221.3zm-23.6 187.5L259.4 430.5c-2.4 2.4-4.4 2.4-6.8 0L77.2 251.8c-36.5-37.2-43.9-107.6 7.3-150.7 38.9-32.7 98.9-27.8 136.5 10.5l35 35.7 35-35.7c37.8-38.5 97.8-43.2 136.5-10.6 51.1 43.1 43.5 113.9 7.3 150.8z"
          />
        </svg>
        <i class="far fa-heart icon empty"></i>
        <!-- 0 -->
      </div>
    </div>
  </div>
  <infinite-loading @infinite="infiniteHandler" spinner="sprial">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
    </infinite-loading>
  <!-- 보낸요청 끝 -->
  </div>
</template>

<script>
import jwt_decode from "jwt-decode";
import InfiniteLoading from 'vue-infinite-loading';
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
const token = localStorage.getItem('jwt')
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
  console.log(userpk)
}
export default {
  components:{
    InfiniteLoading
  },
  data(){
    return{
      helpmeSendList: [],
      page: 0
        // "helpmeNo": '',
        // "problemSite": {//문제 정보
        //   "problemSiteName": '',
        //   "problemNo": '',
        //   "problemSiteLink": ''
        // },
        // "helpmeSenderNo": {//요청유저
        //   "name": '',
        //   "no": ''
        // },
        // "helpmeReceptorNo": {//대상유저
        //   "name": '',
        //   "no": ''
        // },
        // "helpmeContent": '',
        // "helpmeStatus": '',
        // "helpmeDate": '',
        // "likeCount": '',
        // "likeState": '',
        // "commentCount": ''
    }
  },
  methods:{
    infiniteHandler($state) {
      //fetch(`${SERVER_URL}/helpme/sendlist`+"?page=" + (this.page), {method: "get"},{headers:this.getToken})
      axios({
        method: 'get',
        url: `${SERVER_URL}/helpme/sendlist`+"?page=" + (this.page),
        headers: this.getToken
      // })
      //   .then(resp => {
      //     return resp.json()
        }).then(res => {
          setTimeout(() => {
            console.log(res.data.helpmeSendList)
            console.log(res.data.helpmeSendList.length)
            if(res.data.helpmeSendList.length) {
              this.helpmeSendList = this.helpmeSendList.concat(res.data.helpmeSendList)
              console.log(this.helpmeSendList)
              $state.loaded()
              this.page += 1
              console.log("after", this.helpmeSendList.length, this.page)
              // 끝인지 판별
              if(res.data.helpmeSendList.length / 20 < 1) {
                $state.complete()
              }
            } else {
              // 끝 지정(No more data)
              $state.complete()
            }
          }, 1000)
        }).catch(err => {
          console.error(err);
        })
    }
  },
	computed: {
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
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