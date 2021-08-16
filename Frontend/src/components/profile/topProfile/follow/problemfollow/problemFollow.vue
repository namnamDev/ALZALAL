<template>
    <div>
      <div class="follow" v-for="(item,index) in problems" :key="index">
        <div class="user col-4 col-sm-4 col-lg-4">
            <span class="problem">
            {{item.site}} 
            </span>
        </div>
        <div class="user col-5 col-sm-5 col-lg-5">
            <span class="problem">
             {{item.no}}번
            </span>
        </div>
        <div class="user col-3 col-sm-3 col-lg-3">
            <span class="followBtn">
              <problemFollowBtn :site="item.site" :no="item.no"/>
            </span>
        </div>
      </div>
      <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
      </infinite-loading>
    </div>
</template>

<script>
import problemFollowBtn from '@/components/profile/topProfile/follow/problemfollow/problemFollowBtn.vue'
import jwt_decode from "jwt-decode";
import InfiniteLoading from 'vue-infinite-loading';
//import axios from 'axios';
const token = sessionStorage.getItem("jwt");
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
  
}
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  components:{
    problemFollowBtn,
    InfiniteLoading
  },
  data(){
    return {
      problems: [],
      page:0,
    }
  },  
  methods:{
    infiniteHandler($state) {
        fetch(`${SERVER_URL}/profile/${userpk}/problemfollowings`+"?page=" + (this.page), {method: "get"}).then(resp => {
          return resp.json()
        }).then(data => {
          setTimeout(() => {
            if(data.length) {
              this.problems = this.problems.concat(data)
              $state.loaded()
              this.page += 1
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면 
              if(data.length / 10 < 1) {
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
.follow {
  display: flex;
  margin-left: 30px;
}
.follow:hover{
  box-shadow: 0 0 0px 5px rgba(62 ,171 ,111 , 1);
}
.user{
  margin-top: 30px;
  font-size: 22px;
  font-weight: 700;
  
}
.problem{
  font-weight: 550;
  font-size: 22px;
  margin-left: 10px;
}

@media (max-width:768px) {
  .user{
  margin-top: 30px;
  font-size: 15px;
  font-weight: 700;
  
}

}


</style>