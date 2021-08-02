<template>
  <div class="row">
      <!-- props로 상위컴포넌트에서 유저pk 유저이름 받아오기 -->
      <div class="userImg col-3">
          <img class="profileImg" :src="imgsrc" alt="이미지">
      </div>
      <div class="user col-9">
          <span class="userName">
          {{name}}
          </span>
          <span class="followBtn">
          <button class="btn" @click="clickFollow" value="팔로우끊기" />
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
        no: Number
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
        }
	},
    methods:{
        clickFollow: function(){
            axios({
                    method: 'post',
                    url: `${SERVER_URL}/follow/member`,
                    data: {"memberNo": this.no},
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
        }
    },

}
</script>

<style scoped>
.userImg {
    width: 70px;
    height: 70px; 
    border-radius: 70%;
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

.userName{
  margin-right: 30px;
  font-weight: 550;
}
.followBtn {
  border-radius: 10%;
  border: solid 0.5px skyblue;
  
  width: 30%;
  margin-left: 30px;
}
@media (max-width:768px) {

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
  font-size: 1vw;
}
#clickFollowing:hover {
  background-color:#a1d4e2;
}
#clickFollower:hover{
  background-color: #a1d4e2;
}
</style>