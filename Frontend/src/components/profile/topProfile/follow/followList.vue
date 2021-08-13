<template>
  <div>
    <div class="row">
      <div class="col-4 col-lg-4">
        <button class="btn1" id="userfollow" @click="changeComponent1('userfollow')">User</button>
      </div>
      <div class="col-4 col-lg-4">
        <button class="btn1" id="problemfollow" @click="changeComponent2('problemfollow')">Problem</button>
      </div>
      <div class="col-4 col-lg-4">
        <button class="btn1" id="algofollow" @click="changeComponent3('algofollow')">Algo</button>
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
      changeComponent1: function(componentName) {
        this.comp = componentName
        document.getElementById('userfollow').style.backgroundColor = 'rgb(62, 171, 111)'
        document.getElementById('userfollow').style.color = 'white'
        document.getElementById('problemfollow').style.backgroundColor = 'white'
        document.getElementById('problemfollow').style.color = 'black'
        document.getElementById('algofollow').style.backgroundColor = 'white'
        document.getElementById('algofollow').style.color = 'black'
      },
      changeComponent2: function(componentName){
        this.comp = componentName
        document.getElementById('userfollow').style.backgroundColor = 'white'
        document.getElementById('userfollow').style.color = 'black'
        document.getElementById('problemfollow').style.backgroundColor = 'rgb(62, 171, 111)'
        document.getElementById('problemfollow').style.color = 'white'
        document.getElementById('algofollow').style.backgroundColor = 'white'
        document.getElementById('algofollow').style.color = 'black'
      },
      changeComponent3: function(componentName) {
        this.comp = componentName
        document.getElementById('userfollow').style.backgroundColor = 'white'
        document.getElementById('userfollow').style.color = 'black'
        document.getElementById('problemfollow').style.backgroundColor = 'white'
        document.getElementById('problemfollow').style.color = 'black'
        document.getElementById('algofollow').style.backgroundColor = 'rgb(62, 171, 111)'
        document.getElementById('algofollow').style.color = 'white'
      },
  }
}
</script>

<style scoped>
.row{
  margin-top: 30px;
}
.btn1{
  width:150px;
  height: 40px;
  font-size:15px;
  font-weight: 550;
  border-radius: 5%;
  margin-left: -20px;
  border: none;
}
.tab-item {
  margin-top: 20px;
}
.btn1:hover{
  background-color: rgb(216, 216, 216);
}
#userfollow{
  background-color: rgb(62, 171, 111);
  color: white;
}
</style>