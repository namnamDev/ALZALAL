<template>
  <div class="row my-5" @click="clickArticle">
    <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
    <div class="box col-lg-6 col-md-10 col-sm-9 col-10">

      <div class="row top mt-2">
        <div class="col-3 box1 image mt-2">
          <i class="fas fa-user" style="font-size:60px;"></i>
        </div>
        <div class="col box1">
          <div class="row box1 name">
            <p>{{item.member.name}}</p>
          </div>
          <div class="row box1 mt-2">
            <div>
              <div class="title mb-1">{{item.articleTitle}}</div>
              <div class="hashtag">
                <span>{{item.problemSite.problemSiteName}} {{item.problemSite.problemNo}}</span>                
                <span>{{item.useLanguage}}</span>
                <span>알고리즘 추가</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row middle">
        <div class="col box1 content">
          <Viewer id="viewer" :viewerText="item.articleContent" />
        </div>
      </div>

      <div class="row bottom mt-4">
        <div class="col box1 like-comment">
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
  methods: {
    // 상세 정보 페이지이동
    clickArticle: function() {
      localStorage.setItem('articleNo', this.item.articleNo)
      this.$store.dispatch('createArticleDetail',this.item)

      // 댓글 정보 요청후 store에 저장
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

      this.$router.push({name : 'articleDetail',})
    },   
  }
};
</script>

<style scoped>
.fa-heart {
  color: red;
}
.feed {
  margin-top: 13vw;
}
.box {
  background: white;
  -webkit-box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  border-radius: 1px;
  padding: 15px 15px;
  height: 400px;
}
.box:hover{
  box-shadow: 0 20px 20px rgba(161,212,226,0.6);
  transform: scale(1.1);
  cursor: pointer;
}
.box1 {
  /* border: 1px solid black; */
}
.image{
  text-align: center;
}
.top{
  height:100px;
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
  height: 210px;
}
.content{
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 6; /* 라인수 */
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
  height:100%;
}

.like-comment{
  text-align: end;
}
.hashtag > span{
  font-size: 16px;
  border-radius: 3px;
  background-color : rgba(221,223,230,1);
  padding:1px 8px;
  margin-right: 6px;
}
.hashtag{
  height:20px;
}
</style>