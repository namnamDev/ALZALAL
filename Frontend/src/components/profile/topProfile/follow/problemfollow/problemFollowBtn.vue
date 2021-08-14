<template>
    <span>
      <button class="btn1" id="fbtn" @click="clickFollow($event)">Unfollow</button>
    </span>
</template>

<script>
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
    props:{
        site: String,
        no: Number
    },
    computed: {
        getToken(){
        const token = sessionStorage.getItem('jwt')
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
                url: `${SERVER_URL}/follow/problem`,
                data: {
                    "problemSite": this.site,
                    "problemNo": this.no
                },
                headers: this.getToken,
            })
            .then(res=>{
                console.log(res)
            })
            .catch(err => {
                console.log(this.site)
                console.log(this.no)
                console.log(this.getToken)
                console.log(err);
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
  },
}
</script>

<style scoped>

.btn1{
    margin-bottom: 20px;
    height: 40px;
  font-weight: 700;
  font-size: 17px;
  border-radius: 5%;
  background-color: white;
  color: black;
  border: 1px solid rgb(62, 171, 111);
  width: 70%;
}
</style>