<template>
  <span>
      <button class="btn" id="fbtn" @click="clickFollow">팔로우</button>
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
                url: `${SERVER_URL}/follow/algorithm`,
                data: {
                    "algorithm": this.algo,
                },
                headers: this.getToken,
            })
            .then(res =>{
                console.log(res)         
            })
            .catch(err => {
                console.log(this.algo)
                console.log(this.getToken)
                console.log(err);
            })
            const btn = document.querySelector('#fbtn')
            if(btn.innerText == 'Follow' ){
              btn.innerText = 'Unfollow'
            }else{
              btn.innerText = 'Follow'
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