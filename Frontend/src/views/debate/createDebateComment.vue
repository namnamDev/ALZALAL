<template>
  <div>
    <Editor
      ref="toastEditor"
      height="500px"
      previewStyle="tab"
      :options="editorOptions"
     />
    <div class="text-end mt-3 mb-5">
      <button @click="submit">저장</button>
    </div>
  </div>
</template>
 
<script>
import { Editor } from '@toast-ui/vue-editor'
import 'codemirror/lib/codemirror.css' // codemirror style
import '@toast-ui/editor/dist/toastui-editor.css' // Editor style

// add dependencies related code-syntax-highlight
import 'highlight.js/styles/github.css';
import hljs from 'highlight.js';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';

import 'tui-color-picker/dist/tui-color-picker.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';

import axios from 'axios';

import $ from 'jquery'

const SERVER_URL = process.env.VUE_APP_SERVER_URL 

export default {
  components: {
    Editor,
  },
  data() {
    return {
      editorText: '',
      editorOptions: {
        minHeight: '300px',
        language: 'ko',
        useCommandShortcut: true,
        usageStatistics: true,
        hideModeSwitch: false,
        placeholder: 'hello world',
        plugins: [[codeSyntaxHighlight, { hljs }],colorSyntax]
      },
    }
  },
  computed: {

  },
  methods: {
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
    // 마크다운 에디터 내용 가져오기
    getContent() {
      return this.$refs.toastEditor.invoke('getMarkdown')
    },
    setContent(content) {
      this.$refs.toastEditor.invoke('setMarkdown', content)
    },
    //댓글 작성버튼 클릭
    submit() {
      this.$swal('댓글을 작성하였습니다.');
      const discussionNo = localStorage.getItem('discussionNo')
      axios({
        method: 'post',
        url: `${SERVER_URL}/comment/discussion/${discussionNo}`,
        headers: this.getToken(),
        data:{
          'commentContent' : this.getContent()
        }
      })   
      .then(() => {
        this.setContent('')
        axios({
          method: 'get',
          url: `${SERVER_URL}/comment/discussion/${discussionNo}`,
        })   
        .then(res =>{
          this.$store.dispatch('createDebateComment',res.data.articleComments)
        })
        .catch(err =>{  
          console.log(err)
        })
      })
      .catch(err =>{  
        console.log(err)
      })

      var commentForm = $('#create-comment')
 
      // commentForm 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
      if( commentForm.is(":visible") ){
          commentForm.slideUp(500);
      }else{
          commentForm.slideDown(1000);
      }

      

    }
  },
}
</script>
<style scoped>
button{
  background-color: rgb(86, 149, 233);
  border-style: none;
  border-radius: 3px;
  color:rgb(255, 255, 255);
  padding:4px 8px;
  font-weight: bold;
}
</style>