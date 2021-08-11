<template>
  <div id="app" @click="clickApp">

    <div id="main">
        <TopMenuBar />
        <router-view class="mycol2"/>
    </div>
        <!-- <SideMenuBar class="mycol1"/> -->
        <!-- <SearchBar class="mycol1" /> -->
    <Modal />
  </div>
</template>

<script>
// import SideMenuBar from "@/components/navBar/SideMenuBar.vue";
import TopMenuBar from "@/components/navBar/TopNavBar.vue";
// import SearchBar from "@/components/search/SearchBar.vue";
import Modal from '@/components/search/SearchModal.vue'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import jwt_decode from 'jwt-decode'
import $ from 'jquery'

const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  name: "App",
  data(){
    return{    
      headers:{
        Authorization:"",
      },
      userpk :'',
      stompClient:''
    }
  },
  components: {
    // SideMenuBar,
    TopMenuBar,
    // SearchBar,
    Modal
  },

  created(){
    if(localStorage.getItem("jwt")){
      let tmp=localStorage.getItem("jwt");
      // memberPK 받아옴
      this.userpk=jwt_decode(tmp).sub;
      this.headers.Authorization= "Bearer "+ tmp;
      this.connect();
    }
  },
  methods: {
    connect: function () {
      let socket = new SockJS(`${SERVER_URL}/connectNotification`);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(this.headers, this.onConnected, this.onError);
    },
    onConnected: function () {
      this.stompClient.subscribe("/user/notification/count", this.onMessageReceived);
      this.stompClient.send("/notification/connect", JSON.stringify({ no: this.userpk }));
      // console.log("연결완료");
      // location.href = '/timeline'
    },
    onMessageReceived:function (payload) {
       let message = JSON.parse(payload.body);

      console.log("messege", message);
    },
    onError:function (error) {
      console.log("에러발생", error);
    },
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

<style>
#main{
  display: flex;
  flex-direction: column;
  width: inherit;
}

</style>
