<template>
  <div class="container py-5">

    <div class="row mt-5">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1">        
      </div>

      <div class="col-lg-6 col-md-10 col-sm-9 col-10 mt-5">       
        <div class="row mt-5 profile-card" v-for="member,idx in memberList" :key="idx">
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
                <button class="follow me-4">팔로우</button>
                <button class="request">문제풀이 요청</button>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>


  </div>
</template>

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  props:{
    user: String,
  },
  data() {
    return{
      memberList: null,
    }
  },
   computed: {
    getArticle: function() {
      return this.$store.getters.getSearchArticle
    }
  },
  created() {
    const data = this.getArticle    
    axios({
      method: 'get',
      url: `${SERVER_URL}/search/member`,
      headers: this.getToken(),
      params: {
        name: data.user,
      }
    })
    .then(res=>{
      this.memberList = res.data.memberList
      console.log(res.data)
    })
    .catch(err=>{
      console.log(err)
    })
  },
  methods: {
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
  }
}
</script>

<style scoped>
.profile-card{
  -webkit-box-shadow : 0 0px 10px rgba(0, 0, 0, 0.08);
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.12);
  border-radius: 20px;
  width: 450px;
  height:140px;
  margin:0 auto;
  padding: 25px 30px;
}
.profile-card:hover{
  -webkit-box-shadow: 0 0px 20px rgba(161, 212, 226, 0.6);
  box-shadow: 0 0px 20px rgba(161, 212, 226, 0.6);
  transform: scale(1.1);
  cursor: pointer;
}
.follow{
  background-color: rgb(181, 222, 233);
  border-style: none;
  border-radius: 3px;
  padding: 3px 10px;
}
.follow:hover{
  background-color: rgba(161, 212, 226, 1);
  transform: scale(1.1);
}
.request{
  background-color: rgba(240,240,240,1);
  border-style: none;
  border-radius: 3px;
  padding: 3px 10px;
}
.request:hover{
  background-color: rgb(224, 222, 222);
  transform: scale(1.1);
}
</style>