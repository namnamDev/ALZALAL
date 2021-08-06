<template>
  <div id="side-menu-bar">    
    <div id="menu">
      <p @click="clickLogo">LOGO</p>
      <ul>
        <li @click="clickLogo">타임라인</li>
        <li @click="clickQnA">Q&A</li>
        <li @click="clickDebate">토론게시판</li>
        <li @click="clickCreate">글작성</li>
        <li class="search">검색하기</li>
      </ul>
    </div>
    <div id="menu-1" style="left:-230px;" v-if="width < 576">
      <p @click="clickLogo">LOGO</p>
      <ul>
        <li @click="clickLogo">타임라인</li>
        <li @click="clickQnA">Q&A</li>
        <li @click="clickDebate">토론게시판</li>
        <li @click="clickCreate">글작성</li>
        <li class="search">검색하기</li>
      </ul>
    </div>        
    <div class="icon ms-3" @click="ClickMenuButton">
      <i class="fas fa-bars menuIcon"></i>
    </div>
  </div>
</template>

<script>


export default {
  name: 'SideMenuBar',
  components: {
  },
  data() {
    return {
      width: 0,
    }
  },
  mounted() {
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    window.removeEventListener('resize',this.handleResize)
  },

  watch: {

  },

  computed: {

  },

  methods: {
    handleResize(){
      this.width = window.innerWidth
    },
    ClickMenuButton: function() {

      let leftMenu = document.getElementById('menu-1')

      if (leftMenu.style.left == 0) {
        leftMenu.style.left = '-230px'
        return
      }
      leftMenu.style.left = 0
      leftMenu.style.opacity = 0.95
    },

    clickLogo: function() {
      this.$router.push({'name':'timeline'})
    },
    clickDebate: function() {
      this.$router.push({'name':'debate'})
    },
    clickCreate: function() {
      this.$router.push({'name':'createArticle'})      
    },
    clickQnA: function() {
      this.$router.push({'name':'qna'})      
    }
  },
  
  created: function () {
  }

}
</script>


<style>
#side-menu-bar{
  position:fixed;
  background-color:rgba(224,245,249,1);
  height: 100vh;
  width: 200px;
  z-index:5;
}

#menu-1{
  position: absolute;
  width: 220px;
  height: 100%;
  background-color:rgba(224,245,249,1);
  /* display:none; */
  left: -230px;
  /* left: 0px; */
  transition: 1s ease;
  opacity: 0;
}
#menu-1 > p {
  position: absolute;
  left: 40%;
  top: 100px;
}
#menu-1 > ul {
  position: absolute;
  text-align: left;
  top: calc(100px + 10%);
}
#menu-1 > ul li{
  list-style: none;
  margin-top:15px;
  cursor: pointer;
}
#menu-1 > ul li:hover{
  transform: scale(1.1);
  font-weight:bold;
}
p{
  cursor: pointer;
}
#menu{
  position: absolute;
  width: 100%;
  height: 100%;
  top: 100px;
}
#menu > p {
  position: absolute;
  left: 40%;
}
#menu > ul {
  position: absolute;
  text-align: left;
  top: 10%;
  width: 100%;
}
#menu > ul li{
  list-style: none;
  margin-top:15px;
  cursor: pointer;
  width: 100%;
      font-size: 1.0rem;
    font-weight: 600;
    line-height: 1.0;
}
#menu > ul li:hover{
  transform: scale(1.1);
}

.icon{
  position: absolute;
  left: 15px;
  top: 20px;
  font-size: 30px;
  display:none;
  cursor: pointer;
}

.search{
  display: none;
}

@media (max-width: 992px){
  .search{
    display: block;
  }
}

@media (max-width:577px) {
  #menu{
    display:none;
  }
  .icon{
    display:block;
  }
  #side-menu-bar {
    width: 50px;
    background-color: rgba(0,0,0,0);
    height: 100vh;
  }
}
</style>