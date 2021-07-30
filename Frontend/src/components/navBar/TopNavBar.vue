<template>
  <div class="top">
    <div class="logo" onclick="window.scrollTo(0,0)">
      <img src="@/assets/images/Algorithm_img.png" height="100px" alt="">
    </div>
    <div class="user">      

      <!-- 로그인 했을 때 -->
      <ul class="navbar-nav me-4" v-if="isLogin">
        <li class="nav-item dropdown" @click="click">
          <a
            class="nav-link"
            href="#"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            {{userEmail}},
            
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" @click="modifyUser">정보수정</a></li>
            <li><a class="dropdown-item" @click="goProfile">프로필페이지</a></li>
            <li><a class="dropdown-item" v-on:click="logout">로그아웃</a></li>
          </ul>
        </li>
      </ul>

<!-- 로그인 안했을 때 -->
      <ul class="navbar-nav me-4" v-if="!isLogin">
        <li class="nav-item dropdown">
          <a class="" @click="login">Log in </a>
        </li>
        <li><a class="" @click="signup">Sign Up</a></li>
      </ul>
    </div>
  </div>
</template>

<script>
import jwt_decode from 'jwt-decode'
const token = localStorage.getItem('jwt')
let username = '';
if (token) {
  const decoded = jwt_decode(token)
  //userpk = decoded.sub
  username = decoded.name
}
//let userpk = '';

export default {
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
      this.$router.push({'name':'profilePage'})
    },
    login: function() {
      this.$router.push({'name':'login'})
    },
    signup: function() {
      this.$router.push({'name':'login'})
    },
    click: function() {
      console.log('click')
    }
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
    }

  }
}
</script>

<style scoped>

.box{
  background-color: bisque;
}
.top{
  position:fixed;
  background-color: white;
  z-index:4;
  width: 100%;
  height:70px;
  /* border: 1px solid black; */
}

.logo{
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  z-index:2;  
  cursor: pointer;
}
.user{
  border: 10px solid blanchedalmond;
  font-size:2vw;
  position: absolute;
  right: 7%;
}
.nav-link{
    font-size: 2vw;
    font-weight: 600;
    line-height: 1.0;
    color: black;
}
.dropdown-item {
  cursor: pointer;
      font-size: 1.0rem;
    font-weight: 600;
    line-height: 1.0;
}

</style>