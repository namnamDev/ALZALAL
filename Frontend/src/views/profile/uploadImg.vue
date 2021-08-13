<template>
  <div>
    <div class="contents">
      <div class="form-wrapper form-wrapper-sm">
        <form @submit.prevent="uploadImage">
          <input
            type="file"
            @change="onFileChange"
            ref="image"
            name="image"
            id="image"
            class="btn-file"
          />
          <div id="preview" class="profile-image" v-if="url">
            <img class="profileImg" :src="url" />
          </div>
          <div id="preview" class="profile-image" v-if="!url">
            <img class="profileImg" alt="프로필이미지를 등록해주세요" />
          </div>

          <button type="submit" class="btn btn-submit">
            프로필이미지로 지정하기
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import jwt_decode from "jwt-decode";
import axios from "axios";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
const token = sessionStorage.getItem("jwt");
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
}
export default {
  data() {
    return {
      images: "",
      imgsrc: `${SERVER_URL}/profile/img/${userpk}`,
      url: null,
    };
  },
  created: function () {
    const token = sessionStorage.getItem("jwt");
    if (!token) {
      this.$router.push({ name: "login" });
    }
  },
  computed: {
    getToken() {
      const token = sessionStorage.getItem("jwt");
      const config = {
        Authorization: `Bearer ${token}`,
      };
      return config;
    },
  },
  methods: {
    uploadImage: function () {
      let formdata = new FormData();
      let profileImage = this.$refs["image"].files[0];

      formdata.append("profileImg", profileImage);

      axios({
        method: "post",
        url: `${SERVER_URL}/profile/img`,
        data: formdata,
        headers: this.getToken,
      })
        .then((res) => {
          console.log("img", res);
          this.$router.push({ name: "profilePage" });
        })
        .catch((err) => { 
          console.log(err)
          console.log(this.getToken)
        });
    },
    onFileChange(e) {
      const file = e.target.files[0];
      this.url = URL.createObjectURL(file);
    },
  },
};
</script>

<style scoped>
/*--- LAYOUT ---*/
.contents {
  position: absolute;
  top: 180px;
  margin: 0 auto;
  padding: 0 5px;
  width: 100%;
}
.form-wrapper {
  background: white;
  -webkit-box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  border-radius: 3px;
  padding: 15px 15px;
  height: 360px;
}
.form-wrapper.form-wrapper-sm {
  max-width: 500px;
  margin: 40px auto;
}
.form-wrapper-sm .page-header {
  padding: 0px 0 20px;
}
.form {
  width: 460px;
  height: 100%;
}
.form .validation-text {
  margin-top: -0.5rem;
  margin-bottom: 0.5rem;
  font-size: 1rem;
  display: flex;
  flex-direction: row-reverse;
  justify-content: space-between;
}

.form input {
  font-family: inherit;
  font-size: 100%;
  width: 100%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
}


.btn {
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: 0.25rem;
}
.btn.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.btn-user {
  opacity: 0.8;
}
.profile-image {
  width: calc(33.333% - 1rem);
  height: auto;
  justify-content: center;
  align-items: center;
  margin-right: 3rem;
}

.profileImg {
  width: 160px;
  height: 160px;
  border-radius: 75%;
}
.btn-submit {
  display: inline;
  background-color: rgb(62, 171, 111);
  color: white;
  width:250px;
  margin-top: 100px;

}
</style>