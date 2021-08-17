<template>
  <div class="container1 py-5" @click="hideAlgoList">

    <div class="row mt-5">
      <div class="box">카테고리</div>
      <select id="select" name="category" v-model="category">
        <option value="solution">문제풀이</option>
        <option value="QnA">Q&A</option>
        >
      </select>
    </div>

    <div class="row select-div mt-3">
      <div class="row algo-div">
        <div class="algo-input-div me-2">
          <input
            id="algo-input-1"
            type="text"
            placeholder="알고리즘 입력(최대4개)"
            @keyup="filterFunction"
          />
          <ul id="algo-ul" @click="clickAlgoUl">
            <li id="algo-li" v-for="item,idx in algoList" :key="idx">
              {{item}}
            </li>
          </ul>
        </div>
        <div class="box-algo"></div>
      </div>
    </div>

    <div class="row mt-3">
      <div id="psite" class="box">문제사이트</div>
      <select
        id="select"
        name="problem-site"
        class="decorated"
        v-model="pSite"          
      >
        <option value="boj">백준</option>
        <option value="swea">SWEA</option>
        <option value="algopost">algopost</option>
        <option value="atcoder">atcoder</option>
        <option value="jungol">jungol</option>
        <option value="codeforce">codeforce</option>
        <option value="programmers">programmers</option> 
        >
      </select>

      <div class="box ms-2">문제번호</div>
      <input v-model="pNum" id="problem-input" type="text"/>

      <div class="box ms-2">사용언어</div>
        <select
          id="select"
          name="language"
          v-model="language"
        >
          <option value="Java" class="litags">Java</option>
          <option value="C">C</option>
          <option value="C++">C++</option>
          <option value="Python">python</option>
          <option value="Ruby">Ruby</option>
          <option value="JavaScript">JavaScript</option>
          <option value="Go">Go</option>
        </select>
    </div>

    <div class="row title mt-3">
      <input
        v-model="title"
        id="title"
        type="text"
        placeholder="제목을 입력해주세요."
      />
    </div>

    <div class="row editor mt-3">
      <Editor ref="toastEditor" />
    </div>
    <div class="row mt-2 mb-5 pb-5">
      <button @click="submit">작성하기</button>
      
    </div>

  </div>

</template>

<script>
import Editor from "@/components/createArticle/Editor";
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "CreateArticle",
  components: {
    Editor,
  },
  data() {
    return{
      category: "solution",
      title: "",
      content: "",
      pNum: "",
      pSite: "boj",
      language: "Java",
      algoList: [],
    }
  },

  computed: {
    articleClass: function () {
      if (this.category == 'solution') {
        return 'A01'
      } else {
        return 'A00'
      }
    }
  },

  created: function() {
    axios({
        method: 'get',
        url: `${SERVER_URL}/algorithmList`,
      })   
      .then(res =>{
        res.data.algorithmList.forEach(element => {
          this.algoList.push(element)
        });   
      })
      .catch(err =>{  
        console.log(err)
      })
    const token = sessionStorage.getItem('jwt')
    if(!token){
      this.$router.push({name:'login'})
    } 
  },

  methods: {
    hideAlgoList(event){
      const ultag = document.querySelector('#algo-ul')
      if (event.target.id == "algo-input-1"){
        if (ultag.style.display == 'block'){
          ultag.style.display = 'none'
        }
        else{
          ultag.style.display = 'block'
        }
      }
      else if (ultag.style.display == 'block'){
        ultag.style.display = 'none'
      }
    },

    // toast editor에서 작성된 내용 가져오기
    getContent() {
      return this.$refs.toastEditor.getContent();
    },
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
      if (!this.pNum){
        this.$swal.fire({
        text: "문제 번호를 입력해주세요",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '확인',
        cancelButtonText: '취소'
        }).then(() => {          
          var input = document.querySelector("#problem-input");
          window.scrollTo({top:input.offsetTop, behavior:'smooth'});      
        })     
      }
      else if(!this.title){
        this.$swal.fire({
        text: "제목을 입력해 주세요",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '확인',
        cancelButtonText: '취소'
        }).then(() => {          
          var input = document.querySelector("#title");
          window.scrollTo({top:input.offsetTop, behavior:'smooth'});      
        })   
      }
      else if(!this.getContent()){
        this.$swal.fire({
        text: "내용을 입력해 주세요",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '확인',
        cancelButtonText: '취소'
        }).then(() => {          
          var input = document.querySelector(".editor");
          window.scrollTo({top:input.offsetTop, behavior:'smooth'});      
        })   
      }
      else{
        let algoList = [];
        const boxAlgo = document.querySelectorAll(".box-algo span");
        boxAlgo.forEach((element) => {
          algoList.push(element.innerText);
        });
  
        const data = {
          category: this.articleClass,
          title: this.title,
          content: this.getContent(),
          pNum: this.pNum,
          pSite: this.pSite,
          language: this.language,
          algo: algoList,
        }
  
        this.$swal.fire({
          text: "글을 작성하시겠습니까?",
          icon: 'question',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: '확인',
          cancelButtonText: '취소'
          }).then((result) => {
            if(result.value){
              axios({
                method: 'post',
                url: `${SERVER_URL}/article/article`,
                data: data,
                headers: this.getToken(),
              })   
              .then(() =>{
                this.$swal('글을 작성하였습니다.');
                this.$router.push({ name: 'timeline' })                         
              })
              .catch(err =>{  
                console.log(err)
              })            
            }          
          })   
      }      
    },
    

    clickAlgoUl(event){
      let boxAlgo = document.querySelector(".box-algo");
      const algoHastag = document.createElement("span");

      algoHastag.innerText = event.target.innerText;
      algoHastag.style.display = "inline-block";
      algoHastag.style.fontSize = "14px";
      algoHastag.style.borderRadius = "3px";
      algoHastag.style.backgroundColor = "rgba(221,223,230,1)";
      algoHastag.style.padding = "4px 8px";
      algoHastag.classList.add("me-3");
      algoHastag.addEventListener("click", function (event) {
        event.target.remove();
      });
      algoHastag.style.cursor = "pointer";
      if (boxAlgo.childElementCount != 4) {
        let flag = true

        boxAlgo.childNodes.forEach(element => {
          if (algoHastag.innerText === element.innerText){
            flag = false                
          }
        });
        if (flag){
          boxAlgo.appendChild(algoHastag);
        }     

        const ul = document.querySelector('#algo-ul')
        ul.style.display = 'none'       
      }    
    },

    // 검색필터 적용
    filterFunction() {
      var input = document.getElementById("algo-input-1");
      var filter = input.value.toUpperCase();
      var div = document.querySelector(".algo-input-div");
      var li = div.getElementsByTagName("li");

      for (var i = 0; i < li.length; i++) {
        const txtValue = li[i].textContent || li[i].innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          li[i].style.display = "";
        } else {
          li[i].style.display = "none";
        }
      }
    },
  },

}
</script>

<style scoped>
.container1{
  width: 100%;
  padding: 0 150px;
}
.row{
  --bs-gutter-x: 0;
}
.box {
  border: 1px solid black;
  border-right-style:none;
  height: 30px;
  width: 110px;
  /* border-radius: 3px; */
  text-align: center;
}
#select {
  width: 120px;
  padding: 0 5px;
  /* border-radius: 3px; */
}
.select-div {
  height:30px;
  position: relative;
}
.algo-div {
  position: absolute;
}
.algo-input-div{
  padding: 0px;
  width: 230px;
  z-index: 1;
}
.box-algo {
  width: calc(100% - 240px);
  text-align: start;
  align-items: center;
  border-radius: 3px;
}
#algo-input-1{
  width: 100%;
  padding: 0 5px;
}
ul {
  padding-left: 3px;
  border: 1px solid black;
  background-color: white;
  list-style: none;
  text-align: start;
  display: none;
  overflow: auto;
  height: 200px;
  box-shadow: 0 20px 20px rgba(39, 40, 41, 0.2);
  z-index: 1;
}
li:hover {
  background-color: antiquewhite;
  cursor: default;
}
#problem-input{
  width: 120px;
  padding: 0 5px;
}
#title{
  width: 100%;
  padding: 0 5px;
}
.editor {
  overflow-wrap: break-word;
}
button {
  width: 110px;
  height: 40px;
  border-radius: 4px;
  border-style: none;
  color: black;
  background-color: rgb(176, 218, 230);
}
button:hover{
  background-color: rgb(129, 198, 218);
}

</style>