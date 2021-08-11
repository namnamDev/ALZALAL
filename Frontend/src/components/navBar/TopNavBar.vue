<template>
  <div class="top">
    <div class="main">
      <!-- <div class="logo" onclick="window.scrollTo(0,0)">
        <img src="@/assets/images/Algorithm_img.png" height="100px" alt="">
      </div> -->
      
      <div class="main-left">
        <!-- 로고 -->
        <div class="logo" @click="clickTimeline">
           <img src="@/assets/images/logo1.jpg" height="60px" alt="Logo">
        </div>
      </div>
      <div class="main-middle">
        <!-- 검색 -->
        <div class="search">
          <SearchBar />
        </div>
      </div>
    
      <div class="main-right">
        <!-- 탐색 -->
        <TimelineIcon/>
        <QnaIcon/>
        <DebateIcon/>   
        <NotificationIcon/>
        <!-- 유저 -->
        <div class="user">      
          <!-- 로그인 했을 때 -->
          <ul class="navbar-nav" v-if="isLogin">
            <li class="nav-item dropdown" @click="click">
              <a
                class="nav-link"
                href="#"
                id="navbarDropdown"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <!-- <span class="username">{{userEmail}}</span> -->
                <span class="imageSection">
                  <img class="profileImg" v-if="imgsrc" :src="imgsrc" @error="imageError = true" alt="프로필사진">
                </span>
              </a>
              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" @click="modifyUser">정보수정</a></li>
                <li><a class="dropdown-item" @click="goProfile">프로필페이지</a></li>
                <li><a class="dropdown-item" v-on:click="logout">로그아웃</a></li>
              </ul>
            </li>
          </ul>

    <!-- 로그인 안했을 때 -->
          <div class="login-signup" v-if="!isLogin">
            <span @click="login" class="loginBtn">Log in </span>
            <span @click="signup" class="signupBtn"> Sign Up</span>
          </div>
          <!-- <ul class="navbar-nav me-4" v-if="!isLogin">
            <li class="nav-item dropdown">
              <a class="" @click="login">Log in </a>
            </li>
            <li><a class="" @click="signup">Sign Up</a></li>
          </ul> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import jwt_decode from 'jwt-decode'
import TimelineIcon from './icon/TimelineIcon.vue'
import QnaIcon from './icon/QnaIcon.vue'
import NotificationIcon from './icon/NotificationIcon.vue'
import DebateIcon from './icon/DebateIcon.vue'
import SearchBar from "@/components/search/SearchBar.vue";

    
const token = localStorage.getItem('jwt')
const SERVER_URL = process.env.VUE_APP_SERVER_URL
let username = '';
let userpk = '';
if (token) {
  const decoded = jwt_decode(token)
  //userpk = decoded.sub
  username = decoded.name
  userpk = decoded.sub
}
//let userpk = '';

export default {
  components:{
    TimelineIcon,
    QnaIcon,
    DebateIcon,
    NotificationIcon,
    SearchBar
  },
  data(){
    return{
      imgsrc: `${SERVER_URL}/profile/img/${userpk}`,
      imageError: false,
      defaultImage: require("@/assets/images/profileImg.png")
    }
  },
  methods: {
    logout: function(){
      this.$store.dispatch('logout')
      localStorage.removeItem("jwt");
      localStorage.removeItem("refresh");
      localStorage.removeItem("vuex")
      this.$router.push({name: 'login'})
    },
    modifyUser: function() {
      this.$router.push({'name':'passwordConfirm'})
    },
    goProfile: function() {
      // localStorage.removeItem("userPk")
      localStorage.setItem("userPk",userpk)
      // if(!localStorage.getItem('userPk')){
      // this.$router.push({'name':'profilePage', params:{userPk:userpk}})
      location.href=`http://localhost:8080/profilePage/${userpk}`
      
    },
    login: function() {
      this.$router.push({'name':'login'})
    },
    signup: function() {
      this.$router.push({'name':'signup'})
    },
    click: function() {
      console.log('click')
    },
    clickTimeline: function() {
     this.$router.push({'name':'timeline'})
    },
  },
  computed: {
    isLogin(){
      //console.log(this.$store.getters.isLogin)
      return this.$store.getters.isLogin
    },
    userName: function(){
      return username
    },
    userEmail(){
      return this.$store.getters.getEmail
    },
    creatorImage() {
        return this.imageError ? this.defaultImage : "imgsrc";
    }

  }
}
</script>

<style scoped>

.top{
  position:sticky;
  top: 0px;
  /* sticky가 fixed랑 비슷한효과 그런데 flex의 적용을 받음 */
  background-color: rgb(62, 171, 111);
  z-index:4;
  width: 100%;
  height:70px;
  /* border: 1px solid black; */
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.logo{
  /* position: absolute;
  left: 50%;
  transform: translateX(-50%);
  z-index:2;   */
  cursor: pointer;
}
.user{
  /* border: 10px solid blanchedalmond; */
  font-size:20px;
  /* position: absolute;
  right: 7%;
  top: 20px; */
}

.nav-link{
    font-size: 25px;
    font-weight: 600;
    line-height: 1.0;
    color: black;
}
.dropdown-item {
  cursor: pointer;
  font-size: 1.0rem;
  line-height: 1.3;
}
.login-signup{
  /* margin-top: */
  cursor: pointer;
  font-weight: 550;
}
.loginBtn{
    font-size: 25px;
    margin-right: 10px;
  }
.signupBtn{
    font-size: 25px;
    margin-right: 10px;
}
.login-signup span:hover{
  /* transform: scale(1.2); */
  font-size: 25px;
}
.username {
  margin-right: 15px;
}

@media (max-width: 576px){
    .login-signup{
    font-weight: 550;
    font-size: 20px;
  }
  .loginBtn{
    display: block;
    font-size: 25px;
    margin-right: 10px;
  }
  .signupBtn{
    font-size: 25px;
    margin-right: 10px;
  }
  .user{
      right: 0;    
  }
  .profileImg {
      width: 40px;
      height: 40px;
      border-radius: 75%;
  }
}
@media (max-width: 768px){
  .username {
    display: none;
  }
}

.imageSection{
}
.profileImg {
    width: 45px;
    height: 45px;
    border-radius: 75%;
}
.main{
  width: inherit;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 5% 0 5%;
}

i {
  color: white;
   cursor: pointer;
  font-size: 30px;
}
.main-left{
  flex-basis: 20%;
}
.main-middle{
  flex-basis: 30%;
  display: flex;
  justify-content: center;
}
.main-right{
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-basis: 20%;
}
</style>