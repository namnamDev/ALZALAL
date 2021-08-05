<template>
  <div class="row my-5">
  <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
   <div class="feed-item col-lg-6 col-md-10 col-sm-9 col-10">
     <div class="row">
          <div class="col-4 col-lg-4">
            <button class="btn" @click="changeComponent('articleList')">게시글</button>
          </div>
          <div class="col-4 col-lg-4" v-if="this.myPage">
            <button class="btn" @click="changeComponent('helpmeList')">내가요청한 문제</button>
          </div>
          <div class="col-4 col-lg-4">
            <button class="btn" @click="changeComponent('receptList')">요청받은 문제</button>
          </div>
        </div>
        <div class="tab-item">
          <keep-alive>
            <component v-bind:is="comp"></component> 
          </keep-alive>
        </div>
  </div>
</div>
</template>

<script>
import articleList from '@/components/profile/downProfile/articleList'
import helpmeList from '@/components/profile/downProfile/helpmeList'
import receptList from '@/components/profile/downProfile/receptList'
import jwt_decode from 'jwt-decode'
const token = localStorage.getItem('jwt')
let userpk = '';
if (token) {
  const decoded = jwt_decode(token)
  userpk = decoded.sub
}
export default {
  components: {
    articleList,
    helpmeList,
    receptList
  },
  data() {
      return { comp: 'articleList', myPage: '', }
  },
  created: function() {
        console.log("target",this.userPk)
        const userPk = localStorage.getItem("userPk")
        console.log(userPk)
        let pk = ''
        console.log(pk)
        if(userPk){
            pk = userPk
            this.myPage = false
        }else{
            pk = userpk
            this.myPage = true
        }
  },
  methods: {
      changeComponent: function(componentName) {
          this.comp = componentName
      }
  }
}
</script>

<style scoped>
.row{
  margin-top: 30px;
  margin-left: -50px;
}
.btn{
  width:200px;
  font-size:15px;
  font-weight: 550;
}
.tab-item {
  margin-top: 20px;
}
.btn:hover{
  background-color: antiquewhite;
}

</style>