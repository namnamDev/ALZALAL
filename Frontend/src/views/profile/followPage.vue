<template>
     <div class="container feed">
      <div class="row">
        <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
        <div class="box col-lg-6 col-md-10 col-sm-9 col-10 ">
          <nav class="nav nav-pills flex-column flex-sm-row">
            <a class="flex-sm-fill text-sm-center nav-link" id="clickFollowing" @click="clickFollowingList">Following</a>
            <a class="flex-sm-fill text-sm-center nav-link" id="clickFollower" @click="clickFollowerList">Follower</a>
          </nav>
          <!-- 내가 팔로우중인사람 -->
          <div class=followList v-show="isFollowingList">
            <div class="follow">
              <div class="userImg1 col-5">
                <img class="profileImg" src="@/assets/profileImg.png" alt="이미지">
              </div>
              <div class="user col-7">
                <div class="userName">
                UserName
                </div>
                <div class="followBtn">
                  <button class="btn">팔로우</button>
                </div>
              </div>
            </div>
          </div>
          <div class=followList v-show="isFollowingList">
            <div class="follow">
              <div class="userImg col-2">
                <img class="profileImg" src="@/assets/profileImg.png" alt="이미지">
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

            <!-- 내가 팔로우중인사람 끝 -->

            <!-- 나를 팔로우하는사람 시작 -->
            <div class=followerList v-show="isFollowerList">
            <div class="follow">
              <div class="userImg col-2">
                <img class="profileImg" src="@/assets/profileImg.png" alt="이미지">
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
            <!-- 나를 팔로우하는사람 끝 -->
          </div>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  data: function() {
    return {
      isFollowingList: true,
      isFollowerList: false, 
    }
  },
  methods: {
    clickFollowingList: function(){
      this.isFollowingList = true
      this.isFollowerList = false
      
    },
    clickFollowerList: function(){
      this.isFollowingList = false
      this.isFollowerList = true
    }
  }
}
</script>

<style scoped>
.feed{
  margin-top: 13vw;
}
.box {
  
  height: 300px;
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
  margin-top: 20px;
  font-size: 1rem;
  font-weight: 700;
  
}
.userName{
  margin-right: 30px;
}
.followBtn {
  border-radius: 10%;
  border: solid 0.5px skyblue;
  ;
  width: 30%;
  margin: 0px auto;
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