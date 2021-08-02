<template>
  <div>
    <!-- 화면예시 시작 -->
    <div class="follow">
    <div class="userImg col-2">
        <img class="profileImg" src="@/assets/images/profileImg.png" alt="이미지">
    </div>
    <div class="user col-10">
        <span class="userName">
        UserName
        </span>
        <span class="followBtn">
        <button class="btn">팔로우</button>
        </span>
    </div>
    </div>
    <!-- 화면예시 끝 -->

    <div class="follow" v-for="(item, index) in follower" :key="index"> 
    <div class="userImg col-2">
        <img class="profileImg" src="@/assets/images/profileImg.png" alt="이미지">
    </div>
    <div class="user col-10">
        <span class="userName" v-bind="memberNo">
        {{item.name}}
        </span>
        <span class="followBtn">
        <button class="btn" @click="clickFollow">팔로우</button>
        </span>
    </div>
    </div>
  </div>
</template>

<script>
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
  data(){
    return {
      follower:[],
      memberNo: ''
    }
  },
  created: function(){
    axios({
      method: 'get',
      url: `${SERVER_URL}/profile/${userpk}/followers`,
      data: page
    })   // back 에 로그인 요청
    .then(res =>{
      console.log(res)
      this.follower = res.data
      console.log(this.follower)
    })
    .catch(err =>{  // 실패하면 error
      console.log(err)
      
    })
  },
  methods:{
    clickFollow: function(){
        axios({
                method: 'post',
                url: `${SERVER_URL}/follow/member`,
                data: this.memberNo,
            })
            .then(res =>{
                console.log(res)         
            })
            .catch(err => {
                console.log(this.memberNo)
                console.log(err);
            })
    }
  }
}
</script>

<style scoped>
.container{
  margin-top: 110px;
}
@media (max-width:576px){
  .container{
    margin-top: 120px;
  }
}
.userImg1 {
    
    width: 75px;
    height: 75px; 
    border-radius: 70%;
    overflow: hidden;
    display: flex;
    margin: 20px 0px 0px auto;
}
/* 두개 비교할려고 냅둠 */
.userImg {
    
    width: 75px;
    height: 75px; 
    border-radius: 70%;
    overflow: hidden;
    display: flex;
    margin: 20px 0px 0px auto;
}
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