<template>
  <div class="row">
      <!-- props로 상위컴포넌트에서 유저pk 유저이름 받아오기 -->
      <div class="userImg col-3 col-sm-3 col-lg-3">
          <img class="profileImg"  :src="imgsrc" alt="Img">
          
      </div>
      <div class="user col-6 col-sm-6 col-lg-6">
          <span class="userName">
          <button class="btn" @click="clickName">{{name}}</button>
          </span>
      </div>
      <div class="user col-2 col-sm-2 col-lg-2">
          <span class="followBtn" v-if="!this.isLogin && !this.me">
            <button class="btn btn-unfollow" v-if="this.followState"  @click="clickFollow($event)">unfollow</button>
            <button class="btn btn-follow" v-if="!this.followState"  @click="clickFollow($event)">follow</button>
          </span>
      </div>      
      <div class="user col-1 col-sm-1 col-lg-1">

      </div>
  </div>
</template>

<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'
const SERVER_URL = process.env.VUE_APP_SERVER_URL
const token = localStorage.getItem('jwt')
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
      
      let pk = ''
      if(userPk){
          this.pk = userPk
          this.myPage = false
           //this.imgsrc=`${SERVER_URL}/profile/img/${pk}`
            if(pk==userpk){
                this.myPage = true
                //this.imgsrc=`${SERVER_URL}/profile/img/${pk}`
            }
      }else{
          this.pk = userpk
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
                    alert("로그인이 필요합니다.")
                this.$router.push({name:'login'})
                }

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
                    // console.log(this.no)
                    // console.log(this.getToken)
                    // console.log(err);
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
.btn{
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  font-size: 17px;
  border-radius: 5%;
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