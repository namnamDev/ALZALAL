<template>
  <div>
    <!-- <div class="follow" v-for="(item, index) in follower" :key="index">  -->
      <followerListImg v-for="(item,index) in follower" :key="index" v-bind:name="item.name" v-bind:no="item.no"/>
    <!-- </div> -->
  </div>
</template>

<script>
import followerListImg from '@/components/profile/topProfile/follower/followerListImg.vue'
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
    followerListImg
  },
  data(){
    return {
      follower:[],
    }
  },
  created: function(){
    
    axios({
      method: 'get',
      url: `${SERVER_URL}/profile/${userpk}/followers`,
      data: page
    })   // back 에 로그인 요청
    .then(res =>{
      this.follower = res.data
    })
    .catch(err =>{  // 실패하면 error
      console.log(err)
      
    })
  },
  computed: {
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
	},
}
</script>

<style scoped>
</style>