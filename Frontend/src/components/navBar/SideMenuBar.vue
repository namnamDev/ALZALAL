<template>
  <div class="sideMenu">
    <div class="menuIconDiv" @click="ClickMenuButton">
      <i class="fas fa-bars menuIcon"></i>
    </div>
    <div id="leftMenu">
      <span>6 To 4</span>
      <ul>
        <li>타임라인</li>
        <li>Q&A</li>
        <li>토론게시판</li>
        <li>글작성</li>
      </ul>
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
.sideMenu{
  position: fixed;
  top: 60px;
}

.menuIconDiv{
  border-right: 1px solid black;
  height: 100vh;
  width: 50px;
  position: fixed;
  cursor: pointer;  
  /* z-index:2; */
}

.menuIcon{
  margin-top: 20px;
  font-size: 25px;
}

#leftMenu{
  border-right: 1px solid black;
  height: 100vh;
  width: 250px;  
  left: 50px;
  position: relative;
}
  #leftMenu > ul{
    position: absolute;    
    left: 10px;
    top: 160px;
    list-style: none;
    float: left;
    text-align: left;
  }
  #leftMenu > ul > li {
    margin-top: 20px;
    font-size: 20px;
  }
  #leftMenu > span{
    position:absolute;   
    left: 50px;
    top: 70px;
    font-size: 40px;
  }

</style>