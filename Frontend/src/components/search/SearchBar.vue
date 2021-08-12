<template>
  <div>
    <div id="search" >    
      <div id="search-bar">
        <div class="search-box " data-bs-toggle="modal" data-bs-target="#exampleModal">
          <i class="fas fa-search"></i>
          검색해주세요.
        </div>
      </div>     
    </div>
  </div>
</template>

<script>


export default {
  components: {

  },  
}
</script>

<style scoped>
#search{
  
  width: 310px;
  /* right: 70px;
  top: 170px;
  position:fixed; */
  /* border-left: 1px solid black;   */
  /* display: none; */
  /* display: block; */
}
#search-bar{
  /* position: absolute; */
  /* z-index:-2; */
}
.fas{
  font-size: 20px;
  margin-right:10px;
}
.search-box{
  background-color: white;
  text-align: center;
  border:1px solid white;
  font-size: 1.4rem;
  font-weight: bold ;
  color: rgb(62, 171, 111);
  width: 100%;
  border-radius: 5px;
  padding:4px 7px;  
  cursor: pointer;
  line-height: 1.5;
}

#category{
  /* position: absolute; */
  width: 130px;
  height:30px;
  border-radius: 3px;
}
#language{
  width: 130px;
  height:30px;
  border-radius: 3px;
  /* position:absolute; */
  top: 50px;
}
.search-algo-input-div{
  border-radius: 3px;
  /* position: absolute; */
  top: 100px;
  z-index: 1;
  display: inline;
}
.show {
  display: block;
}
#search-algo-input-1{
  width:200px;
}
#search-algo-ul{
  padding-left: 3px;
  border: 1px solid black;
  background-color: white;
  list-style: none;
  text-align: start;
  display: none;
  overflow: auto;
  height: 150px;
  box-shadow: 0 20px 20px rgba(39, 40, 41, 0.2);
}
#search-algo-li:hover {
  background-color: antiquewhite;
}
.include{
  border: 1px solid black;
  height:140px;
  width: 200px;
  /* position: absolute; */
  top: 150px;
}
.exclude{
  border: 1px solid black;
  height:140px;
  width: 200px;
  /* position: absolute; */
  top: 320px;
}
.btn-include{
  /* position:absolute; */
  width: 40px;
  top:3px;
  right:-50px;
  cursor: pointer;
}
.btn-exclude{
  /* position:absolute; */
  width:40px;
  top:3px;
  right: -100px;
  cursor: pointer;
}
.btn-search{
  /* position: absolute; */
  width:200px;
  top: 470px;
}
.fa-search{
  animation-name: heartBeat;
  animation-duration: 2s;
  animation-iteration-count: infinite; 
}

/* @media (max-width:992px){
  #search{
    display:none; 
  }
} */
</style>
