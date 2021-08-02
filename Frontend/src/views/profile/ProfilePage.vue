<template>
  <!-- <div>
    <TopProfile></TopProfile>
    <DownProfile></DownProfile>
  </div> -->
  <div class="container">
    <div class="row">
      <TopProfile></TopProfile>
    </div>
    <div class="row">
      <DownProfile></DownProfile>
    </div> 
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
