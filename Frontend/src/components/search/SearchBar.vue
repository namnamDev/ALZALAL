<template>
  <div id="search">
    <div id="search-bar">
      검색하기
    </div> 
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
#search{
  height: 100vh;
  width: 20%;
  border-left: 1px solid black;  
  right: 0px;
  top: 60px;
  position:fixed;
  display: none;
}
#search-bar{
  position: absolute;
  left: 40%;
  top: 150px;
}

@media (min-width:992px){
  #search{
    display:block;
  }
}
</style>
