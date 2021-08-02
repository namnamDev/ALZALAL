<template>
  <div id="search" >
    <div id="search-bar">
      <div class="search-box">
        <i class="fas fa-search"></i>
        검색해주세요.
      </div>
      <!-- <div class="search-detail">

      </div> -->
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
  /* border-left: 1px solid black;   */
  right: 0px;
  top: 210px;
  position:fixed;
  display: none;
}
#search-bar{
  position: absolute;
  /* display:flex; */
  z-index:-2;
}
.fas{
  font-size: 20px;
  margin-right:10px;
}
.search-box{
  border:1px solid black;
  width: 250px;
  border-radius: 5px;
  padding:4px 7px;  
}
.search-detail{
  border:1px solid black;
  position: absolute;
  width: 300px;
  left: -25px;
  height: 300px;
  border-radius: 5px;
  padding:4px 7px;  
}

@media (min-width:992px){
  #search{
    display:block; 
  }
}
</style>
