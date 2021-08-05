<template>
    <div class="followlist">
      <followListImg v-for="(item,index) in following" :key="index" v-bind:name="item.name" v-bind:no="item.no" v-bind:followState="item.followState"/>
      <infinite-loading @infinite="infiniteHandler" spinner="spiral">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
      </infinite-loading>
    </div>
</template>

<script>
import followListImg from '@/components/profile/topProfile/follow/userfollow/followListImg.vue'
import jwt_decode from "jwt-decode";
import InfiniteLoading from 'vue-infinite-loading';
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
    followListImg,
    InfiniteLoading
  },
  data(){
    return {
      following:[],
      page:0
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
  methods:{
      
        //  axios.get(`${SERVER_URL}/profile/${userpk}/memfollowings`+"?page="+this.page, {

        //   }).then(({ data }) => {
        //     console.log(data)
        //     if (data.length) {
        //       this.following.push(...data);
        //       console.log(this.page)
        //       $state.loaded();
        //       this.page+=1;
        //     } else {
        //       $state.complete();
        //     }
        //   });
      infiniteHandler($state) {
         axios({
          method: 'get',
          url: `${SERVER_URL}/profile/${userpk}/memfollowings`+"?page=" + (this.page),
          headers: this.getToken,
        }).then(res => {
          setTimeout(() => {
            
            if(res.data.length) {
              console.log(res.data.length)
              this.following = this.following.concat(res.data)
              $state.loaded()
              this.page += 1
              console.log("after", this.following.length, this.page)
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
       },
   },

}
</script>

<style scoped>

</style>