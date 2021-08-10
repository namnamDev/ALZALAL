<template>
  <div class="row">
      <!-- props로 상위컴포넌트에서 유저pk 유저이름 받아오기 -->
      <div class="userImg col-3">
          <img class="profileImg"  :src="imgsrc" alt="Img" v-if="flag">
          <img class="profileImg"  :src="require(`@/assets/images/${imgsrc}.png`)" alt="Img" v-else>
      </div>
      <div class="user col-9">
          <span class="userName">
          <button class="btn" @click="clickName">{{name}}</button>
          </span>
          <span class="followBtn" v-if="!this.isLogin && !this.me">
          <button class="btn btn-unfollow" @click="clickFollow($event)">unfollow</button>
          </span>
          <!-- {{followState}} -->
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
          imgsrc: '',
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
      axios({
        method: 'get',
        url: `${SERVER_URL}/profile/img/${this.no}`
      })
      .then(res => {
        this.flag = true
        this.imgsrc = res.data
        console.log(res)
      })
      .catch(() => {
        this.flag= false
        console.log('1231241241441')
        this.imgsrc = 'profile'
        // console.log(err)
      })
    },
    computed: {
        getToken(){
        const token = localStorage.getItem('jwt')
        const config = {
            Authorization: `Bearer ${token}`
        }
        return config
        }
	},
    methods:{
        clickFollow: function(event){
            axios({
                    method: 'post',
                    url: `${SERVER_URL}/follow/member`,
                    data: {"memberNo": this.no},
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
          localStorage.setItem('userPk',this.no)
          this.$router.push({'name':'profilePage', params:{userPk:this.no}})
        }
    },

}

</script>

<style scoped>

.userImg {
    width: 70px;
    height: 70px; 
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
  margin-right: 30px;
  font-weight: 550;
}
.followBtn {
  border-radius: 10%;
  border: solid 1px skyblue;
  
  width: 30%;
  margin-left: 30px;
}
@media (max-width:768px) {
.user{
  margin-top: 5px;
}
.followBtn {
  border-radius: 20%;
  margin-left: 0;
}
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
.btn{
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  font-size: 15px;
  border-radius: 11%;
}
.btn:hover {
  background-color:#a1d4e2;
}

</style>