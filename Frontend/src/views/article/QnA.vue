<template>
  <div class="container">
    <div class="container" v-for="item,idx in article" :key="idx">
      <QnAItem :item="item"/>
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="sprial">
      <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
    </infinite-loading>
  </div>
</template>

<script>
import QnAItem from '@/components/article/QnAItem.vue'
import axios from 'axios';
import InfiniteLoading from 'vue-infinite-loading';
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default { 
  components: {
    QnAItem,
    InfiniteLoading
  },

  data: function(){
    return {
      article:[],
      //articleItems: null,
      page: 0
    }
  },
   computed: {
  //   getQnaList: function() {
  //     return this.$store.getters.getQnaList
  //   }
        getToken(){
      const token = sessionStorage.getItem('jwt')
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
          url: `${SERVER_URL}/articlelist/qna`+"?page="+(this.page),
          headers: this.getToken,
        }).then(res => {
          console.log(res.data.article.length)
          setTimeout(() => {
            if(res.data.article.length) {
              
              this.article = this.article.concat(res.data.article)
              $state.loaded()
              this.page += 1
              console.log("after", this.article.length, this.page)
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면 
              if(res.data.length / 10 < 1) {
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

  // created() {
  //   axios({
  //       method: 'get',
  //       url: `${SERVER_URL}/articlelist/qna`+"?page="+(this.page),
  //       headers: this.getToken(),
  //     })   
  //     .then(res =>{
  //       console.log(res.data)
  //       this.article = res.data
  //       this.$store.dispatch('createQnaList',res.data.article)
  //     })
  //     .catch(err =>{  
  //       console.log(err)
  //     })
  // }
  
}
</script>

<style scoped>
.container{
  margin-top: 110px;
}
@media (max-width:576px){
  .container{
    margin-top: 60px;
  }
}
</style>