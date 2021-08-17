<template>
  <div class="row my-3">
    <div class="col-lg-3 col-md-2 col-sm-3 col-1 col-xl-3"></div>
    <div class="feed-item col-lg-6 col-md-10 col-sm-9 col-10 col-xl-6">
      <div class="row" v-if="this.myPage">
            <div class="col-6 col-md-4 col-lg-6 col-xl-4">
              <button class="btn1" id="articleList" @click="changeComponent1('articleList')">게시글</button>
            </div>
            <div class="col-6 col-md-4 col-lg-6  col-xl-4">
              <button class="btn1" id="helpmeList" @click="changeComponent2('helpmeList')">내가요청한 문제</button>
            </div>
            <div class="col-6 col-md-4 col-lg-6  col-xl-4">
              <button class="btn1" id="receptList" @click="changeComponent3('receptList')">요청받은 문제</button>
            </div>
      </div>
      <div class="row" v-if="!this.myPage">
            <div class="col-6 col-md-6 col-lg-6 col-xl-6">
              <button class="btn1" id="articleList" @click="changeComponent1('articleList')">게시글</button>
            </div>
            <div class="col-6 col-md-6 col-lg-6  col-xl-6">
              <button class="btn1" id="receptList" @click="changeComponent3('receptList')">요청받은 문제</button>
            </div>
      </div>
      <div class="row">
        <div class="tab-item">
          <keep-alive>
            <component v-bind:is="comp"></component> 
          </keep-alive>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import articleList from '@/components/profile/downProfile/articleList'
import helpmeList from '@/components/profile/downProfile/helpmeList'
import receptList from '@/components/profile/downProfile/receptList'
import jwt_decode from 'jwt-decode'
const token = sessionStorage.getItem('jwt')
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
        const userPk = localStorage.getItem("userPk")
        if(userpk != userPk){
            this.myPage = false
        }else{
            this.myPage = true
        }
  },
  methods: {
      changeComponent1: function(componentName) {
        this.comp = componentName
        document.getElementById('articleList').style.backgroundColor = 'rgb(62, 171, 111)'
        document.getElementById('articleList').style.color = 'white'
        document.getElementById('helpmeList').style.backgroundColor = 'white'
        document.getElementById('helpmeList').style.color = 'black'
        document.getElementById('receptList').style.backgroundColor = 'white'
        document.getElementById('receptList').style.color = 'black'
      },
      changeComponent2: function(componentName){
        if(this.myPage){
          this.comp = componentName
          document.getElementById('articleList').style.backgroundColor = 'white'
          document.getElementById('articleList').style.color = 'black'
          document.getElementById('helpmeList').style.backgroundColor = 'rgb(62, 171, 111)'
          document.getElementById('helpmeList').style.color = 'white'
          document.getElementById('receptList').style.backgroundColor = 'white'
          document.getElementById('receptList').style.color = 'black'
        }
      },
      changeComponent3: function(componentName) {
        this.comp = componentName
        document.getElementById('articleList').style.backgroundColor = 'white'
        document.getElementById('articleList').style.color = 'black'
        document.getElementById('helpmeList').style.backgroundColor = 'white'
        document.getElementById('helpmeList').style.color = 'black'
        document.getElementById('receptList').style.backgroundColor = 'rgb(62, 171, 111)'
        document.getElementById('receptList').style.color = 'white'
      },
  }
}
</script>

<style scoped>
.row{
  margin-top: 30px; 
}
.feed-item{
  box-shadow: 0 0 0px 5px rgba(62 ,171 ,111 , 1);
}
.btn1{
  width:100%;
  font-size:15px;
  font-weight: 550;
  border: none;
  height: 30px;
  border-radius: 5%;
}
.tab-item {
  margin-top: 20px;
}
.btn:hover{
  background-color: antiquewhite;
}
#articleList{
  background-color: rgb(62, 171, 111);
  color: white;
}
</style>