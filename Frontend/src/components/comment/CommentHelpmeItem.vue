<template>
  <div class="comment mb-2">
    <div class="user">
      <div class="row">
        <div class="col">
          <div class="inline">
            {{this.memberName}}
          </div >
          <div class="inline">            
            | {{this.date}}
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
      <div class="col">
        <Viewer :viewerText="this.content"/>
      </div>
      <div class="col text-end" v-if="isMine" >
        <span class="delete" @click="deleteComment">삭제</span>
      </div>
    </div>

  </div>
</template>

<script>
import Viewer from '@/components/comment/CommentViewer.vue'

import axios from 'axios';
import jwt_decode from 'jwt-decode'

const SERVER_URL = process.env.VUE_APP_SERVER_URL


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
      // date: this.comment.articleCommentDate,
      likeCount: this.comment.likeCount,
      content: this.comment.content,
      likeState: this.comment.likeState
    }
  },

  computed: {
    date: function() {
      return this.getDate(this.comment.helpmeCommentDate)
    },
    isMine: function() {
      const token = localStorage.getItem('jwt')
      let username = '';
      if (token) {
        const decoded = jwt_decode(token)
        username = decoded.name
        if (username == this.memberName){
          return true
        }
        else{
          return false
        }
      }
      return false
    }
  },

  methods: {
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
        url: `${SERVER_URL}/like/helpme/comment/${commentNo}`,
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
    },
    deleteComment: function() {      
      this.$swal.fire({
        title: '댓글을 삭제하시겠습니까?',
        text: "삭제하시면 다시 복구시킬 수 없습니다.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
      }).then((result) => {
        if (result.value) {          
          axios({
            method: 'delete',
            url: `${SERVER_URL}/comment/article/${this.comment.articleCommentNo}`,
            headers: this.getToken(),
          })
          .then(()=>{           
            location.reload()
          })
          .catch(err=>{
            console.log(err)
          })
        }
      })
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
.like-btn:hover{
  font-size:17px;
  color:rgb(56, 74, 245);
}
.delete{
  color:red;
  cursor: pointer;
}
</style>