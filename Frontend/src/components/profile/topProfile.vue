<template>
  <div class="container feed">
    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="profile box col-lg-6 col-md-10 col-sm-9 col-10 ">
                <!-- 프로필이미지 -->
			  <div class="profile-image">
				  <img class="profileImg" src="@/assets/logo.png" alt="">
                  <div class="modifyProfile"><button class="btn btn-modify">프로필이미지 수정</button></div>
			  </div>

                <!-- 프로필 이름, 수정버튼 -->
			  <div class="profile-user-settings">
				  <h1 class="profile-user-name">{{userName}}</h1>
                  <span>
				  <router-link to="/passwordConfirm"><button class="btn profile-edit-btn">Edit Profile</button>
                  </router-link>  
                  </span>
			  </div>

                    <!-- 게시글 팔로워 팔로잉 -->
			  <div class="profile-stats">
				  <ul>
					  <li>게시글<span class="profile-stat-count">164</span> </li>
					  <li>팔로워<span href="/profilePage/followPage" class="profile-stat-count">188</span> </li>
					  <li>팔로잉<span class="profile-stat-count">206</span> </li>
				  </ul>

			  </div>

			  <div class="profile-bio">
                  <p align="left" class="downInfo">선호 언어: JAVA</p>
                  <p align="left" class="downInfo">선호 문제사이트 : 백준</p>
				  <p align="left" class="downInfo">총 n개의 게시글에 답변했습니다.</p>
                  <p align="left">한줄소개<button class="btn btn-intro">수정</button></p>
			  </div>

		  </div>
		<!-- End of profile section -->
    
    </div>
  </div>
</template>

<script>
import jwt_decode from 'jwt-decode'
const token = localStorage.getItem('jwt')
let userpk = '';
let username = '';
if (token) {
  const decoded = jwt_decode(token)
  userpk = decoded.sub
  username = decoded.name
}
export default {
    created: function() {
        console.log(userpk, username)
    },
    computed: {
        userName: function(){
            return username
        }
    }
}
</script>

<style scoped>
.feed{
  margin-top: 13vw;
   margin-left: 50px;
}
.profile box{
  border: 1px solid black;
  height: 300px;
  padding: 5rem 0;
}
.profile::after {
    content: "";
    display: block;
    clear: both;
}
.profile-image {
    float: left;
    width: calc(33.333% - 1rem);
    height: auto;
    justify-content: center;
    align-items: center;
    margin-right: 3rem;
    
}

.profileImg {
    width: 180px;
    height: 180px;
    border-radius: 75%;
    
}
.btn-modify{
    width: 100%;
    height: 100%;
    font-size: .8vw;
    line-height: 1.3;
    border: 0.1rem solid #dbdbdb;
    border-radius: 0.3rem;
    padding: 0 2.4rem;
    margin-left: 10px;
    font-weight: 700;
}

.profile-user-settings,
.profile-stats,
.profile-bio {
    float: left;
    width: calc(66.666% - 2rem);
}

.profile-user-settings {
    margin-top: 1.1rem;
}

.profile-user-name {
    display: inline-block;
    font-size: 4vw;
    font-weight: 300;
}

.profile-edit-btn {
    font-size: 1.2vw;
    line-height: 1.8;
    border: 0.1rem solid #dbdbdb;
    border-radius: 0.3rem;
    padding: 0 2.4rem;
    margin-left: 2rem;
}

.profile-settings-btn {
    font-size: 2rem;
    margin-left: 1rem;
}

.profile-stats {
    margin-top: 2.3rem;
}

.profile-stats li {
    display: inline-block;
    font-size: 1.5vw;
    line-height: 1.5;
    margin-right: 1rem;
    cursor: pointer;
}

.profile-stats li:last-of-type {
    margin-right: 0;
}

.profile-bio {
    font-size: 1.2rem;
    font-weight: 400;
    line-height: 1.5;
    margin-top: 2.3rem;
}
.downInfo {
    font-size: 1.0rem;
    font-weight: 600;
    line-height: 1.0;
}
.profile-real-name,
.profile-stat-count,
.profile-edit-btn {
    font-weight: 600;
} 

</style>