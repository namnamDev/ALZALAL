<template>
  <div class="container" >
    <div class="row main">

      <div class="col-lg-8 col-md-10 col-sm-9 col-10">

        <div class="main">
        <div class="col-lg-10 col-md-10 col-sm-9 col-10">
          <div class="row top">
            <div class="col">
              <div v-if="problem">
                #{{ problem }} 
                <span class="follow-cancel-btn" @click="follow" v-if="followingState">팔로우취소</span>
                <span class="follow-btn" @click="follow" v-else>팔로우</span>
              </div>
            </div>
          </div>

          <div class="row middle mb-2">
            <div class="col">
              <div style="display: inline" class="me-3" v-if="followingNumber">
                팔로잉 {{ followingNumber }}
              </div>
              <div style="display: inline" class="me-3" v-else>팔로잉 0</div>
              <div style="display: inline" class="me-3" v-if="articleNumber">
                게시글 {{ articleNumber }}
              </div>
              <div style="display: inline" class="me-3" v-else>게시글 0</div>
            </div>
          </div>

          <div class="row mb-2">
            <div class="col">
              <span class="include-algo me-2" v-for="item, idx in includeAlgo" :key="idx">{{item}}</span>
            </div>
          </div>
          <div class="row mb-4">
            <div class="col">
              <span class="exclude-algo me-2" v-for="item, idx in excludeAlgo" :key="idx">{{item}}</span>
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
        <div v-if="!like">
          <SearchProblemDate />
        </div>
        <div v-else>
          <SearchProblemLike />
        </div>

        

      </div>
    </div>

    
  </div>
</template>

<script>
import SearchProblemDate from "@/components/article/SearchProblemDate.vue"
import SearchProblemLike from "@/components/article/SearchProblemLike.vue"
import axios from "axios";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    SearchProblemDate,
    SearchProblemLike
  },
  data() {
    return {
      includeAlgo: "",
      excludeAlgo: "",
      problem: "",
      problemSite: '',
      problemNo: '',
      language: "",
      followingNumber: "",
      articleNumber: "",
      page: 0,
      params: '',
      articleCount: 0 ,   
      followingState: false,   
      like: false,
    };
  },
  computed: {
    getArticle: function () {
      return this.$store.getters.getSearchArticle;
    },
  },
  methods: {
    follow() {
      this.followingState = !this.followingState
      if(this.followingState){
        this.followingNumber += 1
        this.$swal(`'${this.problem}' 문제를 팔로우 하셨습니다.`)
      }
      else{
        this.followingNumber -= 1
        this.$swal(`'${this.problem}' 문제를 팔로우 취소 하셨습니다.`)
      }
      const data = {
        problemSite: this.problemSite,
        problemNo: this.problemNo,
      }
      axios({
        method: 'post',
        url: `${SERVER_URL}/follow/problem`,
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
      const like = document.querySelector('.order-by-like')
      const date = document.querySelector('.order-by-new')
      like.style.color = 'black'
      like.style.fontSize = '18px'
      date.style.color = 'lightgray'
      date.style.fontSize = '16px'
      this.like = true
    },
    clickOrderByNew() {
      const like = document.querySelector('.order-by-like')
      const date = document.querySelector('.order-by-new')
      like.style.color = 'lightgray'
      like.style.fontSize = '16px'
      date.style.color = 'black'
      date.style.fontSize = '18px'
      this.like = false
    },
    getToken() {
      const token = sessionStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },
    requestProblem(params) {
      axios({
        method: "get",
        url: `${SERVER_URL}/search/article/problem` + "?page=" + this.page,
        headers: this.getToken(),
        params: params,
      })
      .then((res) => {
        this.followingState = res.data.followInfo.followingState
        this.articleCount = res.data.articleSearchCount
        this.followingNumber = res.data.followInfo.followingNumber;
        this.articleNumber = res.data.followInfo.articleNumber;          
      })
      .catch((err) => {
        console.log(err);
      });        
    },

    
  },
  mounted() {
      
    const data = this.getArticle;

    this.problem = data.problem;
    this.problemSite = data.problemSite;
    this.problemNo = data.problemNo;
    this.includeAlgo = data.includeAlgo;
    this.excludeAlgo = data.excludeAlgo;
    this.language = data.language;

    let and = "";
    data.includeAlgo.forEach((element) => {
      and += element + ",";
    });

    let not = "";
    data.excludeAlgo.forEach((element) => {
      not += element + ",";
    });

    and = and.substring(0, and.length - 1);
    not = not.substring(0, not.length - 1);

    let params = {
      problem: data.problem,
      language: data.language,
      and: and,
      not: not,
    };
    if (not == "") {
      delete params.not;
    }
    if (and == "") {
      delete params.and;
    }
    if (params.language == "null") {
      delete params.language;
    }
    this.$store.dispatch('createSearchParams', params)

    this.params = params
    this.requestProblem(params);
  },
};
</script>

<style scoped>
.top {
  margin-top: 30px;
  font-size: 50px;
  font-weight: bold;
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

.include-algo{
  background-color: rgba(161,212,226,1);
  border-radius: 5px;
  padding: 1px 3px;
  font-size: 15px;
  font-weight: bold;
  display: inline-block;
}
.exclude-algo{
  background-color: rgb(246, 163, 142);
  border-radius: 5px;
  padding: 1px 3px;
  font-size: 15px;
  font-weight:bold;
  display: inline-block
}
.follow-btn{
  font-size:20px;
  background-color: rgb(0, 153, 255);
  padding:4px 12px;
  border-radius: 4px;
  color:white;
  cursor: pointer;
}
.follow-btn:hover{
  background-color: rgb(0, 89, 255);
  padding:5px 16px;
  font-size:22px
}
.follow-cancel-btn{
  font-size:20px;
  background-color: white;
  border: 1px solid blue;
  padding:3px 12px;
  border-radius: 4px;
  color:rgb(59, 121, 223);
  cursor: pointer;
}
.follow-cancel-btn:hover{
  /* background-color: rgb(0, 89, 255); */
  font-size:22px;
  padding:5px 16px;
}
.main{
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>