<template>
  <div class="container">
    <div class="container" v-for="item,idx in articleItems" :key="idx">
      <TimelineItem :articleNo="item.articleNo" :content="item.articleContent"/>      
    </div>
  </div>
</template>

<script>
import TimelineItem from '@/components/article/TimelineItem.vue'
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  components: { TimelineItem },
  data: function(){
    return {
      articleItems: null
    }
  },
  methods: {
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
  },
  mounted() {
    const search = document.querySelector('#search')
    search.style.display = 'block'
  },
  created() {
    console.log('created')
    axios({
        method: 'get',
        url: `${SERVER_URL}/articlelist/article`,
        headers: this.getToken(),
      })   
      .then(res =>{
        this.articleItems = res.data.article      
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