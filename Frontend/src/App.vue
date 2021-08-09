<template>
  <div id="app" @click="clickApp">
    <SideMenuBar />
    <SearchBar />
    <TopMenuBar />
    <router-view />
  </div>
</template>

<script>
import SideMenuBar from "@/components/navBar/SideMenuBar.vue";
import TopMenuBar from "@/components/navBar/TopNavBar.vue";
import SearchBar from "@/components/search/SearchBar.vue";
import $ from 'jquery'

export default {
  name: "App",
  components: {
    SideMenuBar,
    TopMenuBar,
    SearchBar,
  },

  methods: {
    // side menu 바 다른 곳 클릭시 창 사라짐
    clickApp: function(event) {
      var windowWidth = $(window).width();
      if (windowWidth < 577) {
        let leftMenu = document.getElementById('menu-1')
        console.log(leftMenu)
  
        if (event.target == event.currentTarget.querySelector('#menu-1') || 
        event.target == event.currentTarget.querySelector('.menuIcon')){
          return
        }
        else {
          leftMenu.style.left = '-230px'
          leftMenu.style.opacity = 0
        }

      }
    }
  }
};
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  width: 100%;
  position: relative;
  display: flex;  
}


</style>
