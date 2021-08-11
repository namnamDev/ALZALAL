<template>
  <div class="container">
    <div v-for="(item, idx) in articleItems" :key="idx">
      <TimelineItem
        :articleNo="item.articleNo"
        :content="item.articleContent"
      />
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="sprial">
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px"
      >
        목록의 끝입니다 :)
      </div>
    </infinite-loading>
  </div>
</template>

<script>
import TimelineItem from "@/components/article/TimelineItem.vue";
import axios from "axios";
import InfiniteLoading from "vue-infinite-loading";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: { TimelineItem, InfiniteLoading },
  data: function () {
    return {
      articleItems: [],
      page: 0,
    };
  },
  computed: {
    getToken() {
      const token = localStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },
  },
  mounted() {
    // const search = document.querySelector('#search')
    // search.style.display = 'block'
  },
  // created() {
  //   axios({
  //       method: 'get',
  //       url: `${SERVER_URL}/articlelist/article`,
  //       headers: this.getToken(),
  //     })
  //     .then(res =>{
  //       this.articleItems = res.data.article
  //     })
  //     .catch(err =>{
  //       console.log(err)
  //     })
  // }
  methods: {
    infiniteHandler($state) {
      axios({
        method: "get",
        url: `${SERVER_URL}/articlelist/article` + "?page=" + this.page,
        headers: this.getToken,
      })
        .then((res) => {
          setTimeout(() => {
            if (res.data.article.length) {
              console.log(res.data)
              this.articleItems = this.articleItems.concat(res.data.article);
              $state.loaded();
              this.page += 1;
              //console.log("after", this.page)
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면 
              if(res.data.article.length / 10 < 1) {
                $state.complete()
              }
            } else {
              // 끝 지정(No more data)
              $state.complete();
            }
          }, 1000);
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
};
</script>

<style scoped>
.container {
  margin-top: 110px;
}
@media (max-width: 576px) {
  .container {
    margin-top: 60px;
  }
}
</style>