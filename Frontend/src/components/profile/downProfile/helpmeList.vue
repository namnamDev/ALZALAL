<template>
  <div class="row">
  <!-- 보낸요청 시작 -->
  <helpmeItem v-for="item, index in helpmeSendList" :key="index"
     :helpmeNo="item.helpmeNo"
     :helpmeContent="item.helpmeContent"
    >
    </helpmeItem>
  <infinite-loading @infinite="infiniteHandler" spinner="sprial">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
    </infinite-loading>
  <!-- 보낸요청 끝 -->
  </div>
</template>

<script>
import helpmeItem from '@/components/profile/downProfile/helpmeItem.vue';
import InfiniteLoading from 'vue-infinite-loading';
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
import jwt_decode from "jwt-decode";
const token = localStorage.getItem("jwt");
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
  
}
export default {
  components:{
    InfiniteLoading,
    helpmeItem
  },
  data(){
    return{
      helpmeSendList: [],
      page: 0,
      pk: '',
    }
  },
  created: function() {
    console.log("target",this.userPk)
    const userPk = localStorage.getItem("userPk")
    console.log(userPk)
    if(userPk){
        this.pk = userPk
        this.myPage = false
    }else{
        this.pk = userpk
        this.myPage = true
    }
  },
  methods:{
    infiniteHandler($state) {
      axios({
        method: 'get',
        url: `${SERVER_URL}/helpme/sendlist`+"?page=" + (this.page),
        headers: this.getToken
        }).then(res => {
          console.log(res)
          setTimeout(() => {
            if(res.data.helpmeSendList.length) {
              this.helpmeSendList = this.helpmeSendList.concat(res.data.helpmeSendList)
              $state.loaded()
              this.page += 1
              // 끝인지 판별
              if(res.data.helpmeSendList.length / 10 < 1) {
                $state.complete()
              }
            } else {
              // 끝 지정(No more data)
              $state.complete()
            }
          }, 1000)
        }).catch(err => {
          console.error(err);
        })
    },
    
  },
	computed: {
    getToken(){
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    }
	},
}
</script>

<style scoped>

</style>