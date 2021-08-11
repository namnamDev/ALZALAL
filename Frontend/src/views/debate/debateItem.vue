<template>
  <div class="row">
    <div class="col-lg-3 col-md-2 col-sm-3 col-0"></div>
    <div class="debate col-lg-6 col-md-10 col-sm-9 col-12">
        <div class="contentsWrap" @click="clickDebate">
            <p class="title">{{this.discussCompName}}     <span>{{this.disscussCompProblem}}</span></p>
            <p>{{this.discussCompHostName}}</p>                
        </div>
        <div class="btn-group wrap col-12 col-lg-12 col-ml-12">
            <div class="col box1 like-comment">
              <i class="far fa-comment-dots mx-2"></i>
              <span >{{this.commentCount}}</span>
              <div><p class="date">{{this.discussDate}}</p></div>
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
        discussionNo: Number,
    },
    data(){
        return{
            commentCount:'',
            discussCompHostNo:'',
            discussCompHostName:'',
            discussCompName:'',
            discussDate:'',
            disscussCompProblem:'',
            item:null
        }
    },
    computed:{
        Content: function() {
        return this.content
        },
        getToken(){
        const token = sessionStorage.getItem('jwt')
        const config = {
            Authorization: `Bearer ${token}`
        }
        return config
        },
    },
    mounted() {
    axios({
        method: 'get',
        url: `${SERVER_URL}/article/discussion/${this.discussionNo}`,
        headers: this.getToken,
      })   
      .then(res =>{
          console.log(res)
        const detail = res.data.articleDetail
        this.item = res.data
        this.commentCount = detail.commentCount
        this.discussCompHostNo = detail.discussCompHostNo.no
        this.discussCompHostName = detail.discussCompHostNo.name
        this.discussCompName = detail.discussCompName
        this.disscussCompProblem = detail.disscussCompProblem
      })
      .catch(err =>{  
        console.log(err)
      })
  },
  methods:{
      clickDebate: function(){
        localStorage.setItem('discussionNo', this.discussionNo)

        this.$store.dispatch('createDebateDetail',this.item.articleDetail)

        // 댓글 정보 요청후 store에 저장
        axios({
            method: 'get',
            url: `${SERVER_URL}/comment/article/${this.discussionNo}`,
        })   
        .then(res =>{
            this.$store.dispatch('createArticleComment',res.data.debateComments)
        })
        .catch(err =>{  
            console.log(err)
        })
        // 댓글리스트 불러오기
        axios({
          method: 'get',
          url: `${SERVER_URL}/comment/discussion/${this.discussionNo}`,
        })   
        .then(res =>{
          console.log(res)
          this.$store.dispatch('createDebateComment',res.data.articleComments)
        })
        .catch(err =>{  
          console.log(err)
        })

        this.$router.push({name : 'debateDetail', params:{'Page':'0', discussionNo:this.discussionNo}})
      }
  }
}
</script>

<style scoped>
.contentsWrap {
  box-sizing: border-box;
  padding: 12px;
  /* float: left; */
}
.title {
  color:#000;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
  line-height: 1.5em;
  font-size: 25px;
  font-weight: 600;
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