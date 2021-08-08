<template>
  <div class="sendQuiz col-12 col-ml-12 col-lg-12" id="sendQuiz">
    <div class="feed-card col-12 col-lg-12 col-ml-12">
      <div class="contentsWrap" @click="clickHelpmeName">
        <span>{{item.problemSite.problemSiteName}}  </span>
        <span>{{item.problemSite.problemNo}}번 문제</span>
        <div>
          <p class="helpmeContent">{{item.helpmeContent}}</p>
        </div>
        <div class="wrap">
          <p class="comment"><i class="fas fa-comment"></i>  {{item.commentCount}}</p>
          <p class="date">{{item.helpmeDate}}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>