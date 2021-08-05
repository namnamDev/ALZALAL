<template>
  <div class="comment mb-2">
    <div class="user">
      <div class="row">
        <div class="col">
          <div class="inline">
            {{this.memberName}}
          </div >
          <div class="inline">
            | {{this.date | moment("YYYY-MM-DD HH:mm:ss")}}
          </div>
          <div class="inline">
            <i class="fal fa-thumbs-up">
              <span class="ms-2">{{this.likeCount}}</span>
            </i>
          </div>
        </div>
        <div class="col text-end pe-3">
          좋아요
        </div>
      </div>
    </div>
    <div class="content">
      <Viewer :viewerText="this.content"/>
    </div>

  </div>
</template>

<script>
import Viewer from '@/components/comment/CommentViewer.vue'

import Vue from "vue";
import vueMoment from "vue-moment";
Vue.use(vueMoment);

export default {
  components: {
    Viewer
  },
  props: {
    comment: Object
  },
  data() {
    return {
      memberName: this.comment.member.name,
      date: this.comment.articleCommentDate,
      likeCount: this.comment.likeCount,
      content: this.comment.content
    }
  },
}
</script>

<style scoped>
.content{
  /* border: 1px solid black; */
  padding: 10px;
  border-radius: 6px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.08);
}
.inline{
  display:inline;
}
</style>