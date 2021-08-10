<template>
  <div class="row">
    <div class="col-lg-3 col-md-2 col-sm-3 col-0"></div>
    <div class="debate col-lg-6 col-md-10 col-sm-9 col-12">
        <div class="contentsWrap">
            <p class="title">{{this.discussionCompName}}     <span>{{this.discussionCompProblem}}</span></p>
            <p>{{this.discussionHostName}}</p>                
        </div>
        <div class="btn-group wrap col-12 col-lg-12 col-ml-12">
            <div class="col box1 like-comment">
              <i class="far fa-comment-dots mx-2"></i>
              <span >{{this.discussCommentCount}}</span>
              <div><p class="date">{{this.discussionDate}}</p></div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
    props:{
        discussionHostName: String,
        discussionHostNo: Number,
        discussionCompName: String,
        discussionDate: String,
        discussionNo: Number,
        discussionCompProblem: String,
        discussCommentCount: Number
    }
}
</script>

<style>

</style>