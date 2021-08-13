<template>
  <div class="create-form">
    <!-- <notifications group="notifyApp" position="top center" width="400px"/> -->

    <div >
      <div class="row justify-content-start mb-3">
        <div class="box">카테고리</div>
        <select id="select" name="category" v-model="category">
          <option value="solution">문제풀이</option>
          <option value="QnA">Q&A</option>
          >
        </select>
      </div>

      <div class="row select-div">
        <div class="row mb-3 text-center algo-div">
          <div class="algo-input-div me-2">
            <input
              id="algo-input-1"
              type="text"
              placeholder="알고리즘을 입력해주세요"
              @click="clickAlgoInput"
              @keyup="filterFunction"
              @blur="blur"
            />
            <ul id="algo-ul">
              <li id="algo-li" v-for="item,idx in algoList" :key="idx">
                {{item}}
              </li>
            </ul>
          </div>
          <div class="box-algo"></div>
        </div>
      </div>

      <div class="row justify-content-start mb-3 site-div">
        <div id="psite" class="box col-sm-2 mb-2">문제사이트</div>
        <select
          id="select"
          name="problem-site"
          class="col-sm-2 m-size mb-2 decorated"
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

        <div class="box col-sm-2 mb-2">사용언어</div>
        <select
          id="select"
          name="language"
          v-model="language"
          class="col-sm-2 mb-2 m-size"
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

      <div class="row">
        <div class="col title ">
          <input
            v-model="title"
            id="title"
            type="text"
            placeholder="제목을 입력해주세요."
          />
        </div>
      </div>

      <div class="row editor">
        <Editor ref="toastEditor" />
      </div>
      <div class="row mt-2 mb-5 pb-5">
        <button @click="submit">작성하기</button>
        
      </div>
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
    return {
      category: "solution",
      title: "",
      content: "",
      pNum: "",
      pSite: "boj",
      language: "Java",
      algoList: [],
    };
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
    blur() {
      const ul = document.querySelector('#algo-ul')
      setTimeout(() => {
        if (ul.style.display == 'block'){
          ul.style.display = 'none'        
        }
      }, 100);
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
          }).then(() => {          
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
          })   
      }      
    },

    
    clickAlgoInput() {
      // 각 li태그를 선택할때마다 span태그를 추가(hastag 추가)
      const liTags = document.querySelectorAll("#algo-li")
      liTags.forEach((element) => {
        element.addEventListener("click", function (event) {
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

          // console.log(boxAlgo.childNodes)
          if (boxAlgo.childElementCount != 4) {
            let flag = true
            boxAlgo.childNodes.forEach(element => {
              if (algoHastag.innerText == element.innerText){
                flag = false
                
              }
              console.log(element.innerText)
            });
            if (flag){
              boxAlgo.appendChild(algoHastag);
            }            
          }
        });
      });
      // input(알고리즘을 입력해주세요) 클릭시 ul태그 보이게
      const ul = document.querySelector('#algo-ul')
      if (ul.style.display == 'block') {
        ul.style.display = 'none'
      }
      else{
        ul.style.display = 'block'
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
};
</script>

<style scoped>
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
  width: 60%;
  height: 500px;
  left: 50%;
  transform: translateX(-50%);
  position: absolute;
  background-color: white;
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
.algo-div {
  position: absolute;
}
.algo-input-div {
  padding: 0;
  width: 230px;
  z-index: 1;
}
#algo-input-1 {
  width: 100%;
}
.show {
  display: block;
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
}
li:hover {
  background-color: antiquewhite;
}
.box-algo {
  border: 1px solid black;
  /* height: 70px; */
  width: 70%;
  display: inline;
  text-align: start;
  align-items: center;
  border-radius: 3px;
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
  margin-top: 100px;
  padding: 0 0;
}
.title > input {
  width: 68%;
  border-radius: 3px;
}
#title{
  width: 100%;
}
.editor {
  width: 100%;
  transform: translateX(-11px);
  margin-top: 20px;
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
.m-size{
  margin-right:8px;
}



@media (max-width: 576px) {
  .create-form {
    /* left: 5%; */
    width: 100%;
    margin-left:25px;
  }
  .site-div {
    top: 130px;
  }
  .title {
    margin-top: 190px;
  }
  .editor {
    top: 130px;
  }
  .m-size{
    margin-right:250px;
  }
  .algo-input-div{
    margin-bottom: 8px;
  }
  .box-algo{
    padding: 0 0;
  }
  .title {
    margin-top: 210px;
  }
}
@media (max-width: 468px) {
  .m-size{
    margin-right:145px;
  }
}

</style>