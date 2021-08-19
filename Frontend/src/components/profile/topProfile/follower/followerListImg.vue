<template>
  <div class="row">
      <!-- props로 상위컴포넌트에서 유저pk 유저이름 받아오기 -->
      <div class="userImg col-3 col-sm-3 col-lg-3">
          <img class="profileImg"  :src="imgsrc" alt="Img">
          
      </div>
      <div class="user col-7 col-sm-7 col-lg-7">
          <span class="userName">
          <button class="btn" @click="clickName">{{name}}</button>
          </span>
      </div>
      <div class="user col-2 col-sm-2 col-lg-2" align="right">
          <span class="followBtn" v-if="!this.isLogin && !this.me">
            <button class="btn1 btn-unfollow" v-if="this.followState"  @click="clickFollow($event)">unfollow</button>
            <button class="btn1 btn-follow" v-if="!this.followState"  @click="clickFollow($event)">follow</button>
          </span>
      </div>      
  </div>
</template>

<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'
const SERVER_URL = process.env.VUE_APP_SERVER_URL
const token = sessionStorage.getItem('jwt')
let userpk = '';
if (token) {
  const decoded = jwt_decode(token)
  userpk = decoded.sub
}
export default {
    props:{
        name: String,
        no: Number,
        followState: Boolean
    },
    data(){
        return{
            // imgsrc: `${SERVER_URL}/profile/img/${this.no}`,
            imgsrc: `${SERVER_URL}/profile/img/${this.no}`,
            myPage: '',
            isLogin: '',
            me: '',
            
        }
    },
    created: function() {
      
      const userPk = localStorage.getItem("userPk")
      if(userPk){
        this.myPage = false
          if(userpk==userpk){
              this.myPage = true
          }
      }else{
          this.myPage = true
      }
      if(this.token){
        this.isLogin = true
      }else{
        this.isLogin = false
      }
      if(this.no == userpk){
        this.me=true
      }else{
        this.me=false
      }


    },
    computed: {
        getToken(){
          const token = sessionStorage.getItem('jwt')
          const config = {
              Authorization: `Bearer ${token}`
          }
          return config
        },

	},
    methods:{
      click() {
        console.log(this.imgsrc)
      },
        clickFollow: function(event){
            const token = sessionStorage.getItem('jwt')
                if(!token){
                  this.$swal('로그인이 필요합니다.')
                  this.$router.push({name:'login'})
                }else{

            axios({
                    method: 'post',
                    url: `${SERVER_URL}/follow/member`,
                    data: {
                        "memberNo": this.no,
                    },
                    headers: this.getToken,
                })
                .then({      
                })
                .catch({
                })
                
            if(event.target.innerText == 'follow' ){
              event.target.innerText = 'Unfollow'
              event.target.style.backgroundColor='#FFFFFF'
              event.target.style.color="black"
              
            }else{
              event.target.innerText = 'follow'
              
              event.target.style.backgroundColor='rgb(62, 171, 111)'
              event.target.style.color="white"
            }
          }
        },
        clickName: function(){
          localStorage.setItem('userPk', this.no)
          this.$router.push({'name':'profilePage', params:{ userPk:this.no }})
        }

    }
}
</script>

<style scoped>
.row:hover{
  box-shadow: 0 0 0px 5px rgba(62 ,171 ,111 , 1);
}
.row{
  margin-bottom: 20px;
}
.userImg {
    width: 75px;
    height: 75px; 
    border-radius: 75%;
    overflow: hidden;
    display: flex;
    margin: 0px 0px 0px 0px;
}
.profileImg{
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
}
.user{
  margin-top: 20px;
  font-weight: 550;
}


@media (max-width:576px) {
.user{
  margin-top: 5px;
}

}
.btn1{
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  font-size: 17px;
  border-radius: 5%;
  border: 1px solid rgb(62, 171, 111);
}
.btn-unfollow {
  background-color: white;
  color: black;
  border: 1px solid rgb(62, 171, 111);
  width: 120px;
}
.btn-follow{
  background-color: rgb(62, 171, 111);
  color: white;
  width: 120px;
}

.row{
  width: 100%;
  
}
</style>