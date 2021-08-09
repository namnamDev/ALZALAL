<template>
  <div class="row my-5">
    <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
    <div class="article-box col-lg-6 col-md-10 col-sm-9 col-10">
      <div class="row top mt-2">
        <div class="col-3 image mt-2">
          <i class="fas fa-user" style="font-size: 60px"></i>
        </div>
        <div class="col">
          <div class="row name" @click="clickName">
            <p>{{ this.memberName }}</p>
          </div>
          <div class="row mt-2">
            <div>
              <div class="title mb-1">{{ this.articleTitle }}</div>
              <div class="hashtag">
                <span v-if="articleClass=='A01'" class="has-category">문제풀이</span>
                <span v-else class="has-category">QnA</span>
                <span class="has-problem">{{ this.problemSite }} {{ problemNo }}</span>
                <span class="has-language">{{ this.language }}</span>
                <span class='mt-2 has-algo' v-for="algo,idx in algoList" :key="idx">{{algo}}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row middle" @click="clickArticle">
        <div class="col content">
          <Viewer id="viewer" :viewerText="content" />
        </div>
      </div>

      <div class="row bottom mt-0 mt-sm-4">
        <div class="col like-comment">
          <i class="fas fa-heart me-2" v-if="likeState"></i>
          <i class="far fa-heart me-2" v-else></i>
          <span>{{this.likeCount}}</span>
          <i class="far fa-comment-dots mx-2"></i>
          <span >{{this.commentCount}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Viewer from '@/components/article/ThumbnailViewer.vue'
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  components: {
    Viewer,
  },  
  props: {
    articleNo: Number,
    content: String,
  },
  computed:{
    Content: function() {
      return this.content
    },
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
  },
  data: function() {
    return{
      articleClass: '',
      articleTitle: '',
      date: '',
      commentCount: '',
      likeCount: '',
      likeState: '',
      memberName: '',
      memberNo: '',
      problemSite: '',
      problemNo: '',
      language: '',
      item: null,
      algoList: null,
    }
  },

  mounted() {
    axios({
        method: 'get',
        url: `${SERVER_URL}/article/article/${this.articleNo}`,
        headers: this.getToken,
      })   
      .then(res =>{
        const detail = res.data.articleDetail
        this.item = res.data
        this.articleClass= detail.articleClass,
        this.articleTitle= detail.articleTitle,
        this.date= detail.articleDate,
        this.commentCount= detail.commentCount,
        this.likeCount= detail.likeCount,
        this.likeState= detail.likeState,
        this.memberName= detail.member.name,
        this.memberNo= detail.member.no,
        this.problemSite= detail.problemSite.problemSiteName,
        this.problemNo= detail.problemSite.problemNo,
        this.language= detail.useLanguage
        this.algoList = detail.algo
      })
      .catch(err =>{  
        console.log(err)
      })
  },
  methods: {    
    // 게시글 상세 정보 페이지로 이동
    clickArticle: function() {
      localStorage.setItem('articleNo', this.articleNo)
      this.$router.push({name : 'articleDetail', params:{'Page':'0'}})
    },
    clickName: function(){
      localStorage.setItem('userPk',this.memberNo)
      this.$router.push({'name':'profilePage', params:{userPk:this.memberNo}})
    }   
    
  },
};
</script>

<style scoped>
.fa-heart {
  color: red;
}
#viewer {
  height: 100%;
}
.feed {
  margin-top: 13vw;
}
.article-box {
  background: white;
  -webkit-box-shadow: 0 0px 15px rgba(0, 0, 0, 0.08);
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.12);
  border-radius: 5px;
  padding: 15px 15px;
  /* height: 400px; */
}
.article-box:hover {
  -webkit-box-shadow: 0 0 10px rgba(161, 212, 226, 0.6);
  box-shadow: 0 0 10px rgba(161, 212, 226, 0.6);
  transform: scale(1.05);
}

.image {
  text-align: center;
  align-self: center;
}
.top {
  /* height: 140px; */
}
.name {
  height: 25px;
  margin-top: 10px;
}
.title {
  height: 30px;
  font-size: 25px;
  margin-bottom: 0;
}
.middle {
  margin-top: 10px;
  padding: 0 20px;  
  /* height: 210px; */
}
.content {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 6; /* 라인수 */
  -webkit-box-orient: vertical;
  word-wrap: break-word;
  height: 100%;
  cursor:pointer;
}

.like-comment {
  text-align: end;
}
.hashtag > span {
  font-size: 13px;
  border-radius: 3px;
  /* background-color: rgba(221, 223, 230, 1); */
  padding:0px 8px;
  margin-right: 6px;
  display:inline-block;
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


@media (max-width: 767px) {
  .top {
    margin-bottom: 30px;
  }

}
</style>

