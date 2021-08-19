<template>
  <div class="row">
    <div class="col-2 me-5" >
      <img class="profileImg" :src="getImgSrc" @error="imageError = true" @click="goProfile" alt="프로필사진">
    </div>
    <div class="col ms-4">

      <div class="row" @click="goProfile" style="cursor:pointer;">
        {{member.name}}
      </div>

      <div class="row mt-1">
        <div class="col p-0">
          <span class="me-3">게시글 {{member.articleCount}}</span>
          <span class="me-3">팔로워 {{member.followerCount}}</span>
          <span class="me-3">팔로우 {{member.followingCount}}</span>
        </div>
      </div>

      <div class="row mt-2">
        <div class="col p-0">
          <button class="follow-cancel-btn me-2" @click="follow" v-if="followState && !this.me">팔로우취소</button>
          <button class="follow-btn me-2" @click="follow" v-if="!followState && !this.me">팔로우</button>
          <button class="request" @click="request" v-if="!this.me">문제풀이 요청</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import jwt_decode from 'jwt-decode'
import axios from "axios";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
const token = sessionStorage.getItem('jwt')
let userpk = '';
if (token) {
  const decoded = jwt_decode(token)
  userpk = decoded.sub
}
export default {
  props:{
    member: Object,
    
  },
  data() {
    return{
      followState: false,
      me:'',
    }
  },
  created() {
    this.followState = this.member.followState
    
    if(this.member.no == userpk){
      this.me=true
    }else{
      this.me=false
    }
    
  },
  computed:{
    getImgSrc(){
      return `${SERVER_URL}/profile/img/${this.member.no}`;
    },
    isLogin: function() {
      const token = sessionStorage.getItem('jwt')
      if (token){
        return true
      }
      else{
        return false
      }
    }
  },
  methods: {
    goProfile() {
      localStorage.setItem('userPk',this.member.no)
      this.$router.push({'name':'profilePage', params:{userPk:this.member.no}})
    },
    getToken() {
      const token = sessionStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },

    follow: function() {
      if(this.isLogin){
        this.followState = !this.followState
        if(this.followState){
          this.member.followerCount += 1
          this.$swal(`'${this.member.name}'님을 팔로우 하셨습니다.`)
        }
        else{
          this.member.followerCount -= 1
          this.$swal(`'${this.member.name}'님을 팔로우 취소 하셨습니다.`)
        }
        const data = {
          memberNo: this.member.no
        }
        axios({
          method: 'post',
          url: `${SERVER_URL}/follow/member`,
          headers: this.getToken(),
          data: data
        })
        .then(()=>{
        })
        .catch(err=>{
          console.log(err)
        })
      }
      else{
        this.$swal(`로그인이 필요합니다.`)
      }
    },

    request: function() {
      if(this.isLogin){
        this.$router.push({name:'createHelpme', params:{ targetPK:this.member.no }})
      }
      else{
        this.$swal(`로그인이 필요합니다.`)
      }
    }
  }
}
</script>

<style scoped>
.follow-btn{
  background-color: rgb(0, 153, 255);
  padding:3px 10px;
  border-radius: 4px;
  color:white;
  cursor: pointer;
  border-style: none;
}
.follow-btn:hover{
  transform: scale(1.1);
  background-color: rgb(0, 98, 255);
}
.follow-cancel-btn{
  background-color: white;
  border: 1px solid blue;
  padding:3px 10px;
  border-radius: 4px;
  color:rgb(59, 121, 223);
  cursor: pointer;
}
.follow-cancel-btn:hover{
  transform: scale(1.1);
}
.request{
  background-color: rgba(197, 197, 197, 0.603);
  border-style: none;
  border-radius: 3px;
  padding: 3px 10px;
}
.request:hover{
  background-color: rgba(161, 161, 161, 0.514);
  transform: scale(1.1);
}
.profileImg {
    width: 85px;
    height: 85px;
    border-radius: 75%;
}
</style>