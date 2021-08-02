<template>
  <div class="row my-5" @click="clickArticle">
    <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
    <div class="box col-lg-6 col-md-10 col-sm-9 col-10">

      <div class="row top mt-2">
        <div class="col-3 box1 image mt-2">
          <i class="fas fa-user" style="font-size:60px;"></i>
        </div>
        <div class="col box1">
          <div class="row box1 name">
            <p>my name</p>
          </div>
          <div class="row box1 mt-2">
            <div>
              <div class="title mb-1">this is my title</div>
              <div class="hashtag">
                <span>백준</span>
                <span>1324</span>
                <span>Python</span>
                <span>bfs</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row middle">
        <div class="col box1 content">
          Lorem sit, r adipi consequatur eveniet rem. Velit temporibus fuga labore nisi placeat quos ad, porro quae ipsam, aperiam, ab fugiat nihil vitae doloremque sunt at enim quibusdam quisquam quis dolor veritatis blanditiis! Cupiditate quasi voluptatibus sunt eligendi rem. Quidem dolore ipsam, odit modi voluptates error. Dolorem excepturi consequatur neque maxime odit aliquid enim nobis molestiae itaque, quaerat, repellendus beatae inventore facilis quis fuga ab cupiditate et necessitatibus porro consequuntur ratione! Reprehenderit temporibus aut vel.
        </div>
      </div>

      <div class="row bottom mt-4">
        <div class="col box1 like-comment">
          <i class="fas fa-heart me-3">
            <span class='ms-1'>123</span>
          </i>
          <i class="far fa-comment-dots">
            <span class='ms-1'>456</span>
          </i>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  methods: {
    clickArticle: function() {
      this.$router.push({name : 'articleDetail'})
    }
  }
};
</script>

<style scoped>
.feed {
  margin-top: 13vw;
}
.box {
  background: white;
  -webkit-box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  border-radius: 1px;
  padding: 15px 15px;
  height: 330px;
}
.box:hover{
  box-shadow: 0 20px 20px rgba(161,212,226,0.6);
  transform: scale(1.1);
  cursor: pointer;
}
.box1 {
  /* border: 1px solid black; */
}
.image{
  text-align: center;
}
.top{
  height:100px;
}
.name{
  height:25px;
  margin-top:10px;
}
.title{
  height:30px;
  font-size: 25px;
  margin-bottom: 0;
}
.middle{
  margin-top: 10px;  
  padding: 0 20px;
}
.content{
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 6; /* 라인수 */
  -webkit-box-orient: vertical;
  word-wrap:break-word; 
}

.like-comment{
  text-align: end;
}
.hashtag > span{
  font-size: 16px;
  border-radius: 3px;
  background-color : rgba(221,223,230,1);
  padding:1px 8px;
  margin-right: 6px;
}
.hashtag{
  height:20px;
}
@media (max-width:767px) {
  .top{
    margin-bottom:30px;
  }
  .box{
    height:350px;
  }
}
</style>

