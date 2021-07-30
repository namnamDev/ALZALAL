<template>
  <div class="container">
    <TopProfile></TopProfile>
    <DownProfile></DownProfile>
  </div>
</template>

<script>
import TopProfile from '@/components/profile/topProfile.vue'
import DownProfile from '@/components/profile/downProfile.vue'
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
}
@media (max-width:576px){
  .container{
    margin-top: 60px;
  }
}
</style>