<template>
  <div class="sideMenu">
    <div id="leftMenu">
      <span>6 To 4</span>
      <ul>
        <li>타임라인</li>
        <li>Q&A</li>
        <li>토론게시판</li>
        <li>글작성</li>
      </ul>
    </div>
    <div class="menuIconDiv" @click="ClickMenuButton">
      <i class="fas fa-bars menuIcon"></i>
    </div>
    
  </div>
</template>

<script>
export default {
  name: 'Main',
  components: {
    
  },

  data() {
    return {
      display: 'none',
    }
  },

  computed: {
    sideMenuBar () {
      return {
      }
    },    
  },

  methods: {
    ClickMenuButton: function() {
      let leftMenu = document.getElementById('leftMenu')
      if (leftMenu.style.display == 'none') {
        leftMenu.style.display = 'block'
      }
      else {
        leftMenu.style.display = 'none'
      }
      
      
    }
  }

}
</script>

<style scoped>
#a{
  height:2000px;
  width: 2000px;
  border: 1px solid black;
  z-index: -2;
  position: absolute;
}
.sideMenu{
  position: relative;
  z-index:-1;
}

.menuIconDiv{
  border: 1px solid black;
  height: 100vh;
  width: 50px;
  position: fixed;
  cursor: pointer;
}

.menuIcon{
  margin-top: 70px;
  margin-left: 15px;
  font-size: 25px;
}

#leftMenu{
  border: 1px solid black;
  height: 100vh;
  width: 300px;  
  position: fixed;
}
  #leftMenu > ul{
    position: absolute;
    float: right;
    left: 60px;
    top: 200px;
    list-style: none;
  }
  #leftMenu > ul > li {
    margin-top: 20px;
    font-size: 20px;
  }
  #leftMenu > span{
    position: absolute;
    left: 110px;
    top: 100px;
    font-size: 40px;
  }

</style>