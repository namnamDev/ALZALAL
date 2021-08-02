<template>
    <div>

<!-- 예시 화면 코드 끝 -->

      <followListImg v-for="(item,index) in following" :key="index" v-bind:name="item.name" v-bind:no="item.no"/>
    </div>
</template>

<script>
import followListImg from '@/components/profile/follow/userfollow/followListImg.vue'
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
    followListImg
  },
  data(){
    return {
      following:[],
    }
  },
  created: function(){
    axios({
      method: 'get',
      url: `${SERVER_URL}/profile/${userpk}/memfollowings`,
      data: page
    })   // back 에 로그인 요청
    .then(res =>{
      console.log(res)
      this.following = res.data
      console.log(this.following.no)
      console.log(this.follower)
    })
    .catch(err =>{  // 실패하면 error
      console.log(err)
    })
  },
}
</script>

<style scoped>

.profileImg{
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
}
.follow {
  display: flex;
}
.nav {
  cursor:pointer;
  margin-bottom: 50px;
}
.nav-link{
  font-weight: 700;
}
.user{
  margin-top: 30px;
  font-size: 20px;
  font-weight: 700;
  margin-left: 100px;
  
}
.userName{
  margin-right: 30px;
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
  font-size: 1vw;
}
#clickFollowing:hover {
  background-color:#a1d4e2;
}
#clickFollower:hover{
  background-color: #a1d4e2;
}
</style>