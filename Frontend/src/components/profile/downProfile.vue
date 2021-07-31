<template>
  <div class="row my-5">
  <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
   <div class="feed-item col-lg-6 col-md-10 col-sm-9 col-10">
     <div class="row">
          <div class="col-4 col-lg-4">
            <button class="btn" @click="changeComponent('articleList')">게시글</button>
          </div>
          <div class="col-4 col-lg-4">
            <button class="btn" @click="changeComponent('helpmeList')">요청한 문제</button>
          </div>
          <div class="col-4 col-lg-4">
            <button class="btn" @click="changeComponent('receptList')">요청받은 문제</button>
          </div>
        </div>
        <div class="tab-item">
          <keep-alive>
            <component v-bind:is="comp"></component> 
          </keep-alive>
        </div>
  </div>
</div>
</template>

<script>
import articleList from '@/components/profile/downProfile/articleList'
import helpmeList from '@/components/profile/downProfile/helpmeList'
import receptList from '@/components/profile/downProfile/receptList'
export default {
  components: {
    articleList,
    helpmeList,
    receptList
  },
  data() {
      return { comp: 'articleList' }
  },
  methods: {
      changeComponent: function(componentName) {
          this.comp = componentName
          console.log(this.comp)
      }
  }
}
</script>

<style scoped>
.nav-link {
  cursor: pointer;
}

.feed-card {
    box-sizing: content-box;
    /* box-shadow: 0 0 0 1px #ddd; */
    color: #000;
    float: left;
    border-radius: 5px;
    overflow: hidden;
    
}
 .contentsWrap {
        box-sizing: border-box;
        padding: 12px;
        float: left;
 }
 .title {
    color:#000;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    word-wrap:break-word; 
    line-height: 1.5em;
    font-size: 15px;
    margin: 0 0 8px;
    white-space: normal;
}
.date {
  float: right;
  font-size: 10px;
  color:rgba(0, 0, 0, .5);
}
.feed-item {
    margin-bottom: 30px;
    border-bottom: 1px solid grey;
    padding-bottom: 20px;
}
.profile-image {
    float: left;
}
.user-info, .content {
    width: calc(100% - 50px);
    float: right; 
}
.user-name {
    float: left;
}
.user-name button {
   font-weight: 600;
}
.user-name span {
   margin-left: 10px;
}
.date {
  float: right;
}
.profileImg{
  width: 100px;
  height: 100%;
  box-sizing: border-box;
  float:left;
  border:1px solid grey;
  border-radius: 3px;
}
#clickBoard:hover {
  background-color:#a1d4e2;
}
#clickRequest:hover{
  background-color: #a1d4e2;
}
#clickSend:hover{
  background-color: #a1d4e2;
}
@media (max-width:577px) {
  .feed{
    margin-left:0;
  }
}

</style>