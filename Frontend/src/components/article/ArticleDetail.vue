<template>
  <div class="container">
    <div class="row my-5">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="col-lg-6 col-md-10 col-sm-9 col-11 content">

        <div class="row top mb-3">
          <div class="row title">
            제목
          </div>
          <div class="row">
            <div class="col">
              작성자
            </div>
            <div class="col text-end">
             작성날짜
            </div>
          </div>
        </div>

        <div class="row middle py-4 px-0">
          <div class="row mb-5">
            <div>
              <Viewer />
            </div>

            <div class="col thumbs mb-3">
              <i class="fal fa-thumbs-up">
                <span class="ms-2">34</span>
              </i>
            </div>

          </div>
        </div>

        <div class="row bottom my-3">
          <div class="row">
            <button class="mb-4" @click="clickAlgoInput">댓글쓰기</button>
            <div id="create-comment">
              <CreateComment />
            </div>
          </div>
          <div class="row my-3">
            <Comment />
            <Comment />
            <Comment />
          </div>
        </div>
      </div>
    
    </div>
  </div>
</template>

<script>
import Comment from '@/components/comment/CommentItem.vue'
import Viewer from '@/components/article/Viewer.vue'
import CreateComment from '@/components/comment/CreateCommentEditor.vue'
import $ from 'jquery';

export default {
  name: 'ArticleDetail',
  components:{
    Comment,
    Viewer,
    CreateComment,
  },
  methods: {
    clickAlgoInput() {
      // document.getElementById("create-comment").classList.toggle("show");

      var commentForm = $('#create-comment')
 
      // commentForm 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
      if( commentForm.is(":visible") ){
          commentForm.slideUp(1000);
      }else{
          commentForm.slideDown(1000);
      }
    },
  }
}
</script>

<style scoped>
#create-comment{
  display: none;
}
.show{
  display: block !important;
}

.container{
  margin-top: 100px;
}
.top{
  width:100%;
  height:100px;
  /* border:1px solid black; */
}
.middle{
  width:100%;
  /* height:400px; */
  /* border:1px solid black; */
  border-top: 1px solid black;
  border-bottom: 1px solid black;
  position: relative;
}
.bottom{
  width:100%;
  height:100px;
  /* border:1px solid black; */
  position: relative;
}
.title{
  font-size:40px;
}
.fa-thumbs-up{
  font-size: 20px;
}
.thumbs{
  position: absolute;
  text-align: end;
  bottom: 0;
}
button{
  width: 120px;
}
</style>