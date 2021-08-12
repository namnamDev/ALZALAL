<template>
  <div class="container">    
    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1">        
      </div>

      <div class="col-lg-6 col-md-10 col-sm-9 col-10 mt-4">   
        <div class="row ms-5 ps-5">
          총 {{articleCount}}명의 유저가 검색되었습니다.
        </div>    
        <div class="row mt-5 profile-card" v-for="member,idx in memberList" :key="idx">
          <SearchUserItem :member="member"/>          
        </div>

      </div>
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="sprial">
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px"
      >
        목록의 끝입니다 :)
      </div>
    </infinite-loading>

  </div>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
import SearchUserItem from '@/components/article/SearchUserItem.vue'

import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  components: {
    InfiniteLoading,
    SearchUserItem
  },
  props:{
    user: String,
  },
  data() {
    return{
      memberList: [],
      page:0,
      articleCount: 0 ,
      followState: false,
    }
  },
   computed: {
    getArticle: function() {
      return this.$store.getters.getSearchArticle
    }
  },

  methods: {
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },

    infiniteHandler($state) {
      const data = this.getArticle  
      axios({
        method: "get",
        url: `${SERVER_URL}/search/member` + "?page=" + this.page,
        headers: this.getToken(),
        params: {
          name: data.user,
        }
      })
        .then((res) => {
          this.articleCount = res.data.memberSearchCount
          // this.followState = res.data.
          setTimeout(() => {
            if (res.data.memberList.length) {
              //console.log(res.data.article.length)         
              this.memberList = this.memberList.concat(res.data.memberList);
              $state.loaded();
              this.page += 1;
              //console.log("after", this.page)
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면
              if (res.data.memberList.length / 10 < 1) {
                $state.complete();
              }
            } else {
              // 끝 지정(No more data)
              $state.complete();
            }
          }, 1000);
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },

}
</script>

<style scoped>
.profile-card{
  -webkit-box-shadow : 0 0px 10px rgba(0, 0, 0, 0.08);
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.12);
  border-radius: 20px;
  width: 470px;
  height:140px;
  margin:0 auto;
  padding: 25px 20px;
}
.profile-card:hover{
  -webkit-box-shadow: 0 0px 20px rgba(161, 212, 226, 0.6);
  box-shadow: 0 0px 20px rgba(161, 212, 226, 0.6);
  /* transform: scale(1.1); */
}

</style>