<template>
  <div class="row">
      <!-- props로 상위컴포넌트에서 유저pk 유저이름 받아오기 -->
      <div class="userImg col-2 col-sm-2 col-lg-2">
          <img class="profileImg"  :src="imgsrc" alt="Img">
      </div>
      <div class="user col-10 col-sm-10 col-lg-10">
          <span class="userName">
          <button class="btn" @click="clickName">{{name}}</button>
          </span>
          <span class="followBtn" v-if="!this.isLogin && !this.me">
            <button class="btn btn-unfollow" v-if="this.followState"  @click="clickFollow($event)">unfollow</button>
            <button class="btn btn-follow" v-if="!this.followState"  @click="clickFollow($event)">follow</button>
          </span>
          
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
            imgsrc: `${SERVER_URL}/profile/img/${this.no}`,
            myPage: '',
            isLogin: '',
            me: '',
        }
    },
    created: function() {
      console.log("target",this.userPk)
      const userPk = localStorage.getItem("userPk")
      console.log(userPk)
      if(userPk){
          this.pk = userPk
          this.myPage = false
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
          const token = localStorage.getItem('jwt')
          const config = {
              Authorization: `Bearer ${token}`
          }
          return config
        },

	},
    methods:{
        clickFollow: function(event){
            const token = localStorage.getItem('jwt')
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
              
              event.target.style.backgroundColor='blue'
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
.userImg {
    width: 75px;
    height: 75px; 
    border-radius: 75%;
    overflow: hidden;
    display: flex;
    margin: 20px 0px 0px 0px;
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

.followBtn {
  border-radius: 10%;
  border: solid 0.5px skyblue;
  
  width: 30%;
  margin-left: 30px;
}
@media (max-width:576px) {
.user{
  margin-top: 5px;
}
.followBtn {
  border-radius: 20%;
  margin-left: 0;
}
.row{
  margin-left: 30px;
}
}
.btn{
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  font-size: 15px;
  border-radius: 5%;
}
.btn-unfollow {
  background-color: white;
  color: black;
  border: 1px solid blue;
}
.btn-follow{
  background-color: blue;
  color: white;
}
.btn:hover {
  background-color:#a1d4e2;
}
@media (min-width:768px) {
  .row{
    margin-left: 70px;
  }
}
</style>