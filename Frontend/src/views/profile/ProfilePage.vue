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
}
</script>

<style scoped>
.container{
  margin-top: 70px;
}
@media (max-width:576px){
  .container{
    margin-top: 60px;
    margin-left:0
  }
}
</style>