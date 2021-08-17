<template>
  <div id="app" @click="click">

    <div id="main">
        <TopMenuBar />
        <router-view />
    </div>
    <Modal />
  </div>
</template>

<script>
import TopMenuBar from "@/components/navBar/TopNavBar.vue";
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
      notificationList: [],
      userpk :'',
      stompClient:'',      
    }
  },
  components: {
    TopMenuBar,
    Modal
  },

  created(){
    if(sessionStorage.getItem("jwt")){
      let tmp=sessionStorage.getItem("jwt");
      // memberPK 받아옴
      this.userpk=jwt_decode(tmp).sub;
      this.headers.Authorization= "Bearer "+ tmp;
      this.connect();
    }
  },
  methods: {    
    click: function() {
      var div = $('.notify-table')
      if (event.target != event.currentTarget.querySelector('.fa-bell')){
        if( div.is(":visible") ){
            div.slideUp(400);
        }
      }

    },
    connect: function () {
      let socket = new SockJS(`${SERVER_URL}/connectNotification`);
      this.stompClient = Stomp.over(socket);
      this.stompClient.debug = () => {};
      this.stompClient.connect(this.headers, this.onConnected, this.onError);
      this.$store.dispatch('createSocketConnection',this.stompClient);
    },
    onConnected: function () {
      this.stompClient.subscribe("/user/notification/count", this.onMessageReceived);
      this.stompClient.send("/notification/connect", JSON.stringify({ no: this.userpk }));
    },
    onMessageReceived:function (payload) {
      let message = JSON.parse(payload.body);
      if (message.new){
        this.$store.dispatch('createNotify')
      }
      else{
        this.$store.dispatch('deleteNotify')
      }
      
    },
    onError:function (error) {
      console.log("에러발생", error);
    },
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
#main{
  display: flex;
  flex-direction: column;
  width: inherit;
}
</style>
