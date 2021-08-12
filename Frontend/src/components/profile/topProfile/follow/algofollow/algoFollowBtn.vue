<template>
  <span>
      <button class="btn" id="fbtn" @click="clickFollow($event)">팔로우</button>
  </span>
</template>

<script>
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
    props:{
        algo: String
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
        clickFollow: function(){
            axios({
                method: 'post',
                url: `${SERVER_URL}/follow/algorithm`,
                data: {
                    "algorithm": this.algo,
                },
                headers: this.getToken,
            })
            .then({
                       
            })
            .catch(err => {
                console.log(this.algo)
                console.log(this.getToken)
                console.log(err);
            })
            if(event.target.innerText == 'follow' ){
              event.target.innerText = 'Unfollow'
            }else{
              event.target.innerText = 'follow'
            }
        }
  },
}
</script>

<style scoped>
.btn{
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .1rem;
  font-size: 15px;
}
</style>