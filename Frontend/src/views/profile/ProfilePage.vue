<template>
  <div class="container">
    <TopProfile></TopProfile>
    <DownProfile></DownProfile>
  </div>
</template>

<script>
import TopProfile from '@/components/profile/topProfile/topProfile.vue'
import DownProfile from '@/components/profile/downProfile/downProfile.vue'
export default {
 components: {
     TopProfile,
     DownProfile
 },
  created: function() {
    const token = localStorage.getItem('jwt')
    if(!token){
      this.$router.push({name:'login'})
    }
  },
}
</script>

<style scoped>
.container{
  margin-top: 90px;
  margin-left: 50px;
}
@media (max-width:576px){
  .container{
    margin-top: 60px;
    margin-left:0
  }
}
</style>