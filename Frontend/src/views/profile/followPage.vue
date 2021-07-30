<template>
  <div class="container">
    <div class="row">
    <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="col-lg-6 col-md-10 col-sm-9 col-10 ">
        <div class="row">
          <div class="col-6 col-lg-6">
            <button class="btn" @click="changeComponent('followerList')">follower</button>
          </div>
          <div class="col-6 col-lg-6">
            <button class="btn" @click="changeComponent('followList')"> follow</button>
          </div>
        </div>
        <div class="tab-item">
          <keep-alive>
            <component v-bind:is="comp"></component> 
          </keep-alive>
        </div>
      </div>
    </div>
    <!-- <div class="row"> -->
        <!-- <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
        <div class="col-lg-6 col-md-10 col-sm-9 col-10 "> -->
          
          
          <!-- <div class="nav-menu">
            <nav class="nav nav-pills flex-column flex-sm-row">
              <a class="flex-sm-fill text-sm-center nav-link" id="clickFollowing" @click="clickFollowingList">Following</a>
              <a class="flex-sm-fill text-sm-center nav-link" id="clickFollower" @click="clickFollowerList">Follower</a>
            </nav>
          </div> -->
        <!-- </div> -->
        <!-- <div class=followList v-show="isFollowingList">
          <followerList></followerList>
        </div>
        <div class=followerList v-show="isFollowerList">
          <followList></followList>
        </div> -->
    <!-- </div> -->
  </div>
      <!-- <div class="row">
        <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
        <div class="box col-lg-6 col-md-10 col-sm-9 col-10 ">
          <nav class="nav nav-pills flex-column flex-sm-row">
            <a class="flex-sm-fill text-sm-center nav-link" id="clickFollowing" @click="clickFollowingList">Following</a>
            <a class="flex-sm-fill text-sm-center nav-link" id="clickFollower" @click="clickFollowerList">Follower</a>
          </nav> -->
          <!-- 내가 팔로우중인사람 -->
          <!-- <div class=followList v-show="isFollowingList">
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
          </div>
          <div class=followList v-show="isFollowingList">
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
          </div> -->

            <!-- 내가 팔로우중인사람 끝 -->

            <!-- 나를 팔로우하는사람 시작 -->
            <!-- <div class=followerList v-show="isFollowerList">
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
            </div> -->
            <!-- 나를 팔로우하는사람 끝 -->
          <!-- </div>
        </div>
      </div> -->

</template>

<script>
import followList from '@/components/profile/followList.vue'
import followerList from '@/components/profile/followerList.vue'
// import jwt_decode from "jwt-decode";
// import axios from 'axios';
// const token = localStorage.getItem("jwt");
// let userpk = "";
// if (token) {
//   const decoded = jwt_decode(token);
//   userpk = decoded.sub;
  
// }
// let page =0;
// const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  components: {
    followList,
    followerList
  },
  data() {
      return { comp: 'followerList' }
  },
  methods: {
      changeComponent: function(componentName) {
          this.comp = componentName
          console.log(this.comp)
      }
  }
  // data: function() {
  //   return {
  //     isFollowingList: true,
  //     isFollowerList: false, 
  //   }
  // },
  // created: function(){
  //     axios({
  //       method: 'get',
  //       url: `${SERVER_URL}/profile/${userpk}/followers`,
  //       data: page
  //     })   // back 에 로그인 요청
  //     .then(res =>{
  //      console.log(res)
  //     })
  //     .catch(err =>{  // 실패하면 error
  //       console.log(err)
        
  //     })
  // },
  // methods: {
  //   clickFollowingList: function(){
  //     this.isFollowingList = true
  //     this.isFollowerList = false
      
  //   },
  //   clickFollowerList: function(){
  //     this.isFollowingList = false
  //     this.isFollowerList = true
  //   },
  //}
}
</script>

<style scoped>
.container{
  margin-top: 150px;
}
@media (max-width:576px){
  .container{
    margin-top: 140px;
  }
}
.btn{
  width:200px;
  font-size:25px;
  font-weight: 550;
}
.tab-item {
  margin-top: 20px;
}
.btn:hover{
  background-color: antiquewhite;
}
</style>