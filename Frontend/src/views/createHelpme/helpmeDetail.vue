<template>
  <div class="animate__animated animate__fadeInUp my-4 main">
    <div class="article-box col-lg-6 col-md-10 col-sm-9 col-10">
      <div>
        <div class="title">
          <span class="username">{{ this.helpmeSenderName }}</span
          >님이 <span class="username">{{ this.helpmeReceptorName }}</span
          >님에게 요청한 문제
        </div>
        <div class="row">
          <div class="col text-end text-secondary">
            {{ helpmeDate | moment("YYYY-MM-DD HH:mm:ss") }}
          </div>
        </div>
      </div>

      <div class="middle px-0">
        <div class="col text-end px-0" v-if="isSender">
          <span @click="modifyArticle" style="cursor: pointer">수정</span> |
          <span @click="deleteArticle" style="cursor: pointer">삭제</span>
        </div>
        <div class="viewer">
          {{ this.helpmeContent }}
        </div>
      </div>

      <div class="bottom mt-3">
        <div>
          <div class="col thumbs">
            <i class="fas fa-heart" @click="clickLike" v-if="likeState"></i>
            <i class="far fa-heart" @click="clickLike" v-else></i>
            <span>
              {{ likeCount }}
            </span>
          </div>
          <button class="create-comment-btn" @click="clickCreateComment">
            댓글쓰기
          </button>
          <div id="create-comment">
            <CreateComment :helpmeNo="this.helpmeNo" />
          </div>
        </div>
        <br />
        <div class="row" v-for="(comment, idx) in getComments" :key="idx">
          <Comment :comment="comment" />
        </div>
        <div class="row mb-1 my-1">
          <div class="col text-center">
            <span class="previous-btn" @click="clickPreviousBtn">previous</span>
            <input
              type="text"
              v-model="currentPage"
              class="current-page"
              @keyup.enter="goPage"
            />
            /<span class="mx-4">{{ commentCount }}</span>
            <span class="next-btn" @click="clickNextBtn">next</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
import Comment from "@/components/comment/CommentHelpmeItem.vue";
import CreateComment from "@/views/createHelpme/createHelpmeComment.vue";
import jwt_decode from "jwt-decode";
import Vue from "vue";
import vueMoment from "vue-moment";
import axios from "axios";
import $ from "jquery";
Vue.use(vueMoment);
const token = sessionStorage.getItem("jwt");
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
}
export default {
  name: "helpmeDetail",
  props: {
    // articleno: Number
    Page: String,
  },

  components: {
    Comment,
    CreateComment,
  },

  data: function () {
    return {
      helpmeContent: "",
      helpmeNo: "",
      content: "",
      commentCount: "",
      likeCount: "",
      likeState: "",
      problemSiteName: "",
      problemNo: "",
      language: "",
      helpmeDate: "",
      helpmeReceptorNo: "",
      helpmeReceptorName: "",
      helpmeSenderNo: "",
      helpmeSenderName: "",
      helpmeStatus: "",
      currentPage: Number(this.Page) + 1,
      myPage: "",
    };
  },
  mounted() {
    this.getCommentList();
  },
  computed: {
    getComments: function () {
      return this.$store.getters.getHelpmeComments;
    },
    getStatus: function () {
      let status = "";
      if (this.helpmeStatus == "H00") {
        status = "답변대기중";
      } else if (this.helpmeStatus == "H01") {
        status = "답변중";
      } else if (this.helpmeStatus == "H02") {
        status = "거절됨";
      } else if (this.helpmeStatus == "H03") {
        status = "답변완료";
      }
      return status;
    },
    isSender: function () {
      const token = sessionStorage.getItem("jwt");
      let username = "";
      if (token) {
        const decoded = jwt_decode(token);
        username = decoded.name;
        if (username == this.helpmeSenderName) {
          return true;
        } else {
          return false;
        }
      }
      return false;
    },
  },

  created() {
    const helpme = this.$store.getters.getHelpmeDetail;
    this.helpmeContent = helpme.helpmeContent;
    this.helpmeDate = helpme.helpmeDate;
    this.helpmeNo = helpme.helpmeNo;

    this.helpmeReceptorNo = helpme.helpmeReceptorNo.no;
    this.helpmeSenderNo = helpme.helpmeSenderNo.no;
    this.helpmeReceptorName = helpme.helpmeReceptorNo.name;
    this.helpmeSenderName = helpme.helpmeSenderNo.name;
    this.commentCount = helpme.commentCount;
    this.likeState = helpme.likeState;
    this.helpmeStatus = helpme.helpmeStatus;

    this.problemNo = helpme.problemSite.problemNo;
    this.problemSiteName = helpme.problemSite.problemSiteName;
    const userPk = localStorage.getItem("userPk");

    if (userpk != userPk) {
      this.myPage = false;
    } else {
      this.myPage = true;
    }
  },
  methods: {
    modifyArticle: function () {
      this.$swal
        .fire({
          title: "글을 수정하시겠습니까?",
          text: "문제사이트, 문제번호는 변경이 불가능합니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "네",
          cancelButtonText: "아니요",
        })
        .then((result) => {
          if (result.value) {
            this.$router.push({
              name: "modifyHelpme",
              params: { helpme: this.helpmeNo },
            });
          }
        });
    },

    deleteArticle: function () {
      const helpmeNo = localStorage.getItem("helpmeNo");
      this.$swal
        .fire({
          title: "글을 삭제하시겠습니까?",
          text: "삭제하시면 다시 복구시킬 수 없습니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "네",
          cancelButtonText: "아니요",
        })
        .then((result) => {
          if (result.value) {
            axios({
              method: "delete",
              url: `${SERVER_URL}/helpme/${helpmeNo}`,
              headers: this.getToken(),
            })
              .then(() => {
                this.$router.push({ name: "profilePage" });
              })
              .catch((err) => {
                console.log(err);
              });
          }
        });
    },

    clickNextBtn: function () {
      if (Number(this.Page) < this.commentCount - 1) {
        const page = String(Number(this.Page) + 1);
        location.href = `/helpmeDetail/${page}`;
      }
    },
    clickPreviousBtn: function () {
      if (Number(this.Page) > 0) {
        const page = String(Number(this.Page) - 1);
        location.href = `/helpmeDetail/${page}`;
      }
    },
    goPage: function () {
      location.href = `/helpmeDetail/${this.currentPage - 1}`;
    },
    getCommentList: function () {
      this.$store.dispatch("deleteArticleComment");
      const helpmeNo = localStorage.getItem("helpmeNo");
      axios({
        method: "get",
        url: `${SERVER_URL}/comment/helpme/${helpmeNo}?page=${this.Page}`,
        headers: this.getToken(),
      })
        .then((res) => {
          this.$store.dispatch("createHelpmeComment", res.data.articleComments);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //댓글 작성하기
    clickCreateComment() {
      const token = sessionStorage.getItem("jwt");
      if (!token) {
        this.$swal
          .fire({
            text: "로그인 후 이용해주세요.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "로그인",
            cancelButtonText: "취소",
          })
          .then((result) => {
            if (result.value) {
              this.$router.push({ name: "login" });
            }
          });
      }

      var commentForm = $("#create-comment");

      // commentForm 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
      if (commentForm.is(":visible")) {
        commentForm.slideUp(700);
      } else {
        commentForm.slideDown(1000);
      }
    },

    getToken() {
      const token = sessionStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },
    clickAlgoInput() {
      // document.getElementById("create-comment").classList.toggle("show");

      var commentForm = $("#create-comment");

      // commentForm 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
      if (commentForm.is(":visible")) {
        commentForm.slideUp(700);
      } else {
        commentForm.slideDown(1000);
      }
    },
    clickLike: function () {
      const helpmeNo = localStorage.getItem("helpmeNo");
      axios({
        method: "post",
        url: `${SERVER_URL}/like/helpme/article/${helpmeNo}`,
        headers: this.getToken(),
        data: {},
      })
        .then(() => {})
        .catch((err) => {
          console.log(err);
        });
      if (this.likeState) {
        this.likeState = false;
        this.likeCount -= 1;
      } else {
        this.likeState = true;
        this.likeCount += 1;
      }
    },
    clickName: function () {
      localStorage.setItem("userPk", this.memberNo);
      this.$router.push({
        name: "profilePage",
        params: { userPk: this.memberNo },
      });
    },
  },
};
</script>

<style scoped>
#create-comment {
  display: none;
}
.article-box {
  background: white;

  border: 1px solid rgb(207, 207, 207);
  border-radius: 5px;
  padding: 15px 15px;
  /* height: 400px; */
}
.show {
  display: block !important;
}

.container {
  margin-top: 100px;
}
.top {
  width: 100%;
  height: 100px;
}
.middle {
  width: 100%;
  position: relative;
}
.viewer {
  font-size: 20px;
}
.bottom {
  width: 100%;
  position: relative;
}
.title {
  font-size: 23px;
  font-weight: 550;
}
.fa-thumbs-up {
  font-size: 20px;
}
.thumbs {
  text-align: end;
  bottom: 0;
}
.fa-heart {
  color: rgba(62, 171, 111, 1);
  cursor: pointer;
}
button {
  width: 120px;
  height: 30px;
}
.clickName {
  cursor: pointer;
}
.create-comment-btn {
  background-color: rgba(62, 171, 111, 1);
  border-style: none;
  border-radius: 3px;
  color: rgb(255, 255, 255);
  font-weight: bold;
}
.current-page {
  width: 30px;
  height: 23px;
  margin-left: 10px;
  margin-right: 15px;
  text-align: center;
}
.previous-btn {
  cursor: pointer;
}
.next-btn {
  cursor: pointer;
}
.member-name {
  cursor: pointer;
}
.member-name:hover {
  font-size: 18px;
}
</style>