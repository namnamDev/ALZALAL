<template>
  <div>   
    <div>    
      <Viewer  
      class="viewer"      
      height="500px" 
      :initialValue="viewerContent"
      :options="viewerOptions"/>
    </div>
  </div>
</template>

<script>
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { Viewer } from '@toast-ui/vue-editor';

import 'highlight.js/styles/github.css';
import hljs from 'highlight.js';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';


const VIEWER_OPTION = {
  plugins: [[codeSyntaxHighlight, { hljs }]]
}

export default {
  name: "TextEditor",
  components: {
    Viewer,
  },
  props: {
    viewerText: String,
  },

  data() {
    return {
      viewerOptions: VIEWER_OPTION,
      viewerContent: this.viewerText,
    };
  },



};
</script>

<style scoped>
.viewer{
  height:100%;
}
</style>