<template>
  <div class="container">

    <div class="row main">

      <div class="col-lg-8 col-md-10 col-sm-9 col-10">
        <div class="main">
        <div class="col-lg-10 col-md-10 col-sm-9 col-10">
        
        <div class="row top">
          <div class="col">
            <div v-if="isOneAlgo">
              <div> 
                # {{this.includeAlgo[0]}}
                <span class="follow-cancel-btn" @click="follow" v-if="followingState && isLogin">팔로우취소</span>
                <span class="follow-btn" @click="follow" v-if="!followingState && isLogin">팔로우</span>
              </div>
            </div>
          </div>
        </div>

        <div class="row middle mb-5" v-if="isOneAlgo">
          <div class="col">
            <div style="display:inline;" class="me-3" v-if="followingNumber">
              팔로잉  {{followingNumber}}
            </div>
            <div style="display:inline;" class="me-3" v-else>
              팔로잉  0
            </div>
            <div style="display:inline;" class="me-3" v-if="articleNumber">
              게시글  {{articleNumber}}
            </div>
            <div style="display:inline;" class="me-3" v-else>
              게시글  0
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col">
            총 {{articleCount}}개의 게시글이 검색되었습니다.
          </div>
          <div class="col text-end">
            <span class="order-by-new" @click="clickOrderByNew">최신순</span> | 
            <span class="order-by-like" @click="clickOrderByLike">좋아요순</span>
          </div>          
        </div>
      </div>
    </div>
        <div v-if="like">
          <SearchAlgorithmLike />
        </div>
        <div v-else>
          <SearchAlgorithmDate />
        </div>        
      </div>
    </div>

  </div>
</template>

<script>
import SearchAlgorithmDate from "@/components/article/SearchAlgorithmDate.vue"
import SearchAlgorithmLike from "@/components/article/SearchAlgorithmLike.vue"
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  components: {
    SearchAlgorithmDate,
    SearchAlgorithmLike
  },
  data() {
    return{
      isOneAlgo: false,
      includeAlgo: '',
      excludeAlgo: '',
      language: '',
      followingNumber: '',
      articleNumber: '',
      page: 0,
      params: '',
      articleCount: 0 ,
      problem: '',
      followingState: false, 
      like: false,
    }
  },
  computed: {
    getArticle: function() {
      return this.$store.getters.getSearchArticle
    },
    isLogin: function() {
      const token = sessionStorage.getItem('jwt')
      if (token){
        return true
      }
      else{
        return false
      }
    }
  },

  created() {
    const data = this.getArticle

    this.language = data.language
    this.includeAlgo = data.includeAlgo
    this.excludeAlgo = data.excludeAlgo

    if (this.includeAlgo.length == 1 && this.excludeAlgo.length==0){
      this.isOneAlgo = true
    }

    let and = ''
    data.includeAlgo.forEach(element => {
      and += element+','
    });
    
    let not = ''
    data.excludeAlgo.forEach(element => {
      not += element+','
    })

    and = and.substring(0,and.length-1)
    not = not.substring(0,not.length-1)

    let params = {
      language: this.language,
      and: and,
      not: not,      
    }

    if (not == ''){
      delete params.not
    }
    if (and == ''){
      delete params.and
    }
    if (params.language == 'null'){
      delete params.language
    }

    this.$store.dispatch('createSearchParams', params)
    this.params = params
    this.requestProblem(params)
  },

  methods: {
    follow() {
      this.followingState = !this.followingState
      if(this.followingState){
        this.followingNumber += 1
        this.$swal(`'${this.includeAlgo[0]}' 알고리즘을 팔로우 하셨습니다.`)
      }
      else{
        this.followingNumber -= 1
        this.$swal(`'${this.includeAlgo[0]}' 알고리즘을 팔로우 취소 하셨습니다.`)
      }
      const data = {
        algorithm: this.includeAlgo[0]
      }
      axios({
        method: 'post',
        url: `${SERVER_URL}/follow/algorithm`,
        headers: this.getToken(),
        data: data
      })
      .then(()=>{
      })
      .catch(err=>{
        console.log(err)
      })
    },
    clickOrderByLike() {
      this.like = true
      const like = document.querySelector('.order-by-like')
      const date = document.querySelector('.order-by-new')
      like.style.color = 'black'
      like.style.fontSize = '18px'
      date.style.color = 'lightgray'
      date.style.fontSize = '16px'

    },
    clickOrderByNew() {
      this.like = false
      const like = document.querySelector('.order-by-like')
      const date = document.querySelector('.order-by-new')
      like.style.color = 'lightgray'
      like.style.fontSize = '16px'
      date.style.color = 'black'
      date.style.fontSize = '18px'
    },
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
    requestProblem(params) {
      axios({
        method: "get",
        url: `${SERVER_URL}/search/article/algorithm` + "?page=" + this.page,
        headers: this.getToken(),
        params: params,
      })
      .then((res) => {
        this.articleCount = res.data.articleSearchCount
        if(this.isOneAlgo){
          this.followingState = res.data.followInfo.followingState
          this.followingNumber = res.data.followInfo.followingNumber;
          this.articleNumber = res.data.followInfo.articleNumber;
        }        
      })
      .catch((err) => {
        console.log(err);
      });        
    },
    
  }
}
</script>

<style scoped>
.top{
  margin-top: 30px;
  font-size: 50px;
  font-weight: bold;
}
.member-name{
  font-size:20px;
}
.article-content{
  -webkit-box-shadow : 0 0px 10px rgba(0, 0, 0, 0.08);
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.12);
  width: 100%;
  padding: 10px 20px;
  border-radius: 4px;
}
.article-content:hover{
  -webkit-box-shadow: 0 0px 20px rgba(161, 212, 226, 0.6);
  box-shadow: 0 0px 20px rgba(161, 212, 226, 0.6);
  transform: scale(1.1);
  cursor: pointer;
}
.article-title{
  font-size: 30px;  
}
.hashtag{
  display: inline-block;
  font-size: 13px;
  border-radius: 3px;
  margin-right:3px;
  padding: 1px 3px;
  margin-bottom: 4px;
}
.fa-heart{
  color: red;
}
.order-by-new{
  cursor: pointer;
  color: black;
  font-size:18px;
}
.order-by-like{
  cursor: pointer;
  color: lightgray;
}
.has-category{
  background-color:rgba(170, 224, 217) ;
  font-weight: bold;
}
.has-problem{
  background-color:rgb(97, 209, 209) ;
}
.has-language{
  background-color:rgb(126, 208, 233) ; 
}
.has-algo{
  background-color: rgba(186,184,189,1);
}
.follow-btn{
  font-size:20px;
  background-color: rgb(39, 235, 32);
  padding:4px 12px;
  border-radius: 4px;
  color:white;
  cursor: pointer;
}
.follow-btn:hover{
  background-color: rgb(11, 209, 44);
  padding:5px 16px;
  font-size:22px
}
.follow-cancel-btn{
  font-size:20px;
  background-color: white;
  border: 1px solid rgb(0, 189, 25);
  padding:3px 12px;
  border-radius: 4px;
  color:rgb(41, 207, 35);
  cursor: pointer;
}
.follow-cancel-btn:hover{
  font-size:22px;
  padding:5px 16px;
}
</style>