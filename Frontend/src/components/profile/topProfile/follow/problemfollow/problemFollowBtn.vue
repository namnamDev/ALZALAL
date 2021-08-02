<template>
    <span>
      <button class="btn" @click="clickFollow">팔로우</button>
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
                url: `${SERVER_URL}/follow/problem`,
                data: {
                    "problemSite": this.site,
                    "problemNo": this.no
                },
                headers: this.getToken,
            })
            .then(res =>{
                console.log(res)
            })
            .catch(err => {
                console.log(this.site)
                console.log(this.no)
                console.log(this.getToken)
                console.log(err);
            })
        },
  },
}
</script>

<style>
.btn:focus{
    border:none;
    outline: none;
}
.btn{
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .1rem;
  font-size: 15px;
}
</style>