<template>
  <div>
    <div id="search" >    
      <div id="search-bar">
        <div class="search-box" data-bs-toggle="modal" data-bs-target="#exampleModal" @click="clickSearch">
          <i class="fas fa-search"></i>
          검색해주세요.
        </div>
        
  <!--       
        <div class="search-detail">
          <select id="category" name="category" v-model="category">
            <option value="solution">문제</option>
            <option value="algorithm">알고리즘</option>
            <option value="user">유저</option>          
          </select>

          <select id="language" name="language" v-model="language">
            <option value="Java">Java</option>
            <option value="C">C</option>
            <option value="C++">C++</option>
            <option value="Python">python</option>
            <option value="Ruby">Ruby</option>
            <option value="JavaScript">JavaScript</option>
            <option value="Go">Go</option>          
          </select>        

          <div class="search-algo-input-div">
            <input 
              v-model="algo"
              id="search-algo-input-1"
              type="text"
              placeholder="알고리즘을 입력해주세요"      
              @click="clickAlgoInput" 
              @keyup="filterFunction"   
            >
            <span class="btn-include" @click="include">포함</span>
            <span class="btn-exclude" @click="exclude">제외</span>
            <ul id="search-algo-ul">
              <li 
                id="search-algo-li" 
                v-for="item,idx in algoList" :key="idx"
                @click="clickAlgo($event)">
                {{item}}
              </li>            
            </ul>
          </div>

          <div class="include p-2">
            <p class="mb-0">포함</p>          
          </div>
          <div class="exclude p-2">
            <p class="mb-0">제외</p>          
          </div>

          <button class="btn-search">검색하기</button>
          
        </div> -->
      </div>     
    </div>
    <Modal />
  </div>
</template>

<script>
import $ from 'jquery'
import axios from 'axios';
import Modal from '@/components/search/SearchModal.vue'

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  components: {
    Modal
  },
  data() {
    return{
      language: 'Java',
      category: 'solution',
      algoList: [],
      algo: '',
    }
  },
  computed: {

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
#search{
  height: 100vh;
  width: 280px;
  right: 70px;
  top: 170px;
  position:fixed;
  /* border-left: 1px solid black;   */
  display: none !important;
}
#search-bar{
  position: absolute;
  z-index:-2;
}
.fas{
  font-size: 20px;
  margin-right:10px;
}
.search-box{
  border:1px solid black;
  width: 250px;
  border-radius: 5px;
  padding:4px 7px;  
  cursor: pointer;
}
.search-detail{
  /* border:1px solid black; */
  /* position: absolute; */
  top:50px;
  width: 300px;
  left: -25px;
  height: 300px;
  border-radius: 5px;
  padding:4px 7px;  
  /* display:none; */
}
#category{
  /* position: absolute; */
  width: 130px;
  height:30px;
  border-radius: 3px;
}
#language{
  width: 130px;
  height:30px;
  border-radius: 3px;
  /* position:absolute; */
  top: 50px;
}
.search-algo-input-div{
  border-radius: 3px;
  /* position: absolute; */
  top: 100px;
  z-index: 1;
  display: inline;
}
.show {
  display: block;
}
#search-algo-input-1{
  width:200px;
}
#search-algo-ul{
  padding-left: 3px;
  border: 1px solid black;
  background-color: white;
  list-style: none;
  text-align: start;
  display: none;
  overflow: auto;
  height: 150px;
  box-shadow: 0 20px 20px rgba(39, 40, 41, 0.2);
}
#search-algo-li:hover {
  background-color: antiquewhite;
}
.include{
  border: 1px solid black;
  height:140px;
  width: 200px;
  /* position: absolute; */
  top: 150px;
}
.exclude{
  border: 1px solid black;
  height:140px;
  width: 200px;
  /* position: absolute; */
  top: 320px;
}
.btn-include{
  /* position:absolute; */
  width: 40px;
  top:3px;
  right:-50px;
  cursor: pointer;
}
.btn-exclude{
  /* position:absolute; */
  width:40px;
  top:3px;
  right: -100px;
  cursor: pointer;
}
.btn-search{
  /* position: absolute; */
  width:200px;
  top: 470px;

}

@media (min-width:992px){
  #search{
    display:block !important; 
  }
}
</style>
