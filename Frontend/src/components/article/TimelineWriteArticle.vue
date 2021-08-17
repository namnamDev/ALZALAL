<template>
 <div class="animate__animated animate__fadeInUp my-4 main">
    <div class="article-box col-lg-6 col-md-8 col-sm-7 col-8 " v-if="isLogin">
      <div class="row">
        <div class="col-2 image">
          <img class="profileImg" :src="imgsrc" @error="imageError = true" alt="프로필사진">
        </div>
        <div class="col writeContent"  @click="clickCreate">
              어떤 문제를 푸셨나요?
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import jwt_decode from 'jwt-decode'
const token = sessionStorage.getItem('jwt')
const SERVER_URL = process.env.VUE_APP_SERVER_URL
// let username = '';
let userpk = '';
if (token) {
  const decoded = jwt_decode(token)
  //userpk = decoded.sub
//   username = decoded.name
  userpk = decoded.sub
}
export default {
    data(){
        return{
        imgsrc: `${SERVER_URL}/profile/img/${userpk}`
        }
    },
    computed: {
        isLogin(){
            return this.$store.getters.isLogin
        }, 
    },
    methods: {
        clickCreate: function() {
            this.$router.push({'name':'createArticle'})      
        },
    },
}
</script>

<style>
.main{
    display: flex;
    justify-content: center;
}
.submain{
    display: flex;
    justify-content: space-between;
    
}
.profileImg {
    width: 45px;
    height: 45px;
    border-radius: 75%;
}
.article-box {
  background: white;
  box-shadow: 0 0 0px 0.7px gray;
  border-radius: 5px;
  padding: 15px 15px;
  /* height: 400px; */
}
.writeContent{
    color: rgb(0, 0, 0);
    background-color: rgb(233, 233, 233);
    display: flex;
    align-items: center;
    border-radius: 20px;
    margin-right: 30px;
    justify-content: center;
    cursor: pointer;
}
</style>