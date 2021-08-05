<template>
  <div class="container">
    <div class="container" v-for="item,idx in getQnaList" :key="idx">
      <QnAItem :item="item"/>
    </div>
    
  </div>
</template>

<script>
import QnAItem from '@/components/article/QnAItem.vue'
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default { 
  components: { QnAItem },

  data: function(){
    return {
      articleItems: null
    }
  },
  computed: {
    getQnaList: function() {
      return this.$store.getters.getQnaList
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
    axios({
        method: 'get',
        url: `${SERVER_URL}/articlelist/qna`,
        headers: this.getToken(),
      })   
      .then(res =>{
        this.$store.dispatch('createQnaList',res.data.article)
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