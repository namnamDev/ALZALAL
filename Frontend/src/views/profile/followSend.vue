<template>
  <div class="container">
      <form @submit.prevent="click">
      <label for="memberNo"></label>
      <input id="memberNo" type="text" v-model="form.memberNo">
      <button type="submit">팔로우요청</button>
      </form>
  </div>
</template>

<script>
import jwt_decode from "jwt-decode";
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
const token = localStorage.getItem('jwt')
let userpk = "";
if (token) {
  const decoded = jwt_decode(token);
  userpk = decoded.sub;
  console.log(userpk);
  console.log(token)
}
export default {
    data() {
        return{
            form:{
           memberNo: '',
           }
        }
    },
    created: function () {
        const token = localStorage.getItem("jwt");
        if (!token) {
        this.$router.push({ name: "login" });
        }
    },
    
    methods:{
        click: function(){
            axios({
                    method: 'post',
                    url: `${SERVER_URL}/follow/member`,
                    data: this.form.memberNo,
                    headers: this.getToken,
                })
                .then(res =>{
                    console.log(res)         
                })
                .catch(err => {
                    console.log(this.form.memberNo)
                    console.log(this.getToken)
                    console.log(err);
                })
        }
    }
}
</script>

<style scoped>
.container{
  margin-top: 150px;
  margin-left: 50px;
}
@media (max-width:576px){
  .container{
    margin-top: 150px;
    margin-left:0
  }
}
.form-wrapper.form-wrapper-sm {
  max-width: 700px;
}
.form-wrapper-sm .page-header {
  padding: 0px 0 20px;
}
.form {
	width: 460px;
	height: 100%;
}
.form label {
  width: 100%;
  display: block;
  margin-bottom: 0.5rem;
  font-size: 25px;
  font-weight: 550;
}
.form input,
.form textarea {
  font-family: inherit;
  font-size: 20px;
  width: 100%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
}
</style>