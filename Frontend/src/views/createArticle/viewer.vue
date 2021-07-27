<template>
  <div class="editor">
    <h1>Editor</h1>
    <Editor @send-data="getData"/>
    
    <div style="margin-top: 600px" v-if="viewerText"  >
      <h1>Viewer</h1>
      <Viewer         
      :initialValue="viewerText"
      :options="viewerOptions"/>
    </div>
  </div>
</template>

<script>
// Toast UI Editor - Editor
import "@toast-ui/editor/dist/toastui-editor.css"; // Editor's Style
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/i18n/ko-kr";

// Toast UI Editor - viewer
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { Viewer } from '@toast-ui/vue-editor';

// add dependencies related code-syntax-highlight
import 'highlight.js/styles/github.css';
import hljs from 'highlight.js';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';

import Editor from '@/views/createArticle/markdown4.vue'


const VIEWER_OPTION = {
  plugins: [[codeSyntaxHighlight, { hljs }]]
}

export default {
  name: "TextEditor",
  components: {
    Viewer,
    Editor,
  },
  data() {
    return {
      viewerText: '',
      viewerOptions: VIEWER_OPTION
    };
  },
  methods: {
    getData: function(data) {
      console.log(data)
      this.viewerText = data
    }
  }
};
</script>

<style scoped>
.editor {
  position: absolute;
  /* border: 1px solid black; */
  width: 800px;
  left: 50%;
  transform: translateX(-50%);
  top: 200px;
  
}
</style>