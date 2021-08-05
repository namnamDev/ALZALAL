<template>
  <div class="row">
      <!-- props로 상위컴포넌트에서 유저pk 유저이름 받아오기 -->
      <div class="userImg col-3">
          <img class="profileImg" :src="imgsrc" alt="이미지">
      </div>
      <div class="user col-9">
          <span class="userName" @click="clickName">
          {{name}}
          </span>
          <span class="followBtn">
          <button class="btn" id="fbtn" @click="clickFollow($event)">follow</button>
          </span>
          <!-- {{followState}} -->
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
                .then({
                    
                })
                .catch({
                    // console.log(this.no)
                    // console.log(this.getToken)
                    // console.log(err);
                })
            if(event.target.innerText == 'follow' ){
              event.target.innerText = 'Unfollow'
            }else{
              event.target.innerText = 'follow'
            }

        },
        clickName: function(){
          this.$router.push({'name':'profilePage', params:{userpk:this.no}})
        }
    },

}

</script>

<style scoped>
.row{
  margin-left: 30px;
}
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

.userName{
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

  .followBtn {
    border-radius: 10%;
    border: solid 0.5px skyblue;
    margin-left: 0;
    

  }
}
.btn{
  font-weight: 700;
  font-size: 1vw;
}
.btn:hover {
  background-color:#a1d4e2;
}

</style>