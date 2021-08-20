<template>
  <div class="row">
		<div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
    <div class="col-lg-6 col-md-10 col-sm-9 col-10">
      <div class="form-wrapper form-wrapper-sm">
        <form @submit.prevent="submitForm" class="row form">
          <div>
            <label align="left" for="password">비밀번호를 입력해주세요</label>
            <input id="password" type="password" v-model="password" />
          </div>
          <div class="checkBtn">
            <button :disabled="!password" v-on:keyup.enter="submit" type="submit" class="btn">
              확인
            </button>
          </div>
        </form>
		  </div>
		</div>
       
	</div>
</template>

<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
let userpk = localStorage.getItem('userPk')
export default {
  data() {
		return {
      password: '',
		};
	},
  created: function() {
    const token = sessionStorage.getItem('jwt')
    if(!token){
      this.$router.push({name:'login'})
    }
  },
  computed: {
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    }
  },
  methods: {
    submitForm: function() {
      axios({
            method: 'post',
            url: `${SERVER_URL}/member/checkpw`,
            data: {
              "password" : this.password
              },
            headers: this.getToken,
          })
          .then(() => {

            this.$router.push({ name: 'usermodify', params:userpk })
        
          })
          .catch(err => {
            alert(err)
            console.log(err)
          })
    }
  }
}
</script>

<style scoped>
/*--- LAYOUT ---*/
.row {
  margin-top: 40px;
} 
.form-wrapper {
  background: white;
  -webkit-box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  border-radius: 3px;
  padding: 15px 15px;
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
.form .validation-text {
	margin-top: -0.5rem;
	margin-bottom: 0.5rem;
	font-size: 20px;
	display: flex;
	flex-direction: row-reverse;
	justify-content: space-between;
}
.form label {
  width: 100%;
  display: block;
  margin-bottom: 0.5rem;
  font-size: 20px;
  margin-left: 20px;
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
  margin-left: 20px;
}

.form div:nth-last-child(2) {
  margin-bottom: 0.5rem;
}
.checkBtn{
  margin-left: 150px;
}
.btn {
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .25rem;
  width:30%;
}
.btn.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.btn-user{
    opacity: 0.8;
}
@media (min-width: 1440px){
.form input,
.form textarea {
  font-family: inherit;
  font-size: 20px;
  width: 120%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
}
.btn {
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .25rem;
  margin-left: 300px;
}
.form .validation-text {
	margin-top: -0.5rem;
	margin-bottom: 0.5rem;
  margin-right: -4.5rem;
	font-size: 16px;
	display: flex;
	flex-direction: row-reverse;
	justify-content: space-between;
}
}
@media (max-width:767px) {
  .form input,
.form textarea {
  font-family: inherit;
  font-size: 20px;
  width: 60%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
  margin-left: 20px;
}
}
@media (min-width:768px) and (max-width:1440px) {
  .btn {
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .25rem;
  margin-left: 200px;
}
}
</style>