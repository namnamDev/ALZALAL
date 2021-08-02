<template>
  <div class="container">
    <TimelineItem />
    <TimelineItem />
    <TimelineItem />
    <TimelineItem />
    
  </div>
</template>

<script>
import TimelineItem from '@/components/article/TimelineItem.vue'
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  components: { TimelineItem },
  methods: {
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
  },
  created() {
    axios({
        method: 'get',
        url: `${SERVER_URL}/articlelist/article`,
      })   
      .then(res =>{
        console.log(res);        
      })
      .catch(err =>{  
        console.log(err)
      })
  }
  
}
</script>

<style scoped>
.container{
  margin-top: 110px;
}
@media (max-width:576px){
  .container{
    margin-top: 60px;
  }
}
</style>