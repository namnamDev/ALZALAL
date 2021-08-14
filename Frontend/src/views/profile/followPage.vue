<template>
  <div class="container">
    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="col-lg-6 col-md-10 col-sm-9 col-10 ">
        <div class="row">
          <div class="col-6 col-lg-6">
            <button class="btn1" id="follower" @click="changeComponent1('followerList')">follower</button>
          </div>
          <div class="col-6 col-lg-6">
            <button class="btn1" id="following" @click="changeComponent2('followList')"> following</button>
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
import followList from '@/components/profile/topProfile/follow/followList.vue'
import followerList from '@/components/profile/topProfile/follower/followerList.vue'
export default {
  components: {
    followList,
    followerList,
  },
  data() {
      return {
         comp: 'followerList',
         isFollower:true,
         isFollowing:false
       }
  },
  methods: {
      changeComponent1: function(componentName) {
        this.comp = componentName
        document.getElementById('follower').style.backgroundColor = 'rgb(62, 171, 111)'
        document.getElementById('follower').style.color = 'white'
        document.getElementById('following').style.backgroundColor = 'white'
        document.getElementById('following').style.color = 'black'
      },
      changeComponent2: function(componentName){
        this.comp = componentName
        document.getElementById('follower').style.backgroundColor = 'white'
        document.getElementById('follower').style.color = 'black'
        document.getElementById('following').style.backgroundColor = 'rgb(62, 171, 111)'
        document.getElementById('following').style.color = 'white'
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
    margin-top: 90px;
  }
}
.btn1{
  width:100%;
  font-size:25px;
  font-weight: 550;
  border-radius: 5%;
  border: none;

}
.tab-item {
  margin-top: 20px;
}

#follower{
  background-color: rgb(62, 171, 111);
  color: white;
}
</style>