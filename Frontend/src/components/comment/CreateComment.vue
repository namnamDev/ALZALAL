<template>
  <div class="mb-5">
    <Editor :articleNo="this.articleNo"/>
  </div>
</template>

<script>
import Editor from '@/components/comment/CreateCommentEditor.vue'

export default {
  components: {
    Editor
  },
  props: {
    articleNo: Number,
  }
}
</script>

<style>

</style>