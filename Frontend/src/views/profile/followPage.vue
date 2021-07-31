<template>
  <div class="container">
    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="col-lg-6 col-md-10 col-sm-9 col-10 ">
        <div class="row">
          <div class="col-6 col-lg-6">
            <button class="btn" @click="changeComponent('followerList')">follower</button>
          </div>
          <div class="col-6 col-lg-6">
            <button class="btn" @click="changeComponent('followList')"> follow</button>
          </div>
        </div>
        <div class="tab-item">
          <keep-alive>
            <component v-bind:is="comp"></component> 
          </keep-alive>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import followList from '@/components/profile/followList.vue'
import followerList from '@/components/profile/followerList.vue'
export default {
  components: {
    followList,
    followerList
  },
  data() {
      return { comp: 'followerList' }
  },
  methods: {
      changeComponent: function(componentName) {
          this.comp = componentName
          console.log(this.comp)
      }
  }

}
</script>

<style scoped>
.container{
  margin-top: 150px;
}
@media (max-width:576px){
  .container{
    margin-top: 140px;
  }
}
.btn{
  width:200px;
  font-size:25px;
  font-weight: 550;
}
.tab-item {
  margin-top: 20px;
}
.btn:hover{
  background-color: antiquewhite;
}
</style>