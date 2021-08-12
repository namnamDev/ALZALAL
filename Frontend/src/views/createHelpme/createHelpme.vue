<template clsas="temp">
  <div class="create-form">
    <div class="container">
      <form @submit.prevent="submit">
      <div class="row justify-content-start mb-3">


        
        <div id="psite" class="box col-sm-2 mb-2">문제사이트</div>
        <select
          id="select"
          name="problem-site"
          class="col-sm-2 m-size mb-2"
          v-model="pSite"
        >
          <option value="boj">백준</option>
          <option value="swea">SWEA</option>
          <option value="algopost">algopost</option>
          <option value="atcoder">atcoder</option>
          <option value="jungol">jungol</option>
          <option value="codeforce">codeforce</option>
          <option value="programmers">programmers</option>          
        </select>

        <div class="box col-sm-2 mb-2">문제번호</div>
        <input v-model="pNum" id="problem-input" type="text" class="m-size col-sm-2 mb-2" />
      </div>

      <div class="row">
        <div class="col title ">
          <input
            v-model="content"
            id="title"
            type="text"
            placeholder="질문을 입력해주세요"
          />
        </div>
      </div>
      <div class="row submit">
        <button type="submit" :disabled="!pNum || !pSite || !content" class="requestBtn">제출하기</button>
      </div>
      </form>
    </div>
    
  </div>
</template>

<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "createHelpme",
  data() {
    return {
      pSite: '',
      pNum: '',
      content: '',
      targetPK: '',
    };
  },

  computed: {
  },


  created: function() {
    const token = sessionStorage.getItem('jwt')
    if(!token){
      this.$router.push({name:'login'})
    } 
    const userPk = localStorage.getItem("userPk")
        console.log(userPk)
        if(userPk){
            this.targetPK = userPk
        }
  },

  methods: {

    // token 값 가져오기
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
    
    // 글 작성하기
    submit() {
      const data = {
        problemSiteName: this.pSite,
        problemNo: this.pNum,
        receptMemberNo: this.targetPK,
        content: this.content,
        
      }
      console.log(data)
      axios({
        method: 'post',
        url: `${SERVER_URL}/helpme`,
        data: data,
        headers: this.getToken(),
      })   
      .then(res =>{
        console.log(res);
        alert("작성 완료!")
        this.$router.push({ name: 'profilePage' })       
      })
      .catch(err =>{  
        console.log(err)
        console.log(this.getToken())
      })
    },
  },
};
</script>

<style scoped>
.submit{
  margin-top: 150px;
}
#search{
 display:none; 
}
.box {
  border: 1px solid black;
  height: 30px;
  width: 110px;
  border-radius: 3px;
  text-align: center;
}

.create-form {
  margin-top: 150px;
  margin-bottom: 13vw;
  width: 70%;
  height: 500px;
  left: 23%;
  position: absolute;
}

.container {
  position: absolute;
  background-color: white;
  width: 100%;
  height: 60vw;
  z-index: 1;
  /* border: 1px solid black; */
}

#select {
  width: 120px;
  border-radius: 3px;
}
.select-div {
  position: relative;
}
#problem-input {
  width: 120px;
}
.site-div {
  position: absolute;
  top: 95px;
}
.title {
  width: 80%;
  /* border: 1px solid black; */
  height: 30px;
  margin-top: 20px;
  padding: 0 0;
}
.title > input {
  width: 70%;
  border-radius: 3px;
}
.requestBtn {
  width: 110px;
  height: 40px;
  border-radius: 4px;
  border-style: none;
  font-weight: 550;
  color: white;
  background-color: rgb(62, 171, 111);
}
.requestBtn.disabled{
  color: black;
  background-color: rgb(172, 196, 182);
}
.m-size{
  margin-right:8px;
}
@media (max-width: 576px) {
  .create-form {
    left: 5%;
    width: 100%;
  }
  .site-div {
    top: 130px;
  }
  .title {
    margin-top: 20px;
  }
  .m-size{
    margin-right:250px;
  }
  .title {
    margin-top: 20px;
  }
}
@media (max-width: 468px) {
  .m-size{
    margin-right:145px;
  }
}
#title{
  line-height: 10;
  
}
</style>