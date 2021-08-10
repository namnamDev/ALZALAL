<template>
  <div class="container feed">
      <div class="row">
        <div class="col-lg-3 col-md-2 col-sm-3 col-0"></div>
        <div class="debate feed-card col-lg-6 col-md-10 col-sm-9 col-12">
            <div class="contentsWrap">
                <p class="title">카카오 2016 인턴  <span>1번문제</span></p>
                <p>카카오</p>  
            </div>
            <div class="btn-group wrap col-12 col-lg-12 col-ml-12">
                <div class="col box1 like-comment">
                  <i class="far fa-comment-dots mx-2"></i>
                  <span >2</span>
                  <div><p class="date">2016-01-03 09:00:00</p></div>
                </div>
            </div>
        </div>
      </div>
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
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    }
	},
}
</script>

<style scoped>
.feed{
  position: absolute;
  margin-top: 100px;
  left: 50%;
  transform: translateX(-50%);
  margin-left:0;
}
.row{
  margin-top: 100px;
}
.debate {
  height: 300px;
  font-size: 1vw;
  line-height: 2;
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
.contentsWrap {
  box-sizing: border-box;
  padding: 12px;
  float: left;
}
.title {
  color:#000;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
  line-height: 1.5em;
  font-size: 25px;
  font-weight: 550;
  margin: 0 0 8px;
  white-space: normal;
}
.date {
  float: right;
  font-size: 15px;
  color:rgba(0, 0, 0, .5);
  
}
.comment{
  margin-right: 30px;
}
</style>