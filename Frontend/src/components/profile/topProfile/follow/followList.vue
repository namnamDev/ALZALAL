<template>
  <div>
    <div class="row">
      <div class="col-4 col-lg-4">
        <button class="btn" @click="changeComponent('userfollow')">userfollow</button>
      </div>
      <div class="col-4 col-lg-4">
        <button class="btn" @click="changeComponent('problemfollow')">problemfollow</button>
      </div>
      <div class="col-4 col-lg-4">
        <button class="btn" @click="changeComponent('algofollow')">algofollow</button>
      </div>
    </div>
    <div class="tab-item">
      <keep-alive>
        <component v-bind:is="comp"></component> 
      </keep-alive>
    </div>
  </div>
</template>

<script>
import algofollow from '@/components/profile/topProfile/follow/algofollow/algoFollow.vue'
import problemfollow from '@/components/profile/topProfile/follow/problemfollow/problemFollow.vue'
import userfollow from '@/components/profile/topProfile/follow/userfollow/userFollow.vue'
export default {
  components: {
    userfollow,
    algofollow,
    problemfollow
  },
  data() {
      return { comp: 'userfollow' }
  },
  methods: {
      changeComponent: function(componentName) {
          this.comp = componentName
      }
  }
}
</script>

<style scoped>
.row{
  margin-top: 30px;
  margin-left: -50px;
}
.btn{
  width:200px;
  font-size:15px;
  font-weight: 550;
}
.tab-item {
  margin-top: 20px;
}
.btn:hover{
  background-color: antiquewhite;
}
</style>