<template>
  <div>   
    <div class="viewer">
      <Viewer       
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
    };
  },
  computed: {
    viewerContent: function() {
      return this.viewerText
    },
  },
  methods: {

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
}

</style>