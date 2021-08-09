<template>
    <div>
      <div class="follow" v-for="item,index in algo" :key="index">
        <div class="user col-10">
            <span class="problem">
            {{item}}
            </span>
            <span class="followBtn">
              <algoFollowBtn :algo="item"/>
            </span>
        </div>
      </div>
      <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
      </infinite-loading>
    </div>
</template>

<script>
import algoFollowBtn from '@/components/profile/topProfile/follow/algofollow/algoFollowBtn.vue'
import jwt_decode from "jwt-decode";
import InfiniteLoading from 'vue-infinite-loading';
//import axios from 'axios';
const token = localStorage.getItem("jwt");
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
  
}
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  components:{
    algoFollowBtn,
    InfiniteLoading
  },
  data(){
    return {
      algo: [],
      page:0
    }
  }, 
  methods:{
    infiniteHandler($state) {
        fetch(`${SERVER_URL}/profile/${userpk}/algofollowings`+"?page=" + (this.page), {method: "get"}).then(resp => {
          return resp.json()
        }).then(data => {
          setTimeout(() => {
            if(data.length) {
              this.algo = this.algo.concat(data)
              $state.loaded()
              this.page += 1
             // console.log("after", this.algo.length, this.page)
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면 
              if(data.length / 10 < 1) {
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
       },
  }
  
}
</script>

<style scoped>
.follow {
  display: flex;
}
.nav-link{
  font-weight: 700;
}
.user{
  margin-top: 30px;
  font-size: 22px;
  font-weight: 700;
  margin-left: 100px;
  
}
.problem{
  font-weight: 550;
  font-size: 22px;
}
.followBtn {
  border-radius: 10%;
  border: solid 0.5px skyblue;
  
  width: 30%;
  margin-left: 30px;
}
@media (max-width:768px) {
  .user{
  margin-top: 30px;
  font-size: 15px;
  font-weight: 700;
  margin-left: 30px;
  
}
  .followBtn {
    border-radius: 10%;
    border: solid 0.5px skyblue;
    margin-left: 0;
    

  }
}
.btn{
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .1rem;
  font-size: 15px;
}
#clickFollowing:hover {
  background-color:#a1d4e2;
}
#clickFollower:hover{
  background-color: #a1d4e2;
}
</style>