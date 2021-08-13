<template>
  <div class="container">
      <debateItem v-for="item,index in discussion" :key="index"
        :discussionHostName="item.discussCompHostNo.name"
        :discussionHostNo="item.discussCompHostNo.no"
        :discussionCompName="item.discussCompName"
        :discussionDate="item.discussDate"
        :discussionNo="item.discussNo"
        :discussionCompProblem="item.disscussCompProblem"
        :discussCommentCount="item.discussCommentCount"
      ></debateItem>
      <infinite-loading @infinite="infiniteHandler" spinner="sprial">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
      </infinite-loading>
  </div>
</template>

<script>
import debateItem from '@/views/debate/debateItem.vue'
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
import InfiniteLoading from 'vue-infinite-loading';
export default {
  components:{
    InfiniteLoading,
    debateItem
  },
  data(){
    return{
      discussion:[],
      page:0
    }
  },
  created: function(){
   
  },
  methods: {
    infiniteHandler($state) {
     axios({
        method: 'get',
        url: `${SERVER_URL}/articlelist/discussion`+"?page=" + (this.page),
        headers: this.getToken,
      })   //토론 불러와
      .then(res =>{     
        console.log(res)
        console.log(res.data.discussion.length)             
        setTimeout(() => {
            if(res.data.discussion.length) {
              this.discussion = this.discussion.concat(res.data.discussion)
              $state.loaded()
              this.page += 1
              // 끝인지 판별
              if(res.data.discussion.length / 20 < 1) {
                $state.complete()
              }
            } else {
              // 끝 지정(No more data)
              $state.complete()
            }
          }, 1000)
      })
      .catch((err)=>{  // 실패하면 error
        console.log(err)
      })
    }
  },
  computed: {
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    }
	},
}
</script>

<style scoped>

.row{
  margin-top: 100px;
}

@media (max-width:577px) {
  .feed{
    left: 0;
    transform: translateX(0);
  }
}




.feed-card {
  box-sizing: content-box;
  /* box-shadow: 0 0 0 1px #ddd; */
  color: #000;
  float: left;
  border-radius: 5px;
  overflow: hidden;
  
}

</style>