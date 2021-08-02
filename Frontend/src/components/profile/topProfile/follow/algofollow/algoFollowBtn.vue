<template>
  <div>
      <button class="btn" @click="clickFollow">팔로우</button>
      {{algo}}
  </div>
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
        }
  },
}
</script>

<style>

</style>