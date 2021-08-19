<template>
    <div>
      <div class="follow" v-for="item,index in algo" :key="index">
        <div class="user col-8 col-sm-8 col-lg-8">
            <span class="problem">
            {{item}}
            </span>
        </div>
        <div class="user col-4 col-sm-4 col-lg-4">
            <span class="followBtn">
              <algoFollowBtn :algo="item"/>
            </span>
        </div>
      </div>
      <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
      </infinite-loading>
    </div>
</template>

<script>
import axios from 'axios'
import algoFollowBtn from '@/components/profile/topProfile/follow/algofollow/algoFollowBtn.vue'
import InfiniteLoading from 'vue-infinite-loading';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  components:{
    algoFollowBtn,
    InfiniteLoading
  },
  data(){
    return {
      algo: [],
      page:0
    }
  }, 
  methods:{
    infiniteHandler($state) {
        const userPk = localStorage.getItem('userPk')
        axios({
          method:'get',
          url:`${SERVER_URL}/profile/${userPk}/algofollowings`+"?page=" + (this.page)
        }).then(res => {
          console.log(res.data)
          setTimeout(() => {
            if(res.data.length) {
              this.algo = this.algo.concat(res.data)
              console.log(this.algo)
              $state.loaded()
              this.page += 1
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면 
              if(res.data.length / 10 < 1) {
                $state.complete()
              }
            } else {
              // 끝 지정(No more data)
              $state.complete()
            }
          }, 1000)
          console.log(this.algo)
        }).catch(err => {
          console.error(err);
        })
       },
  }
  
}
</script>

<style scoped>
.follow {
  display: flex;
}
.follow:hover{
 box-shadow: 0 0 0px 5px rgba(62 ,171 ,111 , 1);
}
.nav-link{
  font-weight: 700;
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
    margin-left: 30px;
  
  }
}
</style>