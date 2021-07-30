<template>
  <div>
    <Editor
      ref="toastEditor"
      height="500px"
      previewStyle="tab"
      :options="editorOptions"
     />
    <div class="text-end mt-3 mb-5">
      <button>저장</button>
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
    // 마크다운 에디터 내용 가져오기
    getContent() {
      return this.$refs.toastEditor.invoke('getMarkdown')
    },
    setContent(content) {
      this.$refs.toastEditor.invoke('setMarkdown', content)
    },
  },
}
</script>