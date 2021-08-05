<template>
  <div>
      <followerListImg v-for="(item,index) in follower" :key="index"
       v-bind:name="item.name" v-bind:no="item.no" v-bind:followState="item.followState"/>
      <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
      </infinite-loading>
  </div>
</template>

<script>
import followerListImg from '@/components/profile/topProfile/follower/followerListImg.vue'
import InfiniteLoading from 'vue-infinite-loading';
import jwt_decode from "jwt-decode";
import axios from 'axios';
const token = localStorage.getItem("jwt");
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
  
}


const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  components:{
    followerListImg,
    InfiniteLoading,

  },
  data(){
    return {
      follower:[],
      page: 0
    }
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
  methods: {
    infiniteHandler($state) {
        axios({
          method: 'get',
          url: `${SERVER_URL}/profile/${userpk}/followers`+"?page=" + (this.page),
          headers: this.getToken,
        }).then(res => {
          setTimeout(() => {
            if(res.data.length) {
              this.follower = this.follower.concat(res.data)
              $state.loaded()
              this.page += 1
              //console.log("after", this.follower.length, this.page)
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면 
              if(res.data.length / 20 < 1) {
                $state.complete()
              }
            } else {
              // 끝 지정(No more data)
              $state.complete()
            }
          }, 1000)
        }).catch(err => {
          console.error(err);
        })
    }
  }
}
</script>

<style scoped>
</style>