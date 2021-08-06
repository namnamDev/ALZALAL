<template>
  <div class="container">

    <div class="row">
      <div class="col">
        <select class="me-2" id="category" name="category" v-model="category">
          <option value="solution">문제</option>
          <option value="algorithm">알고리즘</option>
          <option value="user">유저</option>          
        </select>
        <select
          id="pSite"
          name="problem-site"
          class="me-2"
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
      </div>
    </div>
    <div class="row mt-2 user1">
      <div class="col">
        <input v-model="inputData" id="placeholder" type="text" :placeholder="placeholder">
      </div>
    </div>

    <div class="row mt-2">
      <div class="col">
        <select id="language" name="language" v-model="language">
          <option value="null">선택안함</option>
          <option value="Java">Java</option>
          <option value="C">C</option>
          <option value="C++">C++</option>
          <option value="Python">python</option>
          <option value="Ruby">Ruby</option>
          <option value="JavaScript">JavaScript</option>
          <option value="Go">Go</option>          
        </select>
      </div>
    </div>

    <div class="row mt-2 user">
      <div class="col">
        <div class="search-algo-input-div">
          <input 
            v-model="algo"
            id="search-algo-input-1"
            type="text"
            placeholder="알고리즘을 입력해주세요(필수X)"                  
            @click="clickAlgoInput" 
            @keyup="filterFunction"  
          >
          <span class="btn-include span-include" @click="include">포함</span>
          <span class="btn-exclude span-exclude" @click="exclude">제외</span>
          <ul id="search-algo-ul">
            <li 
              id="search-algo-li" 
              v-for="item,idx in algoList" :key="idx"
              @click="clickAlgo($event)">
              {{item}}
              <!-- <span class="span-include" @click="include($event)">포함</span>
              <span class="span-exclude">제외</span> -->
            </li>            
          </ul>
        </div>
      </div>
    </div>

    <div class="row mt-2 user">
      <div class="col">
        <p class="mb-0">포함</p>          
        <div class="include p-2">
        </div>
      </div>
    </div>

    <div class="row mt-2 user">
      <div class="col">
        <p class="mb-0">제외</p>            
        <div class="exclude p-2 mb-5">
        </div>
      </div>
    </div>    

    <div class="footer mb-2">
      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      <button type="button" class="btn" id="submit" data-bs-dismiss="modal" @click="submit">검색하기</button>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  props: {
    value: String
  },
  data() {
    return{
      category: 'solution',
      pSite: 'boj',
      placeholder: '문제번호를 입력해주세요',
      language: 'Java',
      algoList: [],
      algo:'',
      inputData: '',
    }
  },
  watch: {
    category: function() {
      if (this.category == 'solution'){
        this.placeholder = '문제번호를 입력해주세요'
        document.querySelector('#placeholder').style.display = 'block'
        document.querySelector('#language').style.display = 'block'
        document.querySelector('#pSite').style.visibility = 'visible'
        const user = document.getElementsByClassName('user')
        user.forEach(element => {
          element.style.display = 'block'
        });
        const user1 = document.querySelector('.user1')
        user1.style.marginBottom = '0px'
      }
      else if (this.category == 'user'){
        this.placeholder = '유저 이름을 입력해주세요'
        document.querySelector('#placeholder').style.display = 'block'
        document.querySelector('#language').style.display = 'none'
        document.querySelector('#pSite').style.visibility = 'hidden'
        const user = document.getElementsByClassName('user')
        user.forEach(element => {
          element.style.display = 'none'
        });
        const user1 = document.querySelector('.user1')
        user1.style.marginBottom = '50px'
        
      }
      else{
        document.querySelector('#placeholder').style.display = 'none'
        document.querySelector('#language').style.display = 'block'
        document.querySelector('#pSite').style.visibility = 'hidden'
        const user = document.getElementsByClassName('user')
        user.forEach(element => {
          element.style.display = 'block'
        });
        const user1 = document.querySelector('.user1')
        user1.style.marginBottom = '0px'
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
  },

  methods: {
    getData() {
      const includeAlgoList = document.querySelectorAll('.includeAlgo')
      const excludeAlgoList = document.querySelectorAll('.excludeAlgo')
      let includeAlgo = []
      let excludeAlgo = []
      includeAlgoList.forEach(element => {
        includeAlgo.push(element.innerText)
      });
      excludeAlgoList.forEach(element => {
        excludeAlgo.push(element.innerText)
      });
      const data = {
        category: this.category,
        pSite: this.pSite,
        language: this.language,
        inputData: this.inputData,
        includeData: includeAlgo,
        excludeData: excludeAlgo,
      }
      return data
    },
    submit() {
      const searchData = this.getData()
      const category = searchData.category
      let data = {}
      // 문제풀이, qna인 경우
      if (category == 'solution') {
        data.category = searchData.category
        data.language = searchData.language
        data.includeAlgo = searchData.includeData,
        data.excludeAlgo = searchData.excludeData
        data.problem = searchData.pSite + searchData.inputData
        this.$store.dispatch('createSearchArticle',data)        
        this.$router.push({name : 'searchProblem'})
      }
      // 알고리즘인 경우
      else if (category == 'algorithm'){
        data.category = searchData.category
        data.language = searchData.language
        data.includeAlgo = searchData.includeData,
        data.excludeAlgo = searchData.excludeData
        this.$store.dispatch('createSearchArticle',data)
        this.$router.push({name : 'searchAlgorithm'})
      }
      // 유저인 경우
      else if (category == 'user'){
        data.user = searchData.inputData
        this.$store.dispatch('createSearchArticle',data)
        this.$router.push({name : 'searchUser', params:{user:searchData.inputData}})
      }
    },

    // blur() {
    //   const ul = document.querySelector('#search-algo-ul')
    //   ul.style.display = 'none'
    // },
    clickAlgoInput() {      
      // input(알고리즘을 입력해주세요) 클릭시 ul태그 보이게
      const ul = document.querySelector('#search-algo-ul')
      if (ul.style.display == 'block') {
        ul.style.display = 'none'
      }
      else{
        ul.style.display = 'block'
      }
    },
    clickAlgo: function(event){
      this.algo = event.target.innerText
      const ul = document.querySelector('#search-algo-ul')
      ul.style.display = 'none'
    },

    filterFunction() {
      var input = document.getElementById("search-algo-input-1");
      var filter = input.value.toUpperCase();
      var div = document.querySelector(".search-algo-input-div");
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

    clickSearch: function() {
      var commentForm = $('.search-detail')
 
      // commentForm 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
      if( commentForm.is(":visible") ){
          commentForm.slideUp(500);
      }else{
          commentForm.slideDown(500);
      }
    },

    include: function(){
      // console.log($(event.target).parent('li')[0].innerText[1])
      const algoHastag = document.createElement("span")

      algoHastag.innerText = this.algo;
      algoHastag.style.display = "inline-block";
      algoHastag.style.fontSize = "14px";
      algoHastag.style.borderRadius = "3px";
      algoHastag.style.backgroundColor = "rgba(221,223,230,1)";
      algoHastag.style.padding = "4px 8px";
      algoHastag.style.marginBottom = "4px";
      algoHastag.classList.add("me-3");
      algoHastag.classList.add("includeAlgo")
      algoHastag.style.cursor = "pointer";
      algoHastag.addEventListener("click", function (event) {
        event.target.remove();
      });
      if (this.algo != ''){
        let boxAlgo = document.querySelector('.include')
        if (boxAlgo.childElementCount != 4) {
          boxAlgo.appendChild(algoHastag);
        }
        this.algo = ''
      }
    },
    exclude: function() {
      const algoHastag = document.createElement("span")

      algoHastag.innerText = this.algo;
      algoHastag.style.display = "inline-block";
      algoHastag.style.fontSize = "14px";
      algoHastag.style.borderRadius = "3px";
      algoHastag.style.backgroundColor = "rgba(221,223,230,1)";
      algoHastag.style.padding = "4px 8px";
      algoHastag.style.marginBottom = "4px";
      algoHastag.classList.add("me-3");
      algoHastag.style.cursor = "pointer";
      algoHastag.classList.add("excludeAlgo")

      algoHastag.addEventListener("click", function (event) {
        event.target.remove();
      });

      if (this.algo != ''){
        let boxAlgo = document.querySelector('.exclude')
        if (boxAlgo.childElementCount != 4) {
          boxAlgo.appendChild(algoHastag);
        }
        this.algo = ''
      }
    }
  }
}
</script>

<style scoped>
  #submit{
    color:rgb(109, 97, 97);
    background-color: rgba(161,212,226,1);
    margin:0 10px;
  }
  #submit:hover{
    background-color: rgb(133, 191, 207);
  }
  .btn{
    border-radius: 4px;
  }
  #category{
    width: 100px;
    height:30px;
  }
  #language{
    width:100px;
    height:30px;
  }
  #pSite{
    width:100px;
    height:30px;
  }
  .search-algo-input-div{
    position: relative;
  }
  #search-algo-input-1{
    border-radius: 3px;
    width: 300px;
  }
  #search-algo-ul{
    position:absolute;
    padding-left: 3px;
    border: 1px solid black;
    background-color: white;
    list-style: none;
    text-align: start;
    display: none;
    overflow: auto;
    height: 150px;
    width: 300px;
    box-shadow: 0 20px 20px rgba(39, 40, 41, 0.2);
    z-index:1;
  }
  #search-algo-li{
    position: relative;
  }
  #search-algo-li:hover {
    background-color: antiquewhite;
  }
  #search-algo-li:hover > .span-include{
    display: block;
  }
  #search-algo-li:hover > .span-exclude{
    display: block;
  }
  .include{
    /* border: 1px solid black; */
    /* height:40px; */
    width: 450px;
    border-radius: 3px;
  }
  .exclude{
    /* border: 1px solid black; */
    /* height:40px; */
    width: 450px;
    border-radius: 3px;
  }
  .span-include{
    background-color: rgba(161,212,226,1);
    border-radius: 5px;
    padding: 3px;
    font-size: 15px;
    font-weight: bold;
    margin: 0 5px;
    /* position: absolute; */
    /* right: 45px; */
    /* top:-10px; */
    cursor:pointer;
    /* display:none; */
  }
  .span-exclude{
    background-color: rgb(246, 163, 142);
    border-radius: 5px;
    padding: 3px;
    font-size: 15px;
    font-weight:bold;
    /* position: absolute; */
    /* right: 10px; */
    /* top:-10px; */
    cursor:pointer;
    /* display:none; */
  }
  .user{
    margin-bottom: 20px;
  }
  #placeholder{
    width: 210px;
  }
  .footer{
    position:absolute;
    right: 0;
    text-align: end;
    bottom:0;
    padding-top:15px;
    border-top: 1px solid rgb(212, 212, 212);
    width: 100%;
    margin-top: 30px;
  }
</style>