<template>
  <div class="animate__animated animate__fadeInUp my-4 main">

    <div class="article-box col-lg-8 col-md-10 col-sm-9 col-10" @click="clickArticle">
      <div class="row">
        <div class="col-2 image">
          <img class="profileImg" :src="getImgSrc" @error="imageError = true" alt="프로필사진">
        </div>
        <div class="col">
          <div class="row" >
            <div class="col fs-6 fw-bold">
              <span class="member-name">{{ this.memberName }}</span>
            </div>
            <div class="col text-end">
              <span class="text-secondary">{{date}}</span>
            </div>
          </div>
          <div class="row">
            <div>
              <div class="fs-5">{{ this.articleTitle }}</div>
              <div class="hashtag d-flex align-items-center">
                <span v-if="articleClass=='A01'" class="has-category">문제풀이</span>
                <span v-else class="has-category">QnA</span>
                <span class="has-problem">{{ this.problemSite }} {{ problemNo }}</span>
                <span class="has-language">{{ this.language }}</span>
                <span class='has-algo ' v-for="algo,idx in algoList" :key="idx">{{algo}}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col content ">
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
    getImgSrc(){
      return `${SERVER_URL}/profile/img/${this.memberNo}`;
    }
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
        this.commentCount= detail.commentCount,
        this.likeCount= detail.likeCount,
        this.likeState= detail.likeState,
        this.memberName= detail.member.name,
        this.memberNo= detail.member.no,
        this.problemSite= detail.problemSite.problemSiteName,
        this.problemNo= detail.problemSite.problemNo,
        this.language= detail.useLanguage
        this.algoList = detail.algo

        const date = res.data.articleDetail.articleDate

        this.date = this.getDate(date)
      })
      .catch(err =>{  
        console.log(err)
      })
  },
  methods: {    
    // 몇 분전 표기   
    getDate: function(date) {
      const today  = new Date()
      const timeValue  = new Date(date)

      const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
      if (betweenTime < 1) return '방금전';
      if (betweenTime < 60) {
          return `${betweenTime}분전`;
      }

      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
          return `${betweenTimeHour}시간전`;
      }

      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
          return `${betweenTimeDay}일전`;
      }

      return `${Math.floor(betweenTimeDay / 365)}년전`;

    },
    // 게시글 상세 정보 페이지로 이동
    clickArticle: function(clickObject) {
      console.log("tmpasdf ",clickObject)
      console.log("tmpasdfsad ",clickObject.target)      
      console.log("tmpasdfsadasdf ",clickObject.target.getAttribute("class"))
      if(clickObject.target.getAttribute("class")=="profileImg" ||  clickObject.target.getAttribute("class")=="member-name"){
        localStorage.setItem('userPk',this.memberNo)
        this.$router.push({'name':'profilePage', params:{userPk:this.memberNo}})
      }else{
        localStorage.setItem('articleNo', this.articleNo)
        this.$router.push({name : 'articleDetail', params:{'Page':'0'}})
      }
    }
    
  },
};
</script>

<style scoped>
.fa-heart {
  color: black;
}
.fa-comment-dots{
  color:black
}
#viewer {
  height: 100%;
}
.feed {
  margin-top: 13vw;
}
.article-box {
  background: white;

  box-shadow: 0 0 0px 0.7px gray;
  border-radius: 5px;
  padding: 15px 15px;
  /* height: 400px; */
  cursor: pointer;
}
.article-box:hover {
  box-shadow: 0 0 0px 5px rgba(62 ,171 ,111 , 1);
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
  margin: 0 1rem 0 1rem;
}

.like-comment {
  text-align: end;
}
.hashtag > span {
  font-size: 13px;
  border-radius: 3px;
  /* background-color: rgba(221, 223, 230, 1); */
  padding:4px 8px;
  margin-right: 6px;
  display:inline-block;
}
.has-category{
  padding-top: 2px;
  padding-bottom: 2px;
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
.member-name{
  cursor: pointer;
}
.member-name:hover{
  font-size:18px;
}
.profileImg {
    width: 75px;
    height: 75px;
    border-radius: 75%;
}

@media (max-width: 767px) {
  .top {
    margin-bottom: 30px;
  }

}
</style>

