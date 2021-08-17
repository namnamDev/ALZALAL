<template>
  <div class="row">
  <!-- 보낸요청 시작 -->
  <receptItem v-for="item, index in helpmeReceptList" :key="index"
     :helpmeNo="item.helpmeNo"
     :helpmeContent="item.helpmeContent"
    >
    </receptItem>
  <infinite-loading @infinite="infiniteHandler" spinner="sprial">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
    </infinite-loading>
  <!-- 보낸요청 끝 -->
  </div>
</template>

<script>
import receptItem from '@/components/profile/downProfile/receptItem.vue';
import jwt_decode from "jwt-decode";
import InfiniteLoading from 'vue-infinite-loading';
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
const token = sessionStorage.getItem('jwt')
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
}
export default {
  components:{
    InfiniteLoading,
    receptItem
  },
  data(){
    return{
      helpmeReceptList: [],
      page: 0,
      pk: '',
    }
  },
  created: function() {
    const userPk = localStorage.getItem("userPk")
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
        url: `${SERVER_URL}/helpme/receptlist/${this.pk}`+"?page=" + (this.page),
        headers: this.getToken
        }).then(res => {
          setTimeout(() => {
            if(res.data.helpmeReceptList.length) {
              this.helpmeReceptList = this.helpmeReceptList.concat(res.data.helpmeReceptList)
              $state.loaded()
              this.page += 1
              // 끝인지 판별
              if(res.data.helpmeReceptList.length / 10 < 1) {
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
    clickHelpmeName(){

    }
  },
	computed: {
    getToken(){
      const token = sessionStorage.getItem('jwt')
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