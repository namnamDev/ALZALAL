<template clsas="temp">
  <div class="container create-form">
    <div class="row">
      <div class="col-xl-2 col-lg-3 col-md-2 col-sm-3 col-1">

      </div>
      <div class="ps-0 col-xl-9 ms-xl-5 col-lg-6 col-md-10 col-sm-9 col-10">        
        <div class="row justify-content-start mb-3">
          <div class="box">
            카테고리
          </div>
          <select id="select" name="category" v-model="category">
              <option value="solution" selected>문제풀이</option>
              <option value="QnA">Q&A</option>>
          </select>          
        </div>

        <div class="row select-div">
          <div class="row mb-3 text-center algo-div">            
            <div class="algo-input-div me-2">
              <input v-model="algoList" id="algo-input" type="text" placeholder="알고리즘을 입력해주세요" @click="clickAlgoInput" @keyup="filterFunction">      
              <ul id="algo-ul">
                <li>bfs</li>  
                <li>dfs</li>  
                <li>dp</li>  
                <li>다이나믹 프로그래밍</li>  
                <li>tree</li>  
                <li>정렬</li>  
                <li>탐색</li>  
                <li>깊이우선탐색</li>  
                <li>넓이우선탐색</li>  
                <li>다익스트라</li>  
                <li>탐색</li>  
              </ul> 
            </div>
            <div class="box-algo">

            </div>
          </div>

          <div class="row justify-content-start mb-3 site-div">
            <div class="box">
              문제사이트
            </div>
            <select id="select" name="problem-site" class="me-2" v-model="problemSite">
                <option value="백준" selected>백준</option>
                <option value="SWEA">SWEA</option>>
            </select>     

            <div class="box problem-no">
              문제번호
            </div>
            <input id="problem-input" type="text" class="me-2">      

            <div class="box">
              사용언어
            </div>
            <select id="select" name="language" v-model="language">
                <option value="Java">Java</option>
                <option value="C">C</option>
                <option value="C++">C++</option>
                <option value="python">python</option>
            </select>      
          </div>
        </div>

        <div class="row editor">
          <Editor ref="toastEditor" />   
          <button @click="submit">작성하기</button>
        </div>


      </div>
    </div>
  </div>
</template>

<script>
import Editor from '@/components/createArticle/Editor'

export default {
  name: 'CreateArticle',
  components: {
    Editor,
  },
  data() {
    return {
      problemSite: '',
      language: '',
      category: '',
      algoList: '',
    }
  },

  computed: {
    liTags: function() {
      return document.querySelectorAll('#algo-ul > li')
    }
  },

  mounted: function() {

    // 각 li태그를 선택할때마다 span태그를 추가(hastag 추가)
    this.liTags.forEach(element => {
      element.addEventListener('click', function(event){
        let boxAlgo = document.querySelector('.box-algo')
        const algoHastag = document.createElement('span')

        algoHastag.innerText = event.target.innerText
        algoHastag.style.display = 'inline'
        algoHastag.style.fontSize = '13px'
        algoHastag.style.borderRadius = '3px'
        algoHastag.style.backgroundColor = 'rgba(221,223,230,1)'
        algoHastag.style.paddingLeft = '8px'
        algoHastag.classList.add('me-3')

        const deleteButton = document.createElement('button')
        deleteButton.innerText = 'X'
        deleteButton.style.fontSize = '13px'
        deleteButton.style.color = 'red'
        deleteButton.style.backgroundColor = 'transparent'
        deleteButton.style.borderStyle = 'none'
        deleteButton.addEventListener('click', function(event){
          event.target.parentNode.remove()
        })
        algoHastag.appendChild(deleteButton)

        if(boxAlgo.childElementCount != 4){
          boxAlgo.appendChild(algoHastag)
        }

      })
    });
  },

  methods: {
    // toast editor에서 작성된 내용 가져오기
    getContent() {
      return this.$refs.toastEditor.getContent()
    },
    submit() {
      let algoList = []
      const boxAlgo = document.querySelectorAll('.box-algo span')
      boxAlgo.forEach(element => {
        algoList.push(element.innerText)
      });
      console.log(this.category, this.problemSite, this.category, this.getContent(),algoList)

    },
    // input(알고리즘을 입력해주세요) 클릭시 ul태그 보이게
    clickAlgoInput() {
      document.getElementById('algo-ul').classList.toggle('show')
    },
    // 검색필터 적용
    filterFunction() {
      console.log('filterfunction')
      var input = document.getElementById("algo-input");
      var filter = input.value.toUpperCase();
      var div = document.querySelector(".algo-input-div");
      var li = div.getElementsByTagName("li");

      for (var i = 0; i < li.length; i++) {
        const txtValue = li[i].textContent || li[i].innerText;
        console.log(i,txtValue)
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          li[i].style.display = "";
        } else {
          li[i].style.display = "none";
        }
      }

    }
  }

};
</script>

<style scoped>
.create-form{
  margin-top: 16vw;
  margin-bottom: 13vw;
}
.box {
  border: 1px solid black;
  height: 30px;
  /* width: 8vw; */
  width: 110px;
  border-radius: 3px;
}
.box-algo{
  /* border: 1px solid black; */
  height: 30px;
  /* width: 16vw; */
  width: 550px;
  text-align: start;
  /* display: flex; */
  align-items: center;
  border-radius: 3px;
}

#algo-input{
  width: 100%;
  /* width: 550px; */
}
#problem-input{
  width: 110px;
}
#select{
  width:120px;
  border-radius: 3px;
}


.editor{
  width: 800px;
  transform: translateX(-11px);
  margin-top:100px;
}

.algo-input-div{
  padding: 0;
  width: 230px;
  z-index:1;
}

.algo-list{
  /* position: absolute; */
  border: 1px solid black;
  width: 100%;
  height: 30px;
}

.select-div{
  position: relative;
}

.algo-div{
  position:absolute;
}

.site-div{
  position:absolute;
  top: 50px;
}
.show{
  display: block;
}

ul{
  padding-left: 3px;
  border: 1px solid black;
  background-color: white;
  list-style: none;
  text-align: start;
  display:none;
  overflow: auto;
  height:200px;
}

li:hover {
  background-color: antiquewhite;
}

button{
  position: absolute;
  display: flex;
  width: 120px;
  justify-content: center;
  right: 0;
  bottom: -40px;
}


@media (max-width:576px) {
  #algo-input{
    width: 50vw;
    /* width: 550px; */
  }
  #problem-input{
    width: 150px;
  }
  .box{
    width: 150px;
  }
  #select{
    width:150px;
  }
}


</style>