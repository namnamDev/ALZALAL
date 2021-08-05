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
          <span class="followBtn">
            <button class="btn btn-light" v-if="this.followState"  @click="clickFollow($event)">unfollow</button>
            <button class="btn btn-primary" v-if="!this.followState"  @click="clickFollow($event)">follow</button>
          </span>
          
      </div>      
  </div>
</template>

<script>

import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
    props:{
        name: String,
        no: Number,
        followState: Boolean
    },
    data(){
        return{
            imgsrc: `${SERVER_URL}/profile/img/${this.no}`,
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
              
            }else{
              event.target.innerText = 'follow'
              
              event.target.style.backgroundColor='#50bcdf'
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
@media (max-width:768px) {
.user{
  margin-top: 5px;
}
.followBtn {
  border-radius: 20%;
  border: solid 1px skyblue;
  margin-left: 0;
  

}
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