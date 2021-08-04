<template>
  <div class="row">
      <!-- props로 상위컴포넌트에서 유저pk 유저이름 받아오기 -->
      <div class="userImg col-2 col-sm-2 col-lg-2">
          <img class="profileImg"  :src="imgsrc" alt="Img">
      </div>
      <div class="user col-10 col-sm-10 col-lg-10">
          <span class="userName">
          {{name}}
          </span>
          <span class="followBtn">
          <button class="btn" id="fbtn" @click="clickFollow">팔로우</button>
          </span>
      </div>      
  </div>
</template>

<script>

import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL
const token = localStorage.getItem('jwt')
console.log(token)
export default {
    props:{
        name: String,
        no: Number,
    },
    data(){
        return{
            imgsrc: `${SERVER_URL}/profile/img/${this.no}`,
            counter:1,
            btnText:''
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
        clickFollow: function(){
            axios({
                    method: 'post',
                    url: `${SERVER_URL}/follow/member`,
                    data: {
                        "memberNo": this.no,
                    },
                    headers: this.getToken,
                })
                .then(res =>{
                    console.log(res)         
                })
                .catch(err => {
                    console.log(this.no)
                    console.log(this.getToken)
                    console.log(err);
                })
            const btn = document.querySelector('#fbtn')
            console.log(btn)
            if(btn.innerText == 'Follow' ){
              btn.innerText = 'Unfollow'
            }else{
              btn.innerText = 'Follow'
            }
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
  border-radius: 10%;
  border: solid 0.5px skyblue;
  margin-left: 0;
  

}
}
.btn{
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .1rem;
  font-size: 15px;
}
#clickFollowing:hover {
  background-color:#a1d4e2;
}
#clickFollower:hover{
  background-color: #a1d4e2;
}
#fbtn:hover{
  border: 1px solid #3ed34a;
}
</style>