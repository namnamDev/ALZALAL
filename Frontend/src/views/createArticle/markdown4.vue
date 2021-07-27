<template>
  <div class="editor">
    <button @click="sendData">작성</button>
    <Editor
      ref="toastEditor"
      :value="editorText"
      :options="editorOptions"
      height="400px"
      previewStyle="vertical"
    />
  </div>
</template>

<script>
// Toast UI Editor - Editor
import { Editor } from "@toast-ui/vue-editor";
import "@toast-ui/editor/dist/toastui-editor.css"; // Editor's Style
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/i18n/ko-kr";


// add dependencies related code-syntax-highlight
import 'highlight.js/styles/github.css';
import hljs from 'highlight.js';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';

// add dependencies related color-syntax
import 'tui-color-picker/dist/tui-color-picker.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';


const DEFAULT_OPTION = {
  useCommandShortcut: true,
  useDefaultHTMLSanitizer: true,
  usageStatistics: true,
  hideModeSwitch: false,
  plugins: [[codeSyntaxHighlight, { hljs }],colorSyntax]
};

export default {
  name: "markdown4",
  components: {
    Editor,
  },
  data() {
    return {
      editorText: "",
      editorOptions: DEFAULT_OPTION,
    };
  },
  methods: {
    getContent() {
      return this.$refs.toastEditor.invoke('getMarkdown')
    },
    sendData: function() {
      this.$emit('send-data',this.getContent())
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