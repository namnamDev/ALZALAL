<template>
  <div>   
    <!-- <div style="margin-top: 600px" v-if="viewerText"  > -->
    <div>    
      {{viewerContent}}  
      <Viewer  
      class="viewer"      
      height="500px" 
      :initialValue="viewerContent"
      :options="viewerOptions"/>
    </div>
  </div>
</template>

<script>
// Toast UI Editor - viewer
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { Viewer } from '@toast-ui/vue-editor';

// add dependencies related code-syntax-highlight
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
      // viewerText: '# hello world',
      viewerOptions: VIEWER_OPTION,
      viewerContent: this.viewerText,
    };
  },
  computed: {
    // viewerContent: function() {
    //   return this.viewerText
    // },
  },


};
</script>

<style scoped>
.viewer{
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 8; /* 라인수 */
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
  height:100%;
}
</style>