<template>
  <div class="row">
    <div class="col-2 me-5">
      <i class="fas fa-user" style="font-size: 80px"></i>
    </div>
    <div class="col ms-4">

      <div class="row">
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
          <button class="follow-cancel-btn me-2" @click="follow" v-if="followState">팔로우취소</button>
          <button class="follow-btn me-2" @click="follow" v-else>팔로우</button>
          <button class="request" @click="request">문제풀이 요청</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  props:{
    member: Object,
  },
  data() {
    return{
      followState: false,
    }
  },
  created() {
    this.followState = this.member.followState
  },
  methods: {
    getToken() {
      const token = localStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },

    follow: function() {
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
      .then((res)=>{
        console.log(res)
      })
      .catch(err=>{
        console.log(err)
      })
    },

    request: function() {
      this.$router.push({name:'createHelpme', params:{ targetPK:this.member.no }})
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
</style>