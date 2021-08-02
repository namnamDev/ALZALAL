<template>
    <div>
      <div class="follow" v-for="(item,index) in problems" :key="index">
        <div class="user col-10">
            <span class="problem">
            {{item.site}} 
            </span>
            <span class="problem">
             {{item.no}}번
            </span>
            <span class="followBtn">
              <problemFollowBtn :site="item.site" :no="item.no"/>
            </span>
        </div>
      </div>
    </div>
</template>

<script>
import problemFollowBtn from '@/components/profile/topProfile/follow/problemfollow/problemFollowBtn.vue'
import jwt_decode from "jwt-decode";
import axios from 'axios';
const token = localStorage.getItem("jwt");
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
  
}
let page =0;
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  components:{
    problemFollowBtn
  },
  data(){
    return {
      problems: []
    }
  },  
  created: function(){
    axios({
      method: 'get',
      url: `${SERVER_URL}/profile/${userpk}/problemfollowings`,
      data: page
    })   // back 에 로그인 요청
    .then(res =>{
      console.log(res)
      this.problems = res.data
    })
    .catch(err =>{  // 실패하면 error
      console.log(err)
      
    })
  },
}
</script>

<style scoped>
.follow {
  display: flex;
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

#clickFollowing:hover {
  background-color:#a1d4e2;
}
#clickFollower:hover{
  background-color: #a1d4e2;
}
</style>