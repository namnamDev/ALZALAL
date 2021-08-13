<template>
  <div class="animate__animated animate__fadeInUp my-4 main">

    <div class="article-box col-lg-8 col-md-10 col-sm-9 col-10" @click="clickArticle">
      <div class="row">
        <div class="col-2 image">
          <img class="profileImg" :src="getImgSrc" @error="imageError = true" alt="프로필사진">
        </div>
        <div class="col">
          <div class="row">
            <div class="col fs-6 fw-bold">
              <span class="member-name">{{item.member.name}}</span>
            </div>
            <div class="col text-end">
              <span class="text-secondary">{{date}}</span>
            </div>
          </div>
          <div class="row">
            <div>
              <div class="fs-5">{{ item.articleTitle }}</div>
              <div class="hashtag d-flex align-items-center">
                <span class="has-category">QnA</span>
                <span class="has-problem">{{item.problemSite.problemSiteName}} {{item.problemSite.problemNo}}</span>                
                <span class="has-language">{{item.useLanguage}}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col content">
          <Viewer id="viewer" :viewerText="item.articleContent" />
        </div>
      </div>

      <div class="row bottom mt-0 mt-sm-4">
        <div class="col like-comment">
          <i class="fas fa-heart me-2" v-if="item.likeState"></i>
          <i class="far fa-heart me-2" v-else></i>
          <span>{{item.likeCount}}</span>
          <i class="far fa-comment-dots mx-2"></i>
          <span >{{item.commentCount}}</span>
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
    item: Object,
  },
  computed:{
    getImgSrc(){
      return `${SERVER_URL}/profile/img/${this.item.member.no}`;
    }
  },
  data:function() {
    return{
      memberNo: '',
    }
  },
  created() {
    this.date = this.getDate(this.item.articleDate)
  },
  methods: {
    //몇분전 표기
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
    // 상세 정보 페이지이동
    clickArticle: function(clickObject) {
      if(clickObject.target.getAttribute("class")=="profileImg" ||  clickObject.target.getAttribute("class")=="member-name"){
        localStorage.setItem('userPk',this.item.member.no)
        this.$router.push({'name':'profilePage', params:{userPk:this.item.member.no}})
      }else{
        localStorage.setItem('articleNo', this.item.articleNo)
        axios({
          method: 'get',
          url: `${SERVER_URL}/comment/article/${this.item.articleNo}`,
        })   
        .then(res =>{
          this.$store.dispatch('createArticleComment',res.data.articleComments)
        })
        .catch(err =>{  
          console.log(err)
        })

      this.$router.push({name : 'articleDetail',  params:{'Page':'0'}})
        // this.$router.push({name : 'articleDetail', params:{'Page':'0'}})
      }
      // localStorage.setItem('articleNo', this.item.articleNo)
      // this.$store.dispatch('createArticleDetail',this.item)

      // 댓글 정보 요청후 store에 저장
    },   
  }
};
</script>

<style scoped>
.fa-heart {
  color: rgba(62 ,171 ,111 , 1);
}
.feed {
  margin-top: 13vw;
}
.article-content {
  background: white;
  -webkit-box-shadow: 0 0px 15px rgba(0, 0, 0, 0.08);
  box-shadow: 0 0px 15px rgba(0, 0, 0, 0.08);
  border-radius: 1px;
  padding: 15px 15px;
  /* height: 400px; */
  cursor: pointer;
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

.image{
  text-align: center;
  align-self: center;
}
.top{
  /* height:100px; */
}
.name{
  height:25px;
  margin-top:10px;
}
.title{
  height:30px;
  font-size: 25px;
  margin-bottom: 0;
}
.middle{
  margin-top: 10px;  
  padding: 0 20px;
  /* height: 210px; */
}
.content{
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 6; /* 라인수 */
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
  height:100%;
  margin: 0 1rem 0 1rem;

}

.like-comment{
  text-align: end;
}
.hashtag > span{
  font-size: 14px;
  border-radius: 3px;
  /* background-color : rgba(221,223,230,1); */
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
.name{
  display: inline-block;
}
.name:hover{
  font-size:18px;
  cursor:pointer;
}

.member-name{
  cursor: pointer;
}

</style>