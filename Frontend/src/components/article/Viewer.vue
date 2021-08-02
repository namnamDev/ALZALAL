<template>
  <div>   
    <!-- <div style="margin-top: 600px" v-if="viewerText"  > -->
    <div>
      <Viewer         
      :initialValue="viewerText"
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
  data() {
    return {
      // viewerText: '# hello world',
      viewerOptions: VIEWER_OPTION,
    };
  },
  computed: {
    viewerText: function() {
      return this.$store.getters.getContent
    },
  },
  methods: {

  },
  created() {
    
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