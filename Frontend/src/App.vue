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
      notificationList: [],
      userpk :'',
      stompClient:'',      
    }
  },
  components: {
    SideMenuBar,
    TopMenuBar,
    SearchBar,
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
      this.stompClient.debug = () => {};
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

      if (message.new){
        this.$store.dispatch('createNotify')
      }
      else{
        this.$store.dispatch('deleteNotify')
      }
      // this.$store.dispatch('deleteNotificationList')     
      
    },
    onError:function (error) {
      console.log("에러발생", error);
    },
    // side menu 바 다른 곳 클릭시 창 사라짐
    clickApp: function(event) {
      // this.page=0
      // this.$store.dispatch('deleteNotificationList') 
      var div = $('.notify-table')
      if (event.target != event.currentTarget.querySelector('.fa-bell')){
        if( div.is(":visible") ){
            div.slideUp(400);
        }
      }
      // commentForm 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기

      var windowWidth = $(window).width();
      if (windowWidth < 577) {

        let leftMenu = document.getElementById('menu-1')
  
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
