<template>
  <div>
    <div class="row notify-item py-3" @click="clickItem">
      <div class="col-2">
        <i class="fas fa-user" style="font-size: 50px"></i>
      </div>
      <div class="col-9">
        <span class="message">{{message}}</span>
        <br>
        <span class="notiDate">{{notiDate}}</span>
      </div>
      <div class="col-1" v-if="!notiReadStatus">
        <i class="fas fa-circle"></i>
      </div>
      
    </div>
    
  </div>

</template>

<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  props:{
    item: Object,
  },
  computed: {
    message() {
      const who = this.item.notiSenderNM
      const what = this.item.notiSubTaskClass
      let message = ''
      switch (what) {
        case 'NMF' :
          message = `${who}님이 팔로우를 하였습니다.`
          break
        case 'NAC' :
          message = `${who}님이 게시글에 댓글을 작성하였습니다.`
          break
        case 'NHC' :
          message = `${who}님이 풀이요청에 댓글을 작성하였습니다.`
          break
        case 'NAL' :
          message = `${who}님이 게시글을 좋아합니다.`
          break
        case 'NHL' :
          message = `${who}님이 풀이요청 게시글을 좋아합니다.`
          break
        case 'NACL' :
          message = `${who}님이 댓글을 좋아합니다.`
          break
        case 'NHCL' :
          message = `${who}님이 풀이요청 댓글을 좋아합니다.`
          break
        case 'NDCL' :
          message = `${who}님이 토론 댓글을 좋아합니다.`
          break
        case 'NHS' :
          message = `${who}님이 문제풀이를 요청하였습니다.`
          break
        case 'NHA' :
          message = `${who}님이 문제풀이 요청을 수락하였습니다.`
          break
        case 'NHR' :
          message = `${who}님이 문제풀이 요청을 거절하였습니다.`
          break
        case 'NHE' :
          message = `${who}님이 문제풀이 요청에 답변을 완료하였습니다.`
          break
      }
      return message
    }
  },
  data() {
    return{
      notiTaskClass : {
        'FOL' : '팔로우를 하였습니다.',
        'COM' : '댓글을 작성하였습니다.',
        'LIK' : '좋아요를 눌렀습니다.',
        'HLP' : '풀이요청'
      },
      notiSubTaskClass: {
        'NMF': '유저 팔로우',
        'NAC': '일반게시글 댓글',
        'NHC': ' 풀이요청 댓글',
        'NAL': ' 일반게시글 좋아요',
        'NHL': ' 풀이요청게시글 좋아요',
        'NACL': ' 일반댓글 게시글 좋아요',
        'NHCL': ' 풀이요청 댓글 좋아요',
        'NDCL': ' 토론댓글 좋아요',
        'NHS': ' 풀이요청 요청',
        'NHA': ' 풀이요청 수락',
        'NHR': ' 풀이요청 거절',
        'NHE': ' 풀이요청 답변완료',
      },
      notiNo: this.item.notiNo,
      notiReadStatus: this.item.notiReadStatus,
      notiDate: ''
    }
  },
  created() {
    this.notiDate = this.getDate(this.item.notiDate)
  },
  methods: {
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
    clickItem: function() {
      console.log(this.item)
      const data = {
        notiNo : this.item.notiNo
      }
      axios({
        method: 'post',
        url: `${SERVER_URL}/notiRead`,
        headers: this.getToken(),
        data : data
      })
      .then(()=>{
        this.notiReadStatus = true
      })
      .catch(err => {
        console.log(err)
      })
      if(this.item.notiSubTaskClass == 'NHS'){
        const token = sessionStorage.getItem('jwt')
        const userpk=jwt_decode(token).sub;
        localStorage.setItem("userPk",userpk)     
        location.href=`/profilePage/${userpk}`
      }
      else if (this.item.notiSubTaskClass != 'NMF' ){
        localStorage.setItem('articleNo', this.item.notiTargetNo)
        this.$router.push({name : 'articleDetail', params:{'Page':'0'}})
      }
      else{
        localStorage.setItem('userPk',this.item.notiSender)
        this.$router.push({'name':'profilePage', params:{userPk:this.no}})
      }
    }, 

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
  }
}
</script>

<style scoped>
.message{
  font-size: 16px;
}
.notiDate{
  font-size: 14px;
  color:rgb(27, 218, 43);
}
.fa-circle{
  font-size: 12px;
  color: rgb(62,171,111);
}
.notify-item:hover{
  background-color: rgba(62,171,111, 0.2);
  border-radius: 5px;
  cursor:pointer;
}
</style>