<template>
  <div class="comment mb-2">
    <div class="user">
      <div class="row">
        <div class="col">
          <div class="inline">
            {{this.memberName}}
          </div >
          <div class="inline">            
            | {{this.date | moment("YYYY-MM-DD HH:mm:ss")}}
          </div>
          <div class="inline">
            <i class="fas fa-heart ms-2" v-if="likeState"></i>
            <i class="far fa-heart ms-2" v-else></i>
            <span class="" >
              {{likeCount}}
            </span>
          </div>
        </div>
        <div class="col text-end pe-3" @click="clickLike">
          <span class="like-btn">좋아요</span>
        </div>
      </div>
    </div>
    <div class="content">
      {{this.content}}
      <Viewer :viewerText="this.content"/>
    </div>

  </div>
</template>

<script>
import Viewer from '@/components/comment/CommentViewer.vue'

import Vue from "vue";
import vueMoment from "vue-moment";
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL
Vue.use(vueMoment);

export default {
  components: {
    Viewer
  },
  props: {
    comment: Object
  },

  data() {
    return {
      memberName: this.comment.member.name,
      date: this.comment.articleCommentDate,
      likeCount: this.comment.likeCount,
      content: this.comment.content,
      likeState: this.comment.likeState
    }
  },


  methods: {
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
    clickLike() {      

      const commentNo = this.comment.articleCommentNo
      axios({
        method: 'post',
        url: `${SERVER_URL}/like/article/comment/${commentNo}`,
        headers: this.getToken(),
        data :{}
      })
      .then(res=> {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
      if (this.likeState) {
        this.likeState = false
        this.likeCount -= 1
      }
      else{
        this.likeState = true
        this.likeCount += 1
      }
    }
  },
}
</script>

<style scoped>
.content{
  /* border: 1px solid black; */
  padding: 10px;
  border-radius: 6px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.08);
}
.inline{
  display:inline;
}
.fa-heart{
  color: red;
}
.like-btn{
  cursor: pointer;
  color:rgb(70, 172, 235);
}
</style>