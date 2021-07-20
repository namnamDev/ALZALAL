<template>
  <div id="app">
    <TopMenuBar />
    <SideMenuBar />
    <SearchBar />
    <router-view />
  </div>
</template>

<script>
import SideMenuBar from "@/components/navBar/SideMenuBar.vue";
import TopMenuBar from "@/components/navBar/TopNavBar.vue";
import SearchBar from "@/components/search/SearchBar.vue";

export default {
  name: "App",
  components: {
    SideMenuBar,
    TopMenuBar,
    SearchBar,
  },
};
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 100%;
  position: relative;
}

</style>
