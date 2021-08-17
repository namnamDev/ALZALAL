<template>
  <div class="container">
    <TimelineWriteArticle/>
    <div v-for="item,idx in article" :key="idx">
      <QnAItem :item="item"/>
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="sprial">
      <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
    </infinite-loading>
  </div>
</template>

<script>
import QnAItem from '@/components/article/QnAItem.vue'
import TimelineWriteArticle from "@/components/article/TimelineWriteArticle.vue";

import axios from 'axios';
import InfiniteLoading from 'vue-infinite-loading';
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default { 
  components: {
    TimelineWriteArticle,
    QnAItem,
    InfiniteLoading
  },

  data: function(){
    return {
      article:[],
      page: 0
    }
  },
   computed: {
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
          setTimeout(() => {
            if(res.data.article.length) {
              
              this.article = this.article.concat(res.data.article)
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
        }).catch(err => {
          console.error(err);
        })
       },
   },

  
}
</script>

<style scoped>

</style>